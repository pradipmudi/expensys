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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.expensys.constant.CategoryMappings.SUB_TO_MAIN_CATEGORY_MAPPINGS;
import static java.util.Objects.requireNonNullElseGet;

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

    public List<Expense> getExpensesByMonthAndYear(ReportRequest reportRequest) {
        List<ExpenseEntity> expenseEntityList = getExpenseEntitiesByMonthAndYear(reportRequest.getYear(), reportRequest.getMonth());
        return prepareExpenseListFromExpenseEntityList(expenseEntityList, reportRequest);
    }

    public List<ExpenseEntity> getExpenseEntitiesByMonthAndYear(Integer year, Month month){
        year = requireNonNullElseGet(year, () -> LocalDate.now().getYear());
        
        LocalDate[] dateRange = calculateDateRange(year, month);
        return expenseRepository.findByDateBetween(dateRange[0], dateRange[1]);
    }

    public List<ExpenseEntity> getExpensesByMonth(Integer year, Month month, Integer page, Integer itemsPerPage, String sortField, String sortOrder) {
        year = requireNonNullElseGet(year, () -> LocalDate.now().getYear());

        if (page == null || itemsPerPage == null) {
             return getExpenseEntitiesByMonthAndYear(year, month);
        }

        LocalDate[] dateRange = calculateDateRange(year, month);
        LocalDate dateStart = dateRange[0];
        LocalDate dateEnd = dateRange[1];

        Sort sort = Sort.by(Sort.Direction.DESC, "date");
        if (sortField != null && !sortField.isEmpty()) {
             Sort.Direction direction = "asc".equalsIgnoreCase(sortOrder) ? Sort.Direction.ASC : Sort.Direction.DESC;
             String entityField = mapSortField(sortField);
             sort = Sort.by(direction, entityField);
        } else {
             sort = sort.and(Sort.by(Sort.Direction.DESC, "id"));
        }

        Pageable pageable = PageRequest.of(page - 1, itemsPerPage, sort);
        Page<ExpenseEntity> expensePage = expenseRepository.findByDateBetween(dateStart, dateEnd, pageable);
        
        return expensePage.getContent();
    }

    private LocalDate[] calculateDateRange(Integer year, Month month) {
        LocalDate dateStart;
        LocalDate dateEnd;

        if (Month.ALL.equals(month)) {
            dateStart = LocalDate.of(year, 1, 1);
            dateEnd = LocalDate.of(year, 12, 31);
        } else {
            dateStart = LocalDate.of(year, Integer.parseInt(month.getMonthValue()), 1);
            dateEnd = dateStart.withDayOfMonth(dateStart.lengthOfMonth());
        }
        return new LocalDate[]{dateStart, dateEnd};
    }

    private String mapSortField(String frontendField) {
        if (frontendField == null) return "date";
        switch (frontendField.toLowerCase()) {
            case "item": return "item";
            case "category": return "category";
            case "spent": return "spent";
            case "spentby": return "spentBy";
            case "date": return "date";
            default: return "date";
        }
    }

    private List<Expense> prepareExpenseListFromExpenseEntityList(List<ExpenseEntity> expenseEntityList, ReportRequest reportRequest) {
        List<Expense> expenseList = new ArrayList<>();
        for (ExpenseEntity expenseEntity : expenseEntityList) {
            try {
                Category expenseCategory = Category.valueOf(expenseEntity.getCategory().toUpperCase().replaceAll("\\s", "_"));
                Category category = Category.MAIN.equals(reportRequest.getCategory()) ? SUB_TO_MAIN_CATEGORY_MAPPINGS.get(expenseCategory) : expenseCategory;
                Expense expense = new Expense(Month.valueOf(String.valueOf(expenseEntity.getDate().getMonth())), expenseEntity.getItem(), category, expenseEntity.getSpent(), expenseEntity.getSpentBy());
                expenseList.add(expense);
            } catch (IllegalArgumentException e) {
                logger.warn("Skipping expense with invalid category: {}", expenseEntity.getCategory());
            }
        }
        return expenseList;
    }

    List<Expense> getAllExpenses() {
        return prepareExpenseListFromExpenseEntityList(expenseRepository.findAll(), null);
    }

    public List<ExpenseEntity> getExpenseByDateRange(LocalDate startDate, LocalDate endDate){
        return expenseRepository.findByDateBetween(startDate, endDate);
    }

    public void saveExpense(NewExpense newExpense) {
        if (newExpense.getSpent() == null || newExpense.getSpent() < 0) {
            throw new IllegalArgumentException("Spent amount must be positive");
        }
        if (newExpense.getItem() == null || newExpense.getItem().trim().isEmpty()) {
            throw new IllegalArgumentException("Item name cannot be empty");
        }
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
        LocalDate yearStart = LocalDate.of(year, 1, 1);
        LocalDate yearEnd = LocalDate.of(year, 12, 31);
        List<ExpenseEntity> expenseEntityList = expenseRepository.findByDateBetween(yearStart, yearEnd);
        return prepareExpenseListFromExpenseEntityList(expenseEntityList, reportRequest);
    }
}
