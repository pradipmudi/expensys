package com.expensys.helper;


import com.expensys.model.Expense;
import com.expensys.model.enums.Category;
import com.expensys.model.enums.Month;
import com.expensys.model.enums.SpentBy;
import com.expensys.model.request.ReportRequest;
import com.expensys.model.response.Report;
import com.expensys.model.response.ReportInfo;

import java.util.*;
import java.util.stream.Collectors;

import static com.expensys.constant.CategoryMappings.SUB_TO_MAIN_CATEGORY_MAPPINGS;

public class ExpenseToReportConvertor {
    private ExpenseToReportConvertor() {
        // Initialization code
    }

    public List<Report> prepareReportListFromExpenseList(List<Expense> expenseList, ReportRequest reportRequest) {
        HashMap<Month, List<ReportInfo>> monthReportInfoMap;

        monthReportInfoMap = prepareReportInfoListFromExpenseListByMonth(reportRequest, expenseList);
        return prepareReportInfoFromReportInfoByMonth(monthReportInfoMap, reportRequest);
    }

    private HashMap<Month, List<ReportInfo>> prepareReportInfoListFromExpenseListByMonth(ReportRequest reportRequest, List<Expense> expenseList) {
        HashMap<Month, List<ReportInfo>> monthReportInfoMap = new HashMap<>();
        SpentBy spentBy = reportRequest.getSpentBy();
        for (Expense expense : expenseList) {

            ReportInfo reportInfo = ReportInfo.builder()
                    .mainCategory(expense.getCategory())
//                    .subCategory(reportRequest.getCategory().equals(Category.MAIN) ? expense.getCategory().getCat() : expense.getSubCategory())
                    .subCategory(SUB_TO_MAIN_CATEGORY_MAPPINGS.get(expense.getCategory()).toString())
                    .spent(expense.getSpent())
                    .user(SpentBy.ALL.equals(spentBy) ? spentBy.toString() : expense.getSpentBy())
                    .build();

            Month currentMonth = expense.getMonth();
            List<ReportInfo> currentReportInfoList = monthReportInfoMap.get(currentMonth);

            if (currentReportInfoList == null) {
                currentReportInfoList = new ArrayList<>();
                monthReportInfoMap.put(currentMonth, currentReportInfoList);
            } else {
                currentReportInfoList.add(reportInfo);
                monthReportInfoMap.put(currentMonth, currentReportInfoList);
            }

        }
        return monthReportInfoMap;
    }

    private List<Report> prepareReportInfoFromReportInfoByMonth(HashMap<Month, List<ReportInfo>> monthReportInfoMap, ReportRequest reportRequest) {
        List<Report> reportList = new ArrayList<>();

        for (Map.Entry<Month, List<ReportInfo>> monthEntry : monthReportInfoMap.entrySet()) {
            Month month = monthEntry.getKey();
            Report currentReport = new Report(month, processReportInfoList(monthReportInfoMap.get(month), reportRequest));
            reportList.add(currentReport);
        }

        // Sort the reportList based on the month's integer values in descending order
        Collections.sort(reportList, (r1, r2) -> Integer.compare(Integer.valueOf(r2.getMonth().getMonthValue()), Integer.valueOf(r1.getMonth().getMonthValue())));

        return reportList;
    }

    public List<ReportInfo> processReportInfoList(List<ReportInfo> reportInfoList, ReportRequest reportRequest) {
        // Filter and collect by non-null subCategory
        List<ReportInfo> filteredList = reportInfoList.stream()
                .filter(reportInfo -> reportInfo.getSubCategory() != null)
                .toList();

        if (SpentBy.ALL.equals(reportRequest.getSpentBy())) {
            // Aggregate spent amount by subCategory when SpentBy is by "CATEGORY"
            Map<Category, Double> spentBycategory = filteredList.stream()
                    .collect(Collectors.groupingBy(
                            ReportInfo::getMainCategory,  // Group by subCategory
                            Collectors.summingDouble(ReportInfo::getSpent))); // Sum the spent amounts
            // Create a new list of ReportInfo objects based on Task 1
            return spentBycategory.entrySet().stream()
                    .map(entry -> ReportInfo.builder()
                            .mainCategory(entry.getKey())
                            .subCategory(SUB_TO_MAIN_CATEGORY_MAPPINGS.get(entry.getKey()).toString())
                            .user(SpentBy.ALL.getSpentBy())
                            .spent(entry.getValue())
                            .build())
                    .sorted(Comparator.comparing(ReportInfo::getSpent).reversed())
                    .toList();
        } else if (SpentBy.USER.equals(reportRequest.getSpentBy())) {
            // Aggregate spent amount by subCategory and spentBy user, ignoring case
            Map<Category, Map<String, Double>> categoryAndSpentByUser = filteredList.stream()
                    .collect(Collectors.groupingBy(
                            ReportInfo::getMainCategory,  // Group by subCategory
                            Collectors.groupingBy(
                                    reportInfo -> reportInfo.getUser().toLowerCase(), // Group by user, ignoring case
                                    Collectors.summingDouble(ReportInfo::getSpent)))); // Sum the spent amounts

            // Create a new list of ReportInfo objects based on Task 2, retaining the mainCategory
            return categoryAndSpentByUser.entrySet().stream()
                    .flatMap(entry -> {
                        Category category = entry.getKey();
                        // Extract the mainCategory for the subCategory
                        Category mainCategory = filteredList.stream()
                                .filter(reportInfo -> category.equals(reportInfo.getSubCategory()))
                                .map(ReportInfo::getMainCategory)
                                .findFirst()
                                .orElse(null);
                        // Create ReportInfo objects for each user and subCategory combination
                        return entry.getValue().entrySet().stream()
                                .map(userEntry -> ReportInfo.builder()
                                        .mainCategory(category)
                                        .subCategory(SUB_TO_MAIN_CATEGORY_MAPPINGS.get(category).toString())
                                        .spent(userEntry.getValue())
                                        .user(userEntry.getKey().toUpperCase())
                                        .build());
                    })
                    .sorted(Comparator.comparing(ReportInfo::getUser)) // Sort by user field
                    .sorted(Comparator.comparing(ReportInfo::getSpent).reversed())
                    .toList();
        }


        return new ArrayList<>(); // Default: return an empty list if the spentBy value is not recognized.
    }


    private static class SingletonHelper {
        private static final ExpenseToReportConvertor INSTANCE = new ExpenseToReportConvertor();
    }

    public static ExpenseToReportConvertor getInstance() {
        return SingletonHelper.INSTANCE;
    }
}
