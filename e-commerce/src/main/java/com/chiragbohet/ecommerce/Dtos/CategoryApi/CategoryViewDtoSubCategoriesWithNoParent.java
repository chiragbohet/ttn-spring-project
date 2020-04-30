package com.chiragbohet.ecommerce.Dtos.CategoryApi;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class CategoryViewDtoSubCategoriesWithNoParent {

    Long id;

    String name;

    Set<CategoryViewDtoSubCategoriesWithNoParent> subCategoriesSet;

}
