package com.expensys.repository;

import com.expensys.entity.UnknownCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnknownCategoryRepository extends JpaRepository<UnknownCategoryEntity, Long> {
    UnknownCategoryEntity save(UnknownCategoryEntity unknownCategoryEntity);
}
