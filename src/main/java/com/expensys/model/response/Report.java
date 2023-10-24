package com.expensys.model.response;

import com.expensys.model.enums.Month;
import lombok.ToString;

import java.util.List;

@ToString
public class Report {
    Month month;
    List<ReportInfo> reportInfo;

}
