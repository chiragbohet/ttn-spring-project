package com.chiragbohet.ecommerce.repositories;

import com.chiragbohet.ecommerce.entities.CategoryMetadataField;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryMetadataFieldRepository extends JpaRepository<CategoryMetadataField, Long> {

    CategoryMetadataField findByName(String name);

}
