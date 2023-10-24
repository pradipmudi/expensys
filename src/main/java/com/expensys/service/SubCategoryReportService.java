package com.expensys.service;

import com.expensys.model.Expense;
import com.expensys.model.request.ReportRequest;
import com.expensys.model.response.Report;

import java.util.List;

public class SubCategoryReportService implements ICategoryReportService{
    @Override
    public List<Report> prepareReport(ReportRequest reportRequest, List<Expense> expenseList) {
        return null;
    }
}
