package com.chiragbohet.ecommerce.dtos.CategoryApi;

import lombok.Getter;
import lombok.Setter;

// DTO class with no references to subcategories to prevent infinite reference problem

@Getter
@Setter
public class CategoryViewDtoParentWithNoSubCategories {

    Long id;

    String name;

    CategoryViewDtoParentWithNoSubCategories parentCategory;

}
