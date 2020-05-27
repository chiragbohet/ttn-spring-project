package com.chiragbohet.ecommerce.entities.UserRelated;

import com.chiragbohet.ecommerce.entities.User;
import com.chiragbohet.ecommerce.security.Role;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public abstract class User_ extends com.chiragbohet.ecommerce.utilities.Auditable_ {

    public static final String LAST_NAME = "lastName";
    public static final String IS_ACCOUNT_NON_LOCKED = "isAccountNonLocked";
    public static final String IS_ACTIVE = "isActive";
    public static final String ROLE_LIST = "roleList";
    public static final String FIRST_NAME = "firstName";
    public static final String PASSWORD = "password";
    public static final String IS_DELETED = "isDeleted";
    public static final String IS_ACCOUNT_NON_EXPIRED = "isAccountNonExpired";
    public static final String IS_CREDENTIALS_NON_EXPIRED = "isCredentialsNonExpired";
    public static final String IS_ENABLED = "isEnabled";
    public static final String MIDDLE_NAME = "middleName";
    public static final String ID = "id";
    public static final String EMAIL = "email";
    public static volatile SingularAttribute<User, String> lastName;
    public static volatile SingularAttribute<User, Boolean> isAccountNonLocked;
    public static volatile SingularAttribute<User, Boolean> isActive;
    public static volatile ListAttribute<User, Role> roleList;
    public static volatile SingularAttribute<User, String> firstName;
    public static volatile SingularAttribute<User, String> password;
    public static volatile SingularAttribute<User, Boolean> isDeleted;
    public static volatile SingularAttribute<User, Boolean> isAccountNonExpired;
    public static volatile SingularAttribute<User, Boolean> isCredentialsNonExpired;
    public static volatile SingularAttribute<User, Boolean> isEnabled;
    public static volatile SingularAttribute<User, String> middleName;
    public static volatile SingularAttribute<User, Long> id;
    public static volatile SingularAttribute<User, String> email;

}

