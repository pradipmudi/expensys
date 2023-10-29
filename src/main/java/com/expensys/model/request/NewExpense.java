package com.expensys.model.request;

import com.expensys.model.enums.Category;

import java.time.LocalDate;

public class NewExpense {
    LocalDate date;
    String item;
    Category category;
    Double spent;
    String spentBy;

    public NewExpense() {
    }

    public NewExpense(LocalDate date, String item, Category category, Double spent, String spentBy) {
        this.date = date;
        this.item = item;
        this.category = category;
        this.spent = spent;
        this.spentBy = spentBy;
    }

    public LocalDate getDate() {
        return date;
    }

    public Category getCategory() {
        return category;
    }


    public Double getSpent() {
        return spent;
    }

    public String getSpentBy() {
        return spentBy;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setCategory(Category category) {
        this.category = category;
    }


    public void setSpent(Double spent) {
        this.spent = spent;
    }

    public void setSpentBy(String spentBy) {
        this.spentBy = spentBy;
    }

    @Override
    public String toString() {
        return "NewExpense{" +
                "date=" + date +
                ", item='" + item + '\'' +
                ", category=" + category +
                ", spent=" + spent +
                ", spentBy='" + spentBy + '\'' +
                '}';
    }
}
