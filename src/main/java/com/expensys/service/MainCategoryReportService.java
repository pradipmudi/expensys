package com.expensys.service;

import com.expensys.model.Expense;
import com.expensys.model.enums.Category;
import com.expensys.model.request.ReportRequest;
import com.expensys.model.response.Report;
import com.expensys.repository.CategoryMappingRepository;
import com.expensys.convertor.ExpenseToReportConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MainCategoryReportService implements ICategoryReportService {
    private final CategoryMappingRepository categoryMappingRepository;

    @Autowired
    public MainCategoryReportService(CategoryMappingRepository categoryMappingRepository) {
        this.categoryMappingRepository = categoryMappingRepository;
    }

    @Override
    public List<Report> prepareReport(ReportRequest reportRequest, List<Expense> expenseList) {
        Set<Category> mainCategories = categoryMappingRepository.findDistinctMainCategoryByMainCategoryType(Category.MAIN).stream().collect(Collectors.toSet());
        expenseList = expenseList.stream().filter(expense -> mainCategories.contains(expense.getCategory())).collect(Collectors.toList());
        return ExpenseToReportConvertor.getInstance().prepareReportListFromExpenseList(expenseList, reportRequest);
    }

}
