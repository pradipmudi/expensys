package com.expensys.service;

import com.expensys.entity.ExpenseEntity;
import com.expensys.model.Expense;
import com.expensys.model.enums.Category;
import com.expensys.model.enums.Month;
import com.expensys.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExpenseService {
    CategoryMappingService categoryMappingService;
    ExpenseRepository expenseRepository;

    @Autowired
    public ExpenseService(CategoryMappingService categoryMappingService, ExpenseRepository expenseRepository) {
        this.categoryMappingService = categoryMappingService;
        this.expenseRepository = expenseRepository;
    }

    public List<Expense> getExpensesByMonth(Month month) {
        int year = LocalDate.now().getYear(); // You can use the current year or specify a year as needed
        LocalDate monthStart = LocalDate.of(year, Integer.valueOf(month.getMonthValue()), 1);
        LocalDate monthEnd = monthStart.plusMonths(1).minusDays(1);
        List<ExpenseEntity> expenseEntityList = expenseRepository.findByDateBetween(monthStart, monthEnd);
        return prepareExpenseListFromExpenseEntityList(expenseEntityList);
    }

    private List<Expense> prepareExpenseListFromExpenseEntityList(List<ExpenseEntity> expenseEntityList) {
        List<Expense> expenseList = new ArrayList<>();
        for (ExpenseEntity expenseEntity : expenseEntityList) {
            Expense expense = new Expense(Month.valueOf(String.valueOf(expenseEntity.getDate().getMonth())), expenseEntity.getItem(), expenseEntity.getCategory(), expenseEntity.getSpent(), expenseEntity.getSpentBy());
            expenseList.add(expense);
        }
        return expenseList;
    }

    List<Expense> getAllExpenses() {
        return prepareExpenseListFromExpenseEntityList(expenseRepository.findAll());
    }

    public void saveExpense(LocalDate date, String item, Category category, Double spent, String spentBy) {
        expenseRepository.save(prepareExpenseEntity(date, item, category, spent, spentBy));
    }

    private ExpenseEntity prepareExpenseEntity(LocalDate date, String item, Category category, Double spent, String spentBy) {
        ExpenseEntity expenseEntity = new ExpenseEntity();
        expenseEntity.setDate(date);
        expenseEntity.setItem(item);
        expenseEntity.setCategory(category);
        expenseEntity.setSpent(spent);
        expenseEntity.setSpentBy(spentBy);
        return expenseEntity;
    }

    public List<Expense> getAllExpensesByYear(int year) {
        LocalDate yearStart = LocalDate.of(year, 1, 1); // January 1st of the specified year
        LocalDate yearEnd = LocalDate.of(year, 12, 31); // December 31st of the specified year
        List<ExpenseEntity> expenseEntityList = expenseRepository.findByDateBetween(yearStart, yearEnd);
        return prepareExpenseListFromExpenseEntityList(expenseEntityList);
    }
}
