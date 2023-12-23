package com.expensys.service;

import com.expensys.helper.ExpenseToReportConvertor;
import com.expensys.model.Expense;
import com.expensys.model.request.ReportRequest;
import com.expensys.model.response.MonthlyReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.expensys.constant.CategoryMappings.SUB_CATEGORIES;

@Service
public class SubCategoryReportService implements ICategoryReportService{
    private static final Logger logger = LoggerFactory.getLogger(SubCategoryReportService.class);

    @Override
    public List<MonthlyReport> prepareReport(ReportRequest reportRequest, List<Expense> expenseList) {
        expenseList = expenseList.stream().filter(expense -> SUB_CATEGORIES.contains(expense.getCategory())).toList();
        return ExpenseToReportConvertor.getInstance().prepareReportListFromExpenseList(expenseList, reportRequest);
    }
}
