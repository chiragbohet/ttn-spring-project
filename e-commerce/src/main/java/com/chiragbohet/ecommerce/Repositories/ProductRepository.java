package com.chiragbohet.ecommerce.Repositories;

import com.chiragbohet.ecommerce.Entities.ProductRelated.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "SELECT * FROM PRODUCT WHERE SELLER_ID = :sellerId", nativeQuery = true)
    List<Product> getAllProductsBySellerId(@Param("sellerId") Long sellerId, Pageable pageable);

    // TODO : Filter products based on non deletion (soft delete)
    @Query(value = "SELECT * FROM PRODUCT WHERE CATEGORY_ID = :categoryId AND ID IN (SELECT Product_ID FROM PRODUCT_productVariationSet)", nativeQuery = true)
    List<Product> getAllProductsByCategoryId(@Param("categoryId") Long categoryId, Pageable pageable);

    // TODO : Filter products based on non deletion (soft delete)
    @Query(value = "SELECT * FROM PRODUCT WHERE CATEGORY_ID NOT IN (SELECT Category_ID FROM CATEGORY_subCategoriesSet)", nativeQuery = true)
    List<Product> getAllLeafNodeCategoryProducts(Pageable pageable);

}
