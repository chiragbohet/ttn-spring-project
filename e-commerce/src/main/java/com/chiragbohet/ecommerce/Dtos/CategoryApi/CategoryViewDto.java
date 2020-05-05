package com.chiragbohet.ecommerce.Dtos.CategoryApi;

import com.chiragbohet.ecommerce.Entities.CategoryRelated.CategoryMetadataFieldValues;
import lombok.Getter;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonManagedReference;

import java.util.List;
import java.util.Set;

// ref : https://stackoverflow.com/questions/49668298/spring-data-jpa-bidirectional-relation-with-infinite-recursion

@Getter
@Setter
public class CategoryViewDto {

    Long id;

    String name;

    Set<CategoryMetadataFieldValues> fieldValuesSet;

    CategoryViewDtoParentWithNoSubCategories parentCategory;

    Set<CategoryViewDtoSubCategoriesWithNoParent> subCategoriesSet;

}
