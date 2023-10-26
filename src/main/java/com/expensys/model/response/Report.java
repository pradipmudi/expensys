package com.expensys.model.response;

import com.expensys.model.enums.Month;

import java.util.List;

public class Report {
    Month month;
    Double totalSpendings;
    List<ReportInfo> reportInfo;

    public Report(Month month, List<ReportInfo> reportInfo) {
        this.month = month;
        this.reportInfo = reportInfo;
        this.totalSpendings = calculateTotalSpendings();
    }

    // Calculate the total spendings for the current month
    private Double calculateTotalSpendings() {
        Double total = 0.0;
        for (ReportInfo info : reportInfo) {
            if (info.getSpent() != null) {
                total += info.getSpent();
            }
        }
        return total;
    }

    public Double getTotalSpendings() {
        return totalSpendings;
    }

    public void setTotalSpendings(Double totalSpendings) {
        this.totalSpendings = totalSpendings;
    }

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public List<ReportInfo> getReportInfo() {
        return reportInfo;
    }

    public void setReportInfo(List<ReportInfo> reportInfo) {
        this.reportInfo = reportInfo;
    }

    @Override
    public String toString() {
        return "Report{" +
                "month=" + month +
                ", reportInfo=" + reportInfo +
                '}';
    }
}
