package com.chiragbohet.ecommerce.entities;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Order.class)
public abstract class Order_ {

	public static volatile SingularAttribute<Order, OrderAddress> orderAddress;
	public static volatile SingularAttribute<Order, Date> dateCreated;
	public static volatile SingularAttribute<Order, BigDecimal> amountPaid;
	public static volatile SingularAttribute<Order, String> paymentMethod;
	public static volatile SingularAttribute<Order, Long> id;
	public static volatile SingularAttribute<Order, Customer> customer;

	public static final String ORDER_ADDRESS = "orderAddress";
	public static final String DATE_CREATED = "dateCreated";
	public static final String AMOUNT_PAID = "amountPaid";
	public static final String PAYMENT_METHOD = "paymentMethod";
	public static final String ID = "id";
	public static final String CUSTOMER = "customer";

}

