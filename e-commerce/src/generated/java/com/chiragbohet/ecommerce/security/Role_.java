package com.chiragbohet.ecommerce.security;

import com.chiragbohet.ecommerce.entities.User;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Role.class)
public abstract class Role_ {

    public static final String USER_LIST = "userList";
    public static final String AUTHORITY = "authority";
    public static final String ID = "id";
    public static volatile ListAttribute<Role, User> userList;
    public static volatile SingularAttribute<Role, String> authority;
    public static volatile SingularAttribute<Role, Long> id;

}

