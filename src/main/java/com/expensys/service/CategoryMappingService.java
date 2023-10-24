package com.expensys.service;

import com.expensys.entity.CategoryMappingEntity;
import com.expensys.model.enums.Category;
import com.expensys.repository.CategoryMappingRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;

@Service
public class CategoryMappingService {
    CategoryMappingRepository categoryMappingRepository;
    HashMap<Category, HashSet<Category>> subCategoryToMainCategoryMappingMap;
    HashMap<Category, HashSet<Category>> getSubCategoryToMainCategoryMappingMap(){
        return null;
    }
    void addCategoryMapping(CategoryMappingEntity categoryMappingEntity){

    }

}
