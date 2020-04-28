package com.chiragbohet.ecommerce.Entities.CategoryRelated;

import lombok.Data;

import java.io.Serializable;

// ref : https://www.baeldung.com/jpa-composite-primary-keys
@Data
public class CategoryMetadataFieldValuesId implements Serializable {

    Long categoryMetadataFieldId;
    Long categoryId;

}
