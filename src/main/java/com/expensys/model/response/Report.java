package com.expensys.model.response;

import com.expensys.model.enums.Month;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@Setter
public class Report {
    Month month;
    List<ReportInfo> reportInfo;
}
