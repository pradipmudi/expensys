package com.expensys.repository;

import com.expensys.entity.CategoryMappingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryMappingRepository extends JpaRepository<CategoryMappingEntity, Long> {
    List<CategoryMappingEntity> findAll();
    CategoryMappingEntity save(CategoryMappingEntity categoryMappingEntity);

}
