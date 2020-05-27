package com.chiragbohet.ecommerce.utilities;

import com.chiragbohet.ecommerce.entities.User;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.Date;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ConfirmationToken.class)
public abstract class ConfirmationToken_ {

    public static final String CREATED_DATE = "createdDate";
    public static final String CONFIRMATION_TOKEN = "confirmationToken";
    public static final String ID = "id";
    public static final String USER = "user";
    public static volatile SingularAttribute<ConfirmationToken, Date> createdDate;
    public static volatile SingularAttribute<ConfirmationToken, String> confirmationToken;
    public static volatile SingularAttribute<ConfirmationToken, Long> id;
    public static volatile SingularAttribute<ConfirmationToken, User> user;

}

