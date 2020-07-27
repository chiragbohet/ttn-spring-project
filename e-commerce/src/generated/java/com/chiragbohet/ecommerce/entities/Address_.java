package com.chiragbohet.ecommerce.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Address.class)
public abstract class Address_ extends com.chiragbohet.ecommerce.utilities.Auditable_ {

	public static volatile SingularAttribute<Address, String> country;
	public static volatile SingularAttribute<Address, String> zipCode;
	public static volatile SingularAttribute<Address, Boolean> isDeleted;
	public static volatile SingularAttribute<Address, String> city;
	public static volatile SingularAttribute<Address, Long> id;
	public static volatile SingularAttribute<Address, String> state;
	public static volatile SingularAttribute<Address, String> label;
	public static volatile SingularAttribute<Address, String> addressLine;
	public static volatile SingularAttribute<Address, User> user;

	public static final String COUNTRY = "country";
	public static final String ZIP_CODE = "zipCode";
	public static final String IS_DELETED = "isDeleted";
	public static final String CITY = "city";
	public static final String ID = "id";
	public static final String STATE = "state";
	public static final String LABEL = "label";
	public static final String ADDRESS_LINE = "addressLine";
	public static final String USER = "user";

}

