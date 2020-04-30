package com.chiragbohet.ecommerce.Dtos.CategoryApi;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class CategoryViewDto {

    Long id;

    String name;

    CategoryViewDtoParentWithNoSubCategories parentCategory;

    Set<CategoryViewDtoSubCategoriesWithNoParent> subCategoriesSet;

}
