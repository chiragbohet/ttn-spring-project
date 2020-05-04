package com.chiragbohet.ecommerce.Dtos.CategoryApi;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

// DTO class with no references to parent to prevent infinite reference problem

@Getter
@Setter
public class CategoryViewDtoSubCategoriesWithNoParent {

    Long id;

    String name;

    Set<CategoryViewDtoSubCategoriesWithNoParent> subCategoriesSet;

}
