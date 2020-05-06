package com.chiragbohet.ecommerce.Repositories;

import com.chiragbohet.ecommerce.Entities.ProductRelated.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
