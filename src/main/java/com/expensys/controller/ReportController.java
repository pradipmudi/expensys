package com.expensys.controller;

import com.expensys.service.main.ExpenseManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("/report")
public class ReportController {
    Logger logger = LoggerFactory.getLogger(ReportController.class);
    private final ExpenseManagementService expenseManagementService;

    public ReportController(ExpenseManagementService expenseManagementService) {
        this.expenseManagementService = expenseManagementService;
    }

}
