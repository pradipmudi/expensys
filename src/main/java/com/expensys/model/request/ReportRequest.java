package com.expensys.model.request;

import com.expensys.model.enums.Category;
import com.expensys.model.enums.Month;
import com.expensys.model.enums.SpentBy;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReportRequest {
    Month month;
    Category category;
    SpentBy spentBy;
    Boolean showSpendByUserPercentage;
    int year;
}
