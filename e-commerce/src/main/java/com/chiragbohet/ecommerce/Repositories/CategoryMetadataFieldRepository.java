package com.chiragbohet.ecommerce.Repositories;

import com.chiragbohet.ecommerce.Entities.CategoryRelated.CategoryMetadataField;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryMetadataFieldRepository extends JpaRepository<CategoryMetadataField, Long> {

    CategoryMetadataField findByName(String name);

}
