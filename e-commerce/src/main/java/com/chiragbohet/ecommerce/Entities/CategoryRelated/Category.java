package com.chiragbohet.ecommerce.Entities.CategoryRelated;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "CATEGORY")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    Long id;

    @Column(name = "NAME")
    String name;

    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    Set<Category> subCategoriesSet;


    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    Set<CategoryMetadataFieldValues> fieldValuesSet;

    @ManyToOne
    @JoinColumn(name = "PARENT_ID")
    Category parentCategory;

    public void addFieldValues(CategoryMetadataFieldValues... values)
    {
        if(values != null)
        {
            if(fieldValuesSet == null)
                fieldValuesSet = new HashSet<>();

            for (CategoryMetadataFieldValues value : values)
            {
                value.setCategory(this);
                fieldValuesSet.add(value);
            }
        }
    }

    public void addSubCategory(Category... categories)
    {
        if(categories != null)
        {
            if(subCategoriesSet == null)
                subCategoriesSet = new HashSet<>();

            for (Category category : categories)
            {
                category.setParentCategory(this);
                subCategoriesSet.add(category);
            }

        }
    }

    public boolean isLeafCategory(){
        return subCategoriesSet == null;
    }

    public boolean isRootCategory(){
        return parentCategory == null;
    }


}
