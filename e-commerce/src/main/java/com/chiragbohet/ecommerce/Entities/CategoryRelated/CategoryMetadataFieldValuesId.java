package com.chiragbohet.ecommerce.Entities.CategoryRelated;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class CategoryMetadataFieldValuesId implements Serializable {

    @Column(name = "CATEGORY_METADATA_FIELD_ID")
    private Long categoryMetadataFieldId;

    @Column(name = "CATEGORY_ID")
    private Long categoryId;

}
