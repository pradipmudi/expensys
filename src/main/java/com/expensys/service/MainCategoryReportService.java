package com.expensys.service;

import com.expensys.helper.ExpenseToReportConvertor;
import com.expensys.model.Expense;
import com.expensys.model.request.ReportRequest;
import com.expensys.model.response.Report;
import com.expensys.repository.CategoryMappingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.expensys.constant.CategoryMappings.MAIN_CATEGORIES;

@Service
public class MainCategoryReportService implements ICategoryReportService {
    private static final Logger logger = LoggerFactory.getLogger(MainCategoryReportService.class);
    private final CategoryMappingRepository categoryMappingRepository;

    @Autowired
    public MainCategoryReportService(CategoryMappingRepository categoryMappingRepository) {
        this.categoryMappingRepository = categoryMappingRepository;
    }

    @Override
    public List<Report> prepareReport(ReportRequest reportRequest, List<Expense> expenseList) {

        expenseList = expenseList.stream().filter(expense -> MAIN_CATEGORIES.contains(expense.getCategory())).toList();
        return ExpenseToReportConvertor.getInstance().prepareReportListFromExpenseList(expenseList, reportRequest);
    }

}
