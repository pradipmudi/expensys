package com.expensys.entity;

import com.expensys.model.enums.Category;
import jakarta.persistence.*;

@Entity
@Table(name = "category_mappings")
public class CategoryMappingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "sub_category", nullable = false)
    String subCategory;

    @Column(name = "main_category", nullable = false)
    Category mainCategory;

    @Column(name = "main_category_type", nullable = false)
    Category categoryType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public Category getMainCategory() {
        return mainCategory;
    }

    public void setMainCategory(Category mainCategory) {
        this.mainCategory = mainCategory;
    }

    public Category getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(Category categoryType) {
        this.categoryType = categoryType;
    }
}
