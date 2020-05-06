package com.chiragbohet.ecommerce.Repositories;

import com.chiragbohet.ecommerce.Entities.ProductRelated.ProductVariation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductVariationRepository extends JpaRepository<ProductVariation, Long> {
}
