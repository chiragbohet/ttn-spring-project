package com.chiragbohet.ecommerce.Entities.CategoryRelated;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@IdClass(CategoryMetadataFieldValuesId.class)
public class CategoryMetadataFieldValues {

    @Id
    @Column(name = "CATEGORY_METADATA_FIELD_ID")
    Long categoryMetadataFieldId;

    @Id
    @Column(name = "CATEGORY_ID")
    Long categoryId;

    @Column(name = "VALUES")
    String values;

}
