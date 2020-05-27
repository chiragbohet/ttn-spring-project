package com.chiragbohet.ecommerce.repositories;

import com.chiragbohet.ecommerce.entities.ProductVariation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductVariationRepository extends JpaRepository<ProductVariation, Long> {

    @Query(value = "SELECT * FROM PRODUCT_VARIATION WHERE PRODUCT_ID = :productId", nativeQuery = true)
    List<ProductVariation> getAllProductVariationsByProductId(@Param("productId") Long id, Pageable page);
}
