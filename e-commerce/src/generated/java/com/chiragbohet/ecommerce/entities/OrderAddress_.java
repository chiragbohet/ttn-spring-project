package com.chiragbohet.ecommerce.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(OrderAddress.class)
public abstract class OrderAddress_ {

	public static volatile SingularAttribute<OrderAddress, String> country;
	public static volatile SingularAttribute<OrderAddress, String> zipCode;
	public static volatile SingularAttribute<OrderAddress, String> city;
	public static volatile SingularAttribute<OrderAddress, String> state;
	public static volatile SingularAttribute<OrderAddress, String> label;
	public static volatile SingularAttribute<OrderAddress, String> addressLine;

	public static final String COUNTRY = "country";
	public static final String ZIP_CODE = "zipCode";
	public static final String CITY = "city";
	public static final String STATE = "state";
	public static final String LABEL = "label";
	public static final String ADDRESS_LINE = "addressLine";

}

