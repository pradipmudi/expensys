package com.expensys.service.main;

import com.expensys.model.request.NewExpense;
import com.expensys.model.request.ReportRequest;
import com.expensys.model.response.Report;
import com.expensys.service.ExpenseService;
import com.expensys.service.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseManagementService {
    Logger logger = LoggerFactory.getLogger(ExpenseManagementService.class);
    ReportService reportService;
    ExpenseService expenseService;

    @Autowired
    public ExpenseManagementService(ReportService reportService, ExpenseService expenseService) {
        this.reportService = reportService;
        this.expenseService = expenseService;
    }

    public List<Report> getReport(ReportRequest reportRequest) {
        return reportService.getReport(reportRequest);
    }

    public boolean addExpense(NewExpense newExpense) {
        try {
            expenseService.saveExpense(newExpense);
        }catch (Exception e){
            return false;
        }
        return true;
    }

}
