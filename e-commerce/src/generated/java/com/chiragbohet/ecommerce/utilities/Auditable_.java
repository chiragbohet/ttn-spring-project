package com.chiragbohet.ecommerce.utilities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.time.LocalDateTime;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Auditable.class)
public abstract class Auditable_ {

    public static final String CREATED_BY = "createdBy";
    public static final String CREATED_TIMESTAMP = "createdTimestamp";
    public static final String LAST_MODIFIED_BY = "lastModifiedBy";
    public static final String LAST_MODIFIED_TIMESTAMP = "lastModifiedTimestamp";
    public static volatile SingularAttribute<Auditable, String> createdBy;
    public static volatile SingularAttribute<Auditable, LocalDateTime> createdTimestamp;
    public static volatile SingularAttribute<Auditable, String> lastModifiedBy;
    public static volatile SingularAttribute<Auditable, LocalDateTime> lastModifiedTimestamp;

}

