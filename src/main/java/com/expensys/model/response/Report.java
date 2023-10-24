package com.expensys.model.response;

import com.expensys.model.enums.Month;

import java.util.List;

public class Report {
    Month month;
    List<ReportInfo> reportInfo;

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
