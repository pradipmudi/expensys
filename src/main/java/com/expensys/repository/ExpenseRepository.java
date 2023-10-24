package com.expensys.repository;

import com.expensys.entity.ExpenseEntity;
import com.expensys.model.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Long> {
    List<ExpenseEntity> findAll();
    List<ExpenseEntity> findByMonth(String month);

    List<ExpenseEntity> findByYear(int year);

    List<ExpenseEntity> findByMonthAndYear(String month, int year);

    List<ExpenseEntity> findByMonthAndYearAndCategory(String month, int year, Category category);
}
