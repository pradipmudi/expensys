package com.expensys.service;

import com.expensys.entity.CategoryMappingEntity;
import com.expensys.model.enums.Category;
import com.expensys.repository.CategoryMappingRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;

@Service
public class CategoryMappingService {
    CategoryMappingRepository categoryMappingRepository;
    HashMap<Category, HashSet<Category>> subCategoryToMainCategoryMappingMap;
    @PostConstruct
    void prepareSubCategoryToMainCategoryMappingMap(){
//        categoryMappingRepository.findAll();
    }
    HashMap<Category, HashSet<Category>> getSubCategoryToMainCategoryMappingMap(){
        return new HashMap<>();
    }
    void addCategoryMapping(CategoryMappingEntity categoryMappingEntity){
        categoryMappingRepository.save(categoryMappingEntity);
    }

}
