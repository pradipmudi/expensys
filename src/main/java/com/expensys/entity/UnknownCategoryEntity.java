package com.expensys.entity;

import com.expensys.model.enums.Category;
import jakarta.persistence.*;

@Entity
@Table(name = "unknown_category")
public class UnknownCategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "item", nullable = false)
    String item;

    @Column(name = "category", nullable = false)
    Category category;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
