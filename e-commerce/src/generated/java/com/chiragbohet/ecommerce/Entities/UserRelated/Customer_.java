package com.chiragbohet.ecommerce.Entities.UserRelated;

import com.chiragbohet.ecommerce.Entities.OrderRelated.Order;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Customer.class)
public abstract class Customer_ extends com.chiragbohet.ecommerce.Entities.UserRelated.User_ {

    public static final String ADDRESS_SET = "addressSet";
    public static final String ORDER_SET = "orderSet";
    public static final String CONTACT = "contact";
    public static volatile SetAttribute<Customer, Address> addressSet;
    public static volatile SetAttribute<Customer, Order> orderSet;
    public static volatile SingularAttribute<Customer, String> contact;

}

