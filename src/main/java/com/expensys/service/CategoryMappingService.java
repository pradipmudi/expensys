package com.expensys.service;

import com.expensys.entity.CategoryMappingEntity;
import com.expensys.model.enums.Category;
import com.expensys.repository.CategoryMappingRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.expensys.constant.CategoryMappings.SUB_CATEGORIES;

@Service
public class CategoryMappingService {
    
    @Autowired
    CategoryMappingRepository categoryMappingRepository;
    
    HashMap<Category, HashSet<Category>> subCategoryToMainCategoryMappingMap;

    @PostConstruct
    void prepareSubCategoryToMainCategoryMappingMap(){
        // Future: Load from DB
        // categoryMappingRepository.findAll();
    }

    HashMap<Category, HashSet<Category>> getSubCategoryToMainCategoryMappingMap(){
        return new HashMap<>();
    }

    void addCategoryMapping(CategoryMappingEntity categoryMappingEntity){
        categoryMappingRepository.save(categoryMappingEntity);
    }

    public List<Map<String, String>> getAllCategories() {
        // Currently returning the hardcoded SUB_CATEGORIES. 
        // In the future, this will merge DB categories + Enum categories.
        return SUB_CATEGORIES.stream()
                .map(category -> {
                    Map<String, String> map = new HashMap<>();
                    map.put("key", category.name());
                    map.put("label", formatCategoryName(category));
                    return map;
                })
                .sorted(Comparator.comparing(m -> m.get("label")))
                .collect(Collectors.toList());
    }

    private String formatCategoryName(Category category) {
        // Helper to make enum names pretty (e.g., VEGETABLES_FRUITS -> Vegetables Fruits)
        // You can also use your existing mapping logic here if you want specific emojis
        String name = category.name().toLowerCase().replace("_", " ");
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }
}
