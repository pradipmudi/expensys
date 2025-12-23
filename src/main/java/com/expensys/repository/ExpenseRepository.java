package com.expensys.repository;

import com.expensys.entity.ExpenseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Long> {
    List<ExpenseEntity> findAll();

    @Query("SELECT e FROM ExpenseEntity e WHERE e.date BETWEEN :dateStart AND :dateEnd")
    List<ExpenseEntity> findByDateBetween(@Param("dateStart") LocalDate dateStart, @Param("dateEnd") LocalDate dateEnd);

    @Query("SELECT e FROM ExpenseEntity e WHERE e.date BETWEEN :dateStart AND :dateEnd")
    Page<ExpenseEntity> findByDateBetween(@Param("dateStart") LocalDate dateStart, @Param("dateEnd") LocalDate dateEnd, Pageable pageable);
}
