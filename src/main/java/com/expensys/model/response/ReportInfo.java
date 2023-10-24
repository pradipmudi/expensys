package com.expensys.model.response;

import com.expensys.model.enums.Category;
import lombok.Builder;
import lombok.ToString;

@Builder
@ToString
public class ReportInfo {
    Category mainCategory;
    String subCategory;// ignore in response when null
    Double spent;
    String user;

}
