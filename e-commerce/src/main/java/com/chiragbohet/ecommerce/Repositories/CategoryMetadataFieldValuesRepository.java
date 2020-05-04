package com.chiragbohet.ecommerce.Repositories;

import com.chiragbohet.ecommerce.Entities.CategoryRelated.CategoryMetadataFieldValues;
import com.chiragbohet.ecommerce.Entities.CategoryRelated.CategoryMetadataFieldValuesId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface CategoryMetadataFieldValuesRepository extends JpaRepository<CategoryMetadataFieldValues, CategoryMetadataFieldValuesId> {

    Set<CategoryMetadataFieldValues> findAllByCategoryId(Long categoryId);
    Set<CategoryMetadataFieldValues> findAllByCategoryMetadataFieldId(Long categoryMetadataFieldId);


}
