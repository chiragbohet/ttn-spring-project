package com.chiragbohet.ecommerce.co;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CategoryMetadataFieldValuesCo {

    @NotNull(message = "Category ID cannot be null!")
    Long categoryId;

    @NotNull(message = "MetadataField ID cannot be null!")
    Long metadataFieldId;

    @NotNull(message = "values cannot be null, please give comma separated values!")
    String values;
}
