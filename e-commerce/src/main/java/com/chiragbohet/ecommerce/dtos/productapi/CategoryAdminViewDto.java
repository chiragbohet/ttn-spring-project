package com.chiragbohet.ecommerce.dtos.productapi;

import com.chiragbohet.ecommerce.entities.CategoryMetadataFieldValues;
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
