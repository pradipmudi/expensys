package com.expensys.controller;

import com.expensys.model.request.NewExpense;
import com.expensys.service.main.ExpenseManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/expense")
public class ExpenseManagementController {
    Logger logger = LoggerFactory.getLogger(ExpenseManagementController.class);
    private final ExpenseManagementService expenseManagementService;

    @Autowired
    public ExpenseManagementController(ExpenseManagementService expenseManagementService) {
        this.expenseManagementService = expenseManagementService;
    }

    @CrossOrigin
    @PostMapping("/save")
    public ResponseEntity<HttpStatus> saveExpense(@RequestBody NewExpense newExpense){
        logger.info("newExpense -> {}",newExpense);
//        expenseManagementService.addExpense(newExpense);
        return new ResponseEntity<>(expenseManagementService.addExpense(newExpense) ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
