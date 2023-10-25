package com.expensys.repository;

import com.expensys.entity.CategoryMappingEntity;
import com.expensys.model.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryMappingRepository extends JpaRepository<CategoryMappingEntity, Long> {
    List<CategoryMappingEntity> findAll();
    CategoryMappingEntity save(CategoryMappingEntity categoryMappingEntity);
    @Query("SELECT DISTINCT cme.mainCategory FROM CategoryMappingEntity cme WHERE cme.categoryType = :category")
    List<Category> findDistinctMainCategoryByMainCategoryType(Category category);

}
