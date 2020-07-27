package com.chiragbohet.ecommerce.entities;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(OrderProduct.class)
public abstract class OrderProduct_ {

	public static volatile SingularAttribute<OrderProduct, Integer> quantity;
	public static volatile SingularAttribute<OrderProduct, BigDecimal> price;
	public static volatile SingularAttribute<OrderProduct, Long> id;

	public static final String QUANTITY = "quantity";
	public static final String PRICE = "price";
	public static final String ID = "id";

}

