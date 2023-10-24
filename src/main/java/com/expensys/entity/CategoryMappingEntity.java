package com.expensys.entity;

import com.expensys.model.enums.Category;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

}
