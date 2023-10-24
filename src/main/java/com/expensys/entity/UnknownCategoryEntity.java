package com.expensys.entity;

import com.expensys.model.enums.Category;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

}
