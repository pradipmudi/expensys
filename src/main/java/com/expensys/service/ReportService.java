package com.expensys.service;

import com.expensys.model.Expense;
import com.expensys.model.enums.Category;
import com.expensys.model.enums.Month;
import com.expensys.model.request.ReportRequest;
import com.expensys.model.response.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;

@Service
public class ReportService {
    MainCategoryReportService mainCategoryReportService;
    SubCategoryReportService subCategoryReportService;
    ExpenseService expenseService;

    @Autowired
    public ReportService(MainCategoryReportService mainCategoryReportService, SubCategoryReportService subCategoryReportService, ExpenseService expenseService) {
        this.mainCategoryReportService = mainCategoryReportService;
        this.subCategoryReportService = subCategoryReportService;
        this.expenseService = expenseService;
    }

    public List<Report> getReport(ReportRequest reportRequest) {
        List<Expense> expenseList;
        if (Month.ALL.equals(reportRequest.getMonth())) {
            // get current year value
            int year = Year.now().getValue();
            expenseList = expenseService.getAllExpensesByYear(year);
        } else{
            expenseList = expenseService.getExpensesByMonth(reportRequest.getMonth());
        }
        
        return prepareReportFromExpenses(reportRequest, expenseList);
    }

    private List<Report> prepareReportFromExpenses(ReportRequest reportRequest, List<Expense> expenseList) {
        if(Category.MAIN.equals(reportRequest.getCategory())){
            return mainCategoryReportService.prepareReport(reportRequest, expenseList);
        }else{
            return subCategoryReportService.prepareReport(reportRequest, expenseList);
        }
    }

}
