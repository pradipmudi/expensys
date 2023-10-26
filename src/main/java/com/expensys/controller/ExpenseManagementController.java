package com.expensys.controller;

import com.expensys.model.request.ReportRequest;
import com.expensys.model.response.Report;
import com.expensys.service.main.ExpenseManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/report")
public class ExpenseManagementController {
    Logger logger = LoggerFactory.getLogger(ExpenseManagementController.class);
    private final ExpenseManagementService expenseManagementService;

    public ExpenseManagementController(ExpenseManagementService expenseManagementService) {
        this.expenseManagementService = expenseManagementService;
    }

    @GetMapping
    public ResponseEntity<List<Report>> getReport(@ModelAttribute ReportRequest reportRequest){
        List<Report> reportList = expenseManagementService.getReport(reportRequest);
        return new ResponseEntity<>(reportList, HttpStatus.OK);
    }


}
