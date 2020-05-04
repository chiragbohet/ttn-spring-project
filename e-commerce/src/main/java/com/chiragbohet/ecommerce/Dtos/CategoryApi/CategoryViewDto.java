package com.chiragbohet.ecommerce.Dtos.CategoryApi;

import com.chiragbohet.ecommerce.Entities.CategoryRelated.Category;
import com.chiragbohet.ecommerce.Entities.CategoryRelated.CategoryMetadataField;
import com.chiragbohet.ecommerce.Entities.CategoryRelated.CategoryMetadataFieldValues;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.Set;

// ref : https://stackoverflow.com/questions/49668298/spring-data-jpa-bidirectional-relation-with-infinite-recursion

@Getter
@Setter
public class CategoryViewDto {

    Long id;

    String name;

    List<CategoryMetadataFieldValuesDto> metadataFieldValuesDto;

    CategoryViewDtoParentWithNoSubCategories parentCategory;

    Set<CategoryViewDtoSubCategoriesWithNoParent> subCategoriesSet;

}
