package com.expensys.repository;

import com.expensys.entity.UnknownCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnknownCategoryRepository extends JpaRepository<UnknownCategoryEntity, Long> {
    Object save(UnknownCategoryEntity unknownCategoryEntity);
}
