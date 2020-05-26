package com.chiragbohet.ecommerce.Entities.OrderRelated;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.math.BigDecimal;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(OrderProduct.class)
public abstract class OrderProduct_ {

    public static final String QUANTITY = "quantity";
    public static final String PRICE = "price";
    public static final String ID = "id";
    public static volatile SingularAttribute<OrderProduct, Integer> quantity;
    public static volatile SingularAttribute<OrderProduct, BigDecimal> price;
    public static volatile SingularAttribute<OrderProduct, Long> id;

}

