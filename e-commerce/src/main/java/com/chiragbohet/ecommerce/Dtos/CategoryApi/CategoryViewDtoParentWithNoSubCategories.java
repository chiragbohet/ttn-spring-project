package com.chiragbohet.ecommerce.Dtos.CategoryApi;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryViewDtoParentWithNoSubCategories {

    Long id;

    String name;

    CategoryViewDtoParentWithNoSubCategories parentCategory;

}
