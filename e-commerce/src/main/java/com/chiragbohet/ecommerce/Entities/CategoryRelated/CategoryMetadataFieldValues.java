package com.chiragbohet.ecommerce.Entities.CategoryRelated;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "CATEGORY_METADATA_FIELD_VALUES")
public class CategoryMetadataFieldValues {

    @EmbeddedId
    CategoryMetadataFieldValuesId id = new CategoryMetadataFieldValuesId();

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("categoryMetadataFieldId")
    private CategoryMetadataField categoryMetadataField;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("categoryId")
    private Category category;

    @Column(name = "METADATA_VALUES")
    String values;


}
