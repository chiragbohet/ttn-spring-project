package com.chiragbohet.ecommerce.Entities.CategoryRelated;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;


@Getter
@Setter
@Entity
@Table(name = "CATEGORY_METADATA_FIELD_VALUES")
//@IdClass(CategoryMetadataFieldValuesId.class)
public class CategoryMetadataFieldValues {

    @EmbeddedId
    CategoryMetadataFieldValuesId id = new CategoryMetadataFieldValuesId();

//    @Id
//    private Long categoryMetadataFieldId;
//
//    @Id
//    private Long categoryId;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("categoryMetadataFieldId")
    private CategoryMetadataField categoryMetadataField;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("categoryId")
    private Category category;

    @Column(name = "METADATA_VALUES")
    String values;


}
