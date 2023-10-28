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
        Map<Month, List<ReportInfo>> monthReportInfoMap;

        monthReportInfoMap = prepareReportInfoListFromExpenseListByMonth(reportRequest, expenseList);
        return prepareReportInfoFromReportInfoByMonth(monthReportInfoMap, reportRequest);
    }

    private Map<Month, List<ReportInfo>> prepareReportInfoListFromExpenseListByMonth(ReportRequest reportRequest, List<Expense> expenseList) {
        Map<Month, List<ReportInfo>> monthReportInfoMap = new EnumMap<>(Month.class);
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

    private List<Report> prepareReportInfoFromReportInfoByMonth(Map<Month, List<ReportInfo>> monthReportInfoMap, ReportRequest reportRequest) {
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
            // Group and sum by category, user, and mainCategory
            // Group and sum by mainCategory (an enum), user, and subCategory
            Map<Category, Map<String, Map<String, Double>>> result = filteredList.stream()
                    .collect(Collectors.groupingBy(
                            ReportInfo::getMainCategory,  // Group by mainCategory
                            Collectors.groupingBy(
                                    reportInfo -> reportInfo.getUser().toUpperCase(), // Group by user, ignoring case
                                    Collectors.groupingBy(
                                            reportInfo -> SUB_TO_MAIN_CATEGORY_MAPPINGS.get(reportInfo.getMainCategory()).toString(), // Group by mainCategory
                                            Collectors.summingDouble(ReportInfo::getSpent)
                                    )
                            )
                    ));

            // Create a new list of ReportInfo objects based on Task 2
            return result.entrySet().stream()
                    .flatMap(mainCategoryMapEntry -> {
                        Category mainCategory = mainCategoryMapEntry.getKey();
                        return mainCategoryMapEntry.getValue().entrySet().stream()
                                .flatMap(userEntry -> {
                                    String user = userEntry.getKey();
                                    return userEntry.getValue().entrySet().stream()
                                            .map(subCategoryEntry -> {
                                                String subCategory = subCategoryEntry.getKey();
                                                double spent = subCategoryEntry.getValue();
                                                return ReportInfo.builder()
                                                        .mainCategory(mainCategory)
                                                        .subCategory(subCategory)
                                                        .user(user)
                                                        .spent(spent)
                                                        .build();
                                            });
                                });
                    })
                    .sorted(Comparator.comparing(ReportInfo::getUser).thenComparing(ReportInfo::getSpent).reversed())
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
