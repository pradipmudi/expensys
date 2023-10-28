package com.expensys.model.request;

import com.expensys.model.enums.Category;

import java.time.LocalDate;

public class NewExpense {
    LocalDate date;
    String item;
    Category category;
    Double spent;
    String user;

    public NewExpense() {
    }

    public NewExpense(LocalDate date, String item, Category category, Double spent, String user) {
        this.date = date;
        this.item = item;
        this.category = category;
        this.spent = spent;
        this.user = user;
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

    public String getUser() {
        return user;
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

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "NewExpense{" +
                "date=" + date +
                ", item='" + item + '\'' +
                ", category=" + category +
                ", spent=" + spent +
                ", user='" + user + '\'' +
                '}';
    }
}
