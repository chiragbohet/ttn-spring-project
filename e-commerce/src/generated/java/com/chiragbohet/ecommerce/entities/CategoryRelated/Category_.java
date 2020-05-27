package com.chiragbohet.ecommerce.entities.CategoryRelated;

import com.chiragbohet.ecommerce.entities.Category;
import com.chiragbohet.ecommerce.entities.CategoryMetadataFieldValues;
import com.chiragbohet.ecommerce.entities.Product;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Category.class)
public abstract class Category_ extends com.chiragbohet.ecommerce.utilities.Auditable_ {

    public static volatile SingularAttribute<Category, Boolean> isDeleted;
    public static volatile SingularAttribute<Category, String> name;
    public static volatile SingularAttribute<Category, Category> parentCategory;
    public static volatile SingularAttribute<Category, Long> id;
    public static volatile SetAttribute<Category, Category> subCategoriesSet;
    public static volatile SetAttribute<Category, Product> productSet;
    public static volatile SetAttribute<Category, CategoryMetadataFieldValues> fieldValuesSet;

    public static final String IS_DELETED = "isDeleted";
    public static final String NAME = "name";
    public static final String PARENT_CATEGORY = "parentCategory";
    public static final String ID = "id";
    public static final String SUB_CATEGORIES_SET = "subCategoriesSet";
    public static final String PRODUCT_SET = "productSet";
    public static final String FIELD_VALUES_SET = "fieldValuesSet";

}

