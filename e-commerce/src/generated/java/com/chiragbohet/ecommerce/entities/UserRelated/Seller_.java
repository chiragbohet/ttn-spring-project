package com.chiragbohet.ecommerce.entities.UserRelated;

import com.chiragbohet.ecommerce.entities.Address;
import com.chiragbohet.ecommerce.entities.Product;
import com.chiragbohet.ecommerce.entities.Seller;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Seller.class)
public abstract class Seller_ extends com.chiragbohet.ecommerce.entities.UserRelated.User_ {

    public static final String ADDRESS = "address";
    public static final String COMPANY_NAME = "companyName";
    public static final String GST = "gst";
    public static final String COMPANY_CONTACT = "companyContact";
    public static final String PRODUCT_SET = "productSet";
    public static volatile SingularAttribute<Seller, Address> address;
    public static volatile SingularAttribute<Seller, String> companyName;
    public static volatile SingularAttribute<Seller, String> gst;
    public static volatile SingularAttribute<Seller, String> companyContact;
    public static volatile SetAttribute<Seller, Product> productSet;

}

