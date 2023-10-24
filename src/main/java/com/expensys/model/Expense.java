package com.expensys.model;

import com.expensys.model.enums.Category;
import com.expensys.model.enums.Month;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Expense {
    Month month;
    String subCategory;
    Category category;
    Double spent;
    String spentBy;

}
