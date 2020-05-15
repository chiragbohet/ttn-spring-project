package com.chiragbohet.ecommerce.Entities.CategoryRelated;

import com.chiragbohet.ecommerce.Utilities.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "CATEGORY_METADATA_FIELD")
public class CategoryMetadataField extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    Long id;

    @Column(name = "NAME", unique = true, nullable = false)
    String name;

    @OneToMany(mappedBy = "categoryMetadataField", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    Set<CategoryMetadataFieldValues> fieldValuesSet;

    public void addFieldValues(CategoryMetadataFieldValues... values) {
        if(values != null) {
            if(fieldValuesSet == null)
                fieldValuesSet = new HashSet<>();

            for (CategoryMetadataFieldValues value : values) {
                value.setCategoryMetadataField(this);
                fieldValuesSet.add(value);
            }
        }
    }

    public CategoryMetadataField(String name) {
        this.name = name;
    }

}
