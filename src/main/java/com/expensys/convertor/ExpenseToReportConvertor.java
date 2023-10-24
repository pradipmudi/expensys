package com.expensys.convertor;


import com.expensys.model.Expense;
import com.expensys.model.enums.Category;
import com.expensys.model.enums.Month;
import com.expensys.model.enums.SpentBy;
import com.expensys.model.request.ReportRequest;
import com.expensys.model.response.Report;
import com.expensys.model.response.ReportInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ExpenseToReportConvertor {
    private ExpenseToReportConvertor() {
        // Initialization code
    }

    public List<Report> prepareReportListFromExpenseList(List<Expense> expenseList, ReportRequest reportRequest) {
        HashMap<Month, List<Report>> monthReportMap = new HashMap<>();
        HashMap<Month, List<ReportInfo>> monthReportInfoMap = new HashMap<>();

        List<ReportInfo> reportInfoList = new ArrayList<>();
        monthReportInfoMap = prepareReportInfoListFromExpenseListByMonth(expenseList, SpentBy.ALL);
        return prepareReportInfoFromReportInfoByMonth(monthReportInfoMap, reportRequest);
    }

    private HashMap<Month, List<ReportInfo>> prepareReportInfoListFromExpenseListByMonth(List<Expense> expenseList, SpentBy spentBy) {
        HashMap<Month, List<ReportInfo>> monthReportInfoMap = new HashMap<>();

        for (Expense expense : expenseList) {

            ReportInfo reportInfo = ReportInfo.builder()
                    .mainCategory(expense.getCategory())
                    .subCategory(expense.getSubCategory())
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
        for (Month month : monthReportInfoMap.keySet()) {
            Report currentReport = new Report();
            currentReport.setMonth(month);
            currentReport.setReportInfo(processReportInfoList(monthReportInfoMap.get(month), reportRequest));

            reportList.add(currentReport);
        }
        return reportList;
    }

    public List<ReportInfo> processReportInfoList(List<ReportInfo> reportInfoList, ReportRequest reportRequest) {
        // Filter and collect by non-null subCategory
        List<ReportInfo> filteredList = reportInfoList.stream()
                .filter(reportInfo -> reportInfo.getSubCategory() != null)
                .collect(Collectors.toList());

        if (reportRequest.getSpentBy() == SpentBy.ALL) {
            // Task 1: Aggregate spent amount by subCategory when SpentBy is "BY_CATEGORY"
            Map<String, Double> subCategoryToSpent = filteredList.stream()
                    .collect(Collectors.groupingBy(
                            ReportInfo::getSubCategory,  // Group by subCategory
                            Collectors.summingDouble(ReportInfo::getSpent))); // Sum the spent amounts
            // Create a new list of ReportInfo objects based on Task 1
            return subCategoryToSpent.entrySet().stream()
                    .map(entry -> ReportInfo.builder()
                            .subCategory(entry.getKey())
                            .spent(entry.getValue())
                            .build())
                    .collect(Collectors.toList());
        } else if (reportRequest.getSpentBy() == SpentBy.USER) {
            // Task 2: Aggregate spent amount by subCategory and spentBy user
            Map<String, Map<String, Double>> subCategoryAndSpentByUser = filteredList.stream()
                    .collect(Collectors.groupingBy(
                            ReportInfo::getSubCategory,  // Group by subCategory
                            Collectors.groupingBy(
                                    ReportInfo::getUser, // Group by user
                                    Collectors.summingDouble(ReportInfo::getSpent)))); // Sum the spent amounts

            // Create a new list of ReportInfo objects based on Task 2, retaining the mainCategory
            return subCategoryAndSpentByUser.entrySet().stream()
                    .flatMap(entry -> {
                        String subCategory = entry.getKey();
                        // Extract the mainCategory for the subCategory
                        Category mainCategory = filteredList.stream()
                                .filter(reportInfo -> subCategory.equals(reportInfo.getSubCategory()))
                                .map(ReportInfo::getMainCategory)
                                .findFirst()
                                .orElse(null);
                        // Create ReportInfo objects for each user and subCategory combination
                        return entry.getValue().entrySet().stream()
                                .map(userEntry -> ReportInfo.builder()
                                        .mainCategory(mainCategory)
                                        .subCategory(subCategory)
                                        .spent(userEntry.getValue())
                                        .user(userEntry.getKey())
                                        .build());
                    })
                    .collect(Collectors.toList());
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
