package com.chiragbohet.ecommerce.entities;

import com.chiragbohet.ecommerce.utilities.Auditable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@NoArgsConstructor
@Table(name = "CATEGORY")
public class Category extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    Long id;

    @Column(name = "NAME")
    String name;

    @Column(name = "IS_DELETED")
    Boolean isDeleted;

    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
    Set<Product> productSet;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PARENT_ID")
    Category parentCategory;

    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    Set<Category> subCategoriesSet;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    Set<CategoryMetadataFieldValues> fieldValuesSet;

    public void addFieldValues(CategoryMetadataFieldValues... values) {
        if(values != null) {
            if(fieldValuesSet == null)
                fieldValuesSet = new HashSet<>();

            for (CategoryMetadataFieldValues value : values) {
                value.setCategory(this);
                fieldValuesSet.add(value);
            }
        }
    }

    public void addSubCategory(Category... categories) {
        if(categories != null) {
            if(subCategoriesSet == null)
                subCategoriesSet = new HashSet<>();

            for (Category category : categories) {
                category.setParentCategory(this);
                subCategoriesSet.add(category);
            }

        }
    }

    public void addProduct(Product... products) {
        if (products != null) {
            if (productSet == null)
                productSet = new HashSet<>();

            for (Product product : products) {
                if (!productSet.contains(product)) {
                    productSet.add(product);
                    product.setCategory(this);
                }
            }

        }
    }

    public boolean isLeafCategory() {
        return subCategoriesSet == null;
    }

    public boolean isRootCategory() {
        return parentCategory == null;
    }


}
