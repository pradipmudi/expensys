package com.expensys.helper;


import com.expensys.model.Expense;
import com.expensys.model.enums.Category;
import com.expensys.model.enums.Month;
import com.expensys.model.enums.SpentBy;
import com.expensys.model.request.ReportRequest;
import com.expensys.model.response.Report;
import com.expensys.model.response.ReportInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

import static com.expensys.constant.CategoryMappings.SUB_TO_MAIN_CATEGORY_MAPPINGS;

public class ExpenseToReportConvertor {
    private static final Logger logger = LoggerFactory.getLogger(ExpenseToReportConvertor.class);
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
                    .subCategory(expense.getCategory())
                    .mainCategory(SUB_TO_MAIN_CATEGORY_MAPPINGS.get(expense.getCategory()))
                    .spent(expense.getSpent())
                    .spentBy(SpentBy.ALL.equals(spentBy) ? spentBy.toString() : expense.getSpentBy())
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
                .filter(reportInfo -> reportInfo.getMainCategory() != null)
                .toList();

        if (SpentBy.ALL.equals(reportRequest.getSpentBy())) {
            return filteredList.stream()
                    // Step 1: Group ReportInfo objects by subCategory and sum their spent amounts
                    .collect(Collectors.groupingBy(
                            ReportInfo::getSubCategory,  // Group by subCategory
                            Collectors.summingDouble(ReportInfo::getSpent)  // Sum the spent amounts
                    ))
                    .entrySet().stream()
                    // Step 2: Transform the grouped data into ReportInfo objects
                    .map(entry -> ReportInfo.builder()
                            .subCategory(entry.getKey())  // Set the subCategory
                            .mainCategory(SUB_TO_MAIN_CATEGORY_MAPPINGS.get(entry.getKey()))  // Determine mainCategory based on mapping
                            .spentBy(SpentBy.ALL.getSpentBy())  // Set spentBy
                            .spent(entry.getValue())  // Set the total spent amount
                            .build()  // Create a ReportInfo object
                    )
                    // Step 3: Sort the ReportInfo objects by spent amount in descending order
                    .sorted(Comparator.comparing(ReportInfo::getSpent).reversed())
                    .toList();  // Convert the result to a list

        } else if (SpentBy.USER.equals(reportRequest.getSpentBy())) {
            return reportInfoList.stream()
                    // Step 1: Group ReportInfo objects by key (subCategory or mainCategory)
                    .collect(Collectors.toMap(
                            reportInfo -> reportRequest.getCategory().equals(Category.SUB) ? reportInfo.getSubCategorySpentByKey() : reportInfo.getMainCategorySpentByKey(),
                            reportInfo -> reportInfo,
                            // Step 2: Merge ReportInfo objects with the same key
                            (reportInfo1, reportInfo2) -> ReportInfo.builder()
                                    .subCategory(reportInfo2.getSubCategory())  // Take subCategory from the second ReportInfo
                                    .mainCategory(reportInfo2.getMainCategory())  // Take mainCategory from the second ReportInfo
                                    .spent(reportInfo1.getSpent() + reportInfo2.getSpent())  // Sum the spent amounts
                                    .spentBy(reportInfo2.getSpentBy())  // Take spentBy from the second ReportInfo
                                    .build()  // Create a new merged ReportInfo object
                    ))
                    .values()
                    // Step 3: Convert the map values (merged ReportInfo objects) to a stream
                    .stream()
                    // Step 4: Sort the ReportInfo objects by spent amount in descending order
                    .sorted(Comparator.comparing(ReportInfo::getSpent).reversed())
                    // Step 5: Convert the sorted stream to a list
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
