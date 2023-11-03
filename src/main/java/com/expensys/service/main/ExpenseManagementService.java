package com.expensys.service.main;

import com.expensys.entity.ExpenseEntity;
import com.expensys.model.enums.Month;
import com.expensys.model.request.NewExpense;
import com.expensys.model.request.ReportRequest;
import com.expensys.model.response.MonthlyReport;
import com.expensys.service.ExpenseService;
import com.expensys.service.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    public List<MonthlyReport> getReport(ReportRequest reportRequest) {
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

    public List<ExpenseEntity> getExpenseByDateRange(LocalDate startDate, LocalDate endDate){
        return expenseService.getExpenseByDateRange(startDate, endDate);
    }

    public List<ExpenseEntity> getExpensesByMonth(Month month){
        return expenseService.getExpenseEntitiesByMonth(month);
    }

}
