package com.expensys.repository;

import com.expensys.entity.ExpenseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Long> {
    List<ExpenseEntity> findAll();

//    List<ExpenseEntity> findByMonthAndYear(String month, int year);

//    List<ExpenseEntity> findByMonthAndYearAndCategory(String month, int year, Category category);

    @Query("SELECT e FROM ExpenseEntity e WHERE e.date BETWEEN :yearStart AND :yearEnd")
    List<ExpenseEntity> findByDateBetween(@Param("yearStart") LocalDate yearStart, @Param("yearEnd") LocalDate yearEnd);
}
