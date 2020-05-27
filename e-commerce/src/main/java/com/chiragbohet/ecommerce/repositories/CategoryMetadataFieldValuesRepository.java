package com.chiragbohet.ecommerce.repositories;

import com.chiragbohet.ecommerce.entities.CategoryMetadataFieldValues;
import com.chiragbohet.ecommerce.entities.CategoryMetadataFieldValuesId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface CategoryMetadataFieldValuesRepository extends JpaRepository<CategoryMetadataFieldValues, CategoryMetadataFieldValuesId> {

    Set<CategoryMetadataFieldValues> findAllByCategoryId(Long categoryId);
    Set<CategoryMetadataFieldValues> findAllByCategoryMetadataFieldId(Long categoryMetadataFieldId);


}
