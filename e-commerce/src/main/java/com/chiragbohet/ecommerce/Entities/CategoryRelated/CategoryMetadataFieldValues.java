package com.chiragbohet.ecommerce.Entities.CategoryRelated;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
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

    public CategoryMetadataFieldValues(Category category, CategoryMetadataField field, String values)
    {
        this.category = category;
        this.categoryMetadataField = field;
        this.values = values;
    }

}
