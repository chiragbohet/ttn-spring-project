package com.chiragbohet.ecommerce.Repositories;

import com.chiragbohet.ecommerce.Entities.CategoryRelated.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query(value = "SELECT * FROM CATEGORY WHERE PARENT_ID NOT IN (SELECT PARENT_ID FROM CATEGORY WHERE PARENT_ID IS NOT NULL)", nativeQuery = true)
    List<Category> getAllLeafCategories();

    @Query("FROM Category WHERE parentCategory IS NULL")
    List<Category> getAllRootCategories();

    @Query(value = "SELECT * FROM CATEGORY WHERE PARENT_ID = :parentId", nativeQuery = true)
    List<Category> getImmediateChildCategories(@Param("parentId")Long id);

    Category findByName(String name);

}
