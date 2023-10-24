package com.expensys.model.response;

import com.expensys.model.enums.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@ToString
@Getter
public class ReportInfo {
    Category mainCategory;
    String subCategory;// ignore in response when null
    Double spent;
    String user;
}
