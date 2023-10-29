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
            // Aggregate spent amount by subCategory when SpentBy is by "CATEGORY"
            Map<Category, Double> spentBycategory = filteredList.stream()
                    .collect(Collectors.groupingBy(
                            ReportInfo::getSubCategory,  // Group by subCategory
                            Collectors.summingDouble(ReportInfo::getSpent))); // Sum the spent amounts
            // Create a new list of ReportInfo objects based on Task 1
            return spentBycategory.entrySet().stream()
                    .map(entry -> ReportInfo.builder()
                            .subCategory(entry.getKey())
                            .mainCategory(SUB_TO_MAIN_CATEGORY_MAPPINGS.get(entry.getKey()))
                            .spentBy(SpentBy.ALL.getSpentBy())
                            .spent(entry.getValue())
                            .build())
                    .sorted(Comparator.comparing(ReportInfo::getSpent).reversed())
                    .toList();
        } else if (SpentBy.USER.equals(reportRequest.getSpentBy())) {
            HashMap<String, ReportInfo> reportInfoMap = new HashMap<>();
            for(ReportInfo reportInfo : reportInfoList){
                String currentKey = reportRequest.getCategory().equals(Category.SUB) ? reportInfo.getSubCategorySpentByKey() : reportInfo.getMainCategorySpentByKey();
                if(reportInfoMap.containsKey(currentKey)){
                    double currentSpentAmount = reportInfo.getSpent();
                    reportInfo.setSpent(reportInfoMap.get(currentKey).getSpent()+currentSpentAmount);
                    reportInfoMap.put(currentKey, reportInfo);
                }else{
                    reportInfoMap.put(currentKey, reportInfo);
                }
            }
            logger.info("reportRequest -> {}", reportRequest);
            for (String key : reportInfoMap.keySet()){
                logger.info("\nkey -> {}, reportInfo -> {}", key, reportInfoMap.get(key));
            }

            return reportInfoMap.values().stream()
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
