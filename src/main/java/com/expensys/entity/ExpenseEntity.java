package com.expensys.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "expenses")
public class ExpenseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "date", nullable = false)
    LocalDate date;

    @Column(name = "item", nullable = false)
    String item;

    @Column(name = "category", nullable = false)
    String category;

    @Column(name = "spent", nullable = false)
    Double spent;

    @Column(name = "spent_by", nullable = false)
    String spentBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
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

    @Override
    public String toString() {
        return "ExpenseEntity{" +
                "id=" + id +
                ", date=" + date +
                ", item='" + item + '\'' +
                ", category=" + category +
                ", spent=" + spent +
                ", spentBy='" + spentBy + '\'' +
                '}';
    }
}
