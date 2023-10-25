package com.expensys.model.request;

import com.expensys.model.enums.Category;
import com.expensys.model.enums.Month;
import com.expensys.model.enums.SpentBy;

public class ReportRequest {
    Month month;
    Category category;
    SpentBy spentBy;
    Boolean showSpendByUserPercentage;
    int year;

    public ReportRequest(Month month, Category category, SpentBy spentBy, Boolean showSpendByUserPercentage, int year) {
        this.month = month;
        this.category = category;
        this.spentBy = spentBy;
        this.showSpendByUserPercentage = showSpendByUserPercentage;
        this.year = year;
    }

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public SpentBy getSpentBy() {
        return spentBy;
    }

    public void setSpentBy(SpentBy spentBy) {
        this.spentBy = spentBy;
    }

    public Boolean getShowSpendByUserPercentage() {
        return showSpendByUserPercentage;
    }

    public void setShowSpendByUserPercentage(Boolean showSpendByUserPercentage) {
        this.showSpendByUserPercentage = showSpendByUserPercentage;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "ReportRequest{" +
                "month=" + month +
                ", category=" + category +
                ", spentBy=" + spentBy +
                ", showSpendByUserPercentage=" + showSpendByUserPercentage +
                ", year=" + year +
                '}';
    }
}
