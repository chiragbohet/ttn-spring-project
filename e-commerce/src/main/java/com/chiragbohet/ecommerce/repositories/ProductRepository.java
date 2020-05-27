package com.chiragbohet.ecommerce.repositories;

import com.chiragbohet.ecommerce.entities.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "SELECT * FROM PRODUCT WHERE SELLER_ID = :sellerId", nativeQuery = true)
    List<Product> getAllProductsBySellerId(@Param("sellerId") Long sellerId, Pageable pageable);

    // TODO : Filter products based on non deletion (soft delete)
    // Belongs to a leaf category
    @Query(value = "SELECT * FROM PRODUCT WHERE CATEGORY_ID = :categoryId AND CATEGORY_ID NOT IN (SELECT PARENT_ID FROM CATEGORY WHERE PARENT_ID IS NOT NULL)", nativeQuery = true)
    List<Product> getAllProductsByLeafCategoryId(@Param("categoryId") Long categoryId, Pageable pageable);

    // TODO : Filter products based on non deletion (soft delete)
    @Query(value = "SELECT * FROM PRODUCT WHERE CATEGORY_ID NOT IN (SELECT PARENT_ID FROM CATEGORY WHERE PARENT_ID IS NOT NULL)", nativeQuery = true)
    List<Product> getAllLeafNodeCategoryProducts(Pageable pageable);

    @Query(value = "SELECT * FROM PRODUCT WHERE CATEGORY_ID = :categoryId AND IS_ACTIVE = TRUE ", nativeQuery = true)
    List<Product> getAllActiveProductsByCategoryId(@Param("categoryId") Long categoryId, Pageable pageable);

    @Query(value = "SELECT * FROM PRODUCT WHERE CATEGORY_ID = :categoryId AND IS_ACTIVE = TRUE ", nativeQuery = true)
    List<Product> getAllActiveProductsByCategoryId(@Param("categoryId") Long categoryId);

    // list of all non deleted, active products with their cumulative stock count
    @Query(value = "SELECT PRODUCT.ID, PRODUCT.NAME, SUM(PRODUCT_VARIATION.QUANTITY_AVAILABLE) FROM PRODUCT LEFT JOIN PRODUCT_VARIATION ON PRODUCT.ID = PRODUCT_VARIATION.PRODUCT_ID WHERE NOT PRODUCT.IS_DELETED AND PRODUCT.IS_ACTIVE GROUP BY PRODUCT.ID", nativeQuery = true)
    List<Object[]> getAllActiveProductsWithCumulativeStockCount();


    Optional<Product> findByName(String name);

}
