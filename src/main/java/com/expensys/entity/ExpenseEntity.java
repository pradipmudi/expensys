package com.expensys.entity;

import com.expensys.model.enums.Category;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "expenses")
public class ExpenseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "date", nullable = false)
    LocalDate date;

    @Column(name = "item", nullable = false)
    String item;

    @Column(name = "category", nullable = false)
    Category category;

    @Column(name = "spent", nullable = false)
    Double spent;

    @Column(name = "spent_by", nullable = false)
    String spentBy;

}
