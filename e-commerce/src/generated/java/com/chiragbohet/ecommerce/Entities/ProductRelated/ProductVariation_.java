package com.chiragbohet.ecommerce.Entities.ProductRelated;

import javax.annotation.Generated;
import javax.persistence.metamodel.MapAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.math.BigDecimal;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ProductVariation.class)
public abstract class ProductVariation_ extends com.chiragbohet.ecommerce.Utilities.Auditable_ {

    public static final String QUANTITY_AVAILABLE = "quantityAvailable";
    public static final String PRODUCT = "product";
    public static final String METADATA = "metadata";
    public static final String PRICE = "price";
    public static final String PRIMARY_IMAGE_NAME = "primaryImageName";
    public static final String ID = "id";
    public static final String IS_ACTIVE = "isActive";
    public static volatile SingularAttribute<ProductVariation, Long> quantityAvailable;
    public static volatile SingularAttribute<ProductVariation, Product> product;
    public static volatile MapAttribute<ProductVariation, String, String> metadata;
    public static volatile SingularAttribute<ProductVariation, BigDecimal> price;
    public static volatile SingularAttribute<ProductVariation, String> primaryImageName;
    public static volatile SingularAttribute<ProductVariation, Long> id;
    public static volatile SingularAttribute<ProductVariation, Boolean> isActive;

}

