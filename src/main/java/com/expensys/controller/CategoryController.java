package com.expensys.controller;

import com.expensys.model.enums.Category;
import com.expensys.service.CategoryMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryMappingService categoryMappingService;

    @Autowired
    public CategoryController(CategoryMappingService categoryMappingService) {
        this.categoryMappingService = categoryMappingService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Map<String, String>>> getAllCategories() {
        return ResponseEntity.ok(categoryMappingService.getAllCategories());
    }
}
