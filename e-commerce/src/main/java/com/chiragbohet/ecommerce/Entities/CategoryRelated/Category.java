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

    @ManyToOne
    @JoinColumn(name = "PARENT_ID")
    Category parentCategory;

    @OneToMany
    Set<Category> subCategoriesSet;

    @OneToMany
    Set<CategoryMetadataField> categoryMetadataFieldSet;

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

    public void addCategoryMetadataField(CategoryMetadataField... fields)
    {
        if(fields != null)
        {
            if(categoryMetadataFieldSet == null)
                categoryMetadataFieldSet = new HashSet<>();

            for(CategoryMetadataField field : fields)
            {
                categoryMetadataFieldSet.add(field);
            }
        }
    }

}
