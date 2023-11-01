package com.expensys.service;

import com.expensys.model.Expense;
import com.expensys.model.request.ReportRequest;
import com.expensys.model.response.MonthlyReport;

import java.util.List;

public interface ICategoryReportService {
    List<MonthlyReport> prepareReport(ReportRequest reportRequest, List<Expense> expenseList);
}
