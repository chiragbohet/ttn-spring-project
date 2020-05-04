package com.chiragbohet.ecommerce.Entities.CategoryRelated;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "CATEGORY_METADATA_FIELD")
public class CategoryMetadataField {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    Long id;

    @Column(name = "NAME", unique = true, nullable = false)
    String name;

    @OneToMany(mappedBy = "categoryMetadataField", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    Set<CategoryMetadataFieldValues> fieldValuesSet;

    public void addFieldValues(CategoryMetadataFieldValues... values)
    {
        if(values != null)
        {
            if(fieldValuesSet == null)
                fieldValuesSet = new HashSet<>();

            for (CategoryMetadataFieldValues value : values)
            {
                value.setCategoryMetadataField(this);
                fieldValuesSet.add(value);
            }
        }
    }

}
