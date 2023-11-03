package com.expensys.service;

import com.expensys.entity.ExpenseEntity;
import com.expensys.model.Expense;
import com.expensys.model.enums.Category;
import com.expensys.model.enums.Month;
import com.expensys.model.request.NewExpense;
import com.expensys.model.request.ReportRequest;
import com.expensys.repository.ExpenseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.expensys.constant.CategoryMappings.SUB_TO_MAIN_CATEGORY_MAPPINGS;

@Service
public class ExpenseService {
    private static final Logger logger = LoggerFactory.getLogger(ExpenseService.class);
    CategoryMappingService categoryMappingService;
    ExpenseRepository expenseRepository;

    @Autowired
    public ExpenseService(CategoryMappingService categoryMappingService, ExpenseRepository expenseRepository) {
        this.categoryMappingService = categoryMappingService;
        this.expenseRepository = expenseRepository;
    }

    public List<Expense> getExpensesByMonth(Month month, ReportRequest reportRequest) {
        List<ExpenseEntity> expenseEntityList = getExpenseEntitiesByMonth(month);
        return prepareExpenseListFromExpenseEntityList(expenseEntityList, reportRequest);
    }

    public List<ExpenseEntity> getExpenseEntitiesByMonth(Month month){
        int year = LocalDate.now().getYear(); // You can use the current year or specify a year as needed
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Create the LocalDate for the start of the month
        LocalDate dateStart = LocalDate.parse(LocalDate.of(year, Integer.valueOf(month.getMonthValue()), 1).format(formatter));

        // Create the LocalDate for the end of the month
        LocalDate dateEnd = LocalDate.parse(dateStart.plusMonths(1).minusDays(1).format(formatter));
        return expenseRepository.findByDateBetween(dateStart, dateEnd);
    }

    private List<Expense> prepareExpenseListFromExpenseEntityList(List<ExpenseEntity> expenseEntityList, ReportRequest reportRequest) {
        List<Expense> expenseList = new ArrayList<>();
        for (ExpenseEntity expenseEntity : expenseEntityList) {
            Category expenseCategory = Category.valueOf(expenseEntity.getCategory().toUpperCase().replaceAll("\\s", "_"));
            Category category = Category.MAIN.equals(reportRequest.getCategory()) ? SUB_TO_MAIN_CATEGORY_MAPPINGS.get(expenseCategory) : expenseCategory;
            Expense expense = new Expense(Month.valueOf(String.valueOf(expenseEntity.getDate().getMonth())), expenseEntity.getItem(), category, expenseEntity.getSpent(), expenseEntity.getSpentBy());
            expenseList.add(expense);
        }
//        for (Expense expense : expenseList) {
//            logger.info("expense -> {}",expense);
//        }
        return expenseList;
    }

    List<Expense> getAllExpenses() {
        return prepareExpenseListFromExpenseEntityList(expenseRepository.findAll(), null);
    }

    public List<ExpenseEntity> getExpenseByDateRange(LocalDate startDate, LocalDate endDate){
        return expenseRepository.findByDateBetween(startDate, endDate);
    }

    public void saveExpense(NewExpense newExpense) {
        expenseRepository.save(prepareExpenseEntity(newExpense));
    }

    private ExpenseEntity prepareExpenseEntity(NewExpense newExpense) {
        ExpenseEntity expenseEntity = new ExpenseEntity();
        expenseEntity.setDate(newExpense.getDate());
        expenseEntity.setItem(newExpense.getItem());
        expenseEntity.setCategory(newExpense.getCategory().toString());
        expenseEntity.setSpent(newExpense.getSpent());
        expenseEntity.setSpentBy(newExpense.getSpentBy());
        return expenseEntity;
    }

    public List<Expense> getAllExpensesByYear(int year, ReportRequest reportRequest) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate yearStart = LocalDate.parse(LocalDate.of(year, 1, 1).format(formatter)); // January 1st of the specified year
        LocalDate yearEnd = LocalDate.parse(LocalDate.of(year, 12, 31).format(formatter)); // December 31st of the specified year
        List<ExpenseEntity> expenseEntityList = expenseRepository.findByDateBetween(yearStart, yearEnd);
        return prepareExpenseListFromExpenseEntityList(expenseEntityList, reportRequest);
    }
}
