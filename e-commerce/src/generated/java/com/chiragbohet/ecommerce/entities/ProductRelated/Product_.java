package com.chiragbohet.ecommerce.entities.ProductRelated;

import com.chiragbohet.ecommerce.entities.Category;
import com.chiragbohet.ecommerce.entities.Product;
import com.chiragbohet.ecommerce.entities.ProductVariation;
import com.chiragbohet.ecommerce.entities.Seller;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Product.class)
public abstract class Product_ extends com.chiragbohet.ecommerce.utilities.Auditable_ {

    public static final String IS_RETURNABLE = "isReturnable";
    public static final String SELLER = "seller";
    public static final String IS_DELETED = "isDeleted";
    public static final String PRODUCT_VARIATION_SET = "productVariationSet";
    public static final String IS_CANCELLABLE = "isCancellable";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String ID = "id";
    public static final String IS_ACTIVE = "isActive";
    public static final String CATEGORY = "category";
    public static final String BRAND = "brand";
    public static volatile SingularAttribute<Product, Boolean> isReturnable;
    public static volatile SingularAttribute<Product, Seller> seller;
    public static volatile SingularAttribute<Product, Boolean> isDeleted;
    public static volatile SetAttribute<Product, ProductVariation> productVariationSet;
    public static volatile SingularAttribute<Product, Boolean> isCancellable;
    public static volatile SingularAttribute<Product, String> name;
    public static volatile SingularAttribute<Product, String> description;
    public static volatile SingularAttribute<Product, Long> id;
    public static volatile SingularAttribute<Product, Boolean> isActive;
    public static volatile SingularAttribute<Product, Category> category;
    public static volatile SingularAttribute<Product, String> brand;

}

