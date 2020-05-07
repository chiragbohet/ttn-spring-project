package com.chiragbohet.ecommerce.Dtos.productapi;

import com.chiragbohet.ecommerce.Entities.CategoryRelated.CategoryMetadataFieldValues;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class CategoryAdminViewDto {

    Long id;

    String name;

    Set<CategoryMetadataFieldValues> fieldValuesSet;
}
