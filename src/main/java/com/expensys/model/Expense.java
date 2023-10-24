package com.expensys.model;

import com.expensys.model.enums.Category;
import com.expensys.model.enums.Month;

public class Expense {
    Month month;
    String subCategory;
    Category category;
    Double spent;
    String spentBy;

    public Expense(Month month, String subCategory, Category category, Double spent, String spentBy) {
        this.month = month;
        this.subCategory = subCategory;
        this.category = category;
        this.spent = spent;
        this.spentBy = spentBy;
    }

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Double getSpent() {
        return spent;
    }

    public void setSpent(Double spent) {
        this.spent = spent;
    }

    public String getSpentBy() {
        return spentBy;
    }

    public void setSpentBy(String spentBy) {
        this.spentBy = spentBy;
    }
}
