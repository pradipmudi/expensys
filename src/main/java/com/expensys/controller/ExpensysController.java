package com.expensys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExpensysController {
    @GetMapping("/")
    public String index() {
        return "index"; // This refers to the template name without the extension
    }
    @GetMapping("/new")
    public String addExpense(){
        return "addexpense";
    }
}
