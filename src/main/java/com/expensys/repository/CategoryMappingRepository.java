package com.expensys.repository;

import com.expensys.entity.CategoryMappingEntity;
import com.expensys.model.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface CategoryMappingRepository extends JpaRepository<CategoryMappingEntity, Long> {
    List<CategoryMappingEntity> findAll();
    CategoryMappingEntity save(CategoryMappingEntity categoryMappingEntity);
    List<Category> findDistinctMainCategoryByMainCategoryType(Category category);

}
