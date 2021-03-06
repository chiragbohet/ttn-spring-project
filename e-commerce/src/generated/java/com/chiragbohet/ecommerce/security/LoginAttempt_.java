package com.chiragbohet.ecommerce.security;

import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(LoginAttempt.class)
public abstract class LoginAttempt_ {

	public static volatile SingularAttribute<LoginAttempt, LocalDateTime> accountBlockedAtTimestamp;
	public static volatile SingularAttribute<LoginAttempt, String> userEmail;
	public static volatile SingularAttribute<LoginAttempt, Integer> attemptCount;
	public static volatile SingularAttribute<LoginAttempt, LocalDateTime> firstFailedLoginAttemptTimeStamp;

	public static final String ACCOUNT_BLOCKED_AT_TIMESTAMP = "accountBlockedAtTimestamp";
	public static final String USER_EMAIL = "userEmail";
	public static final String ATTEMPT_COUNT = "attemptCount";
	public static final String FIRST_FAILED_LOGIN_ATTEMPT_TIME_STAMP = "firstFailedLoginAttemptTimeStamp";

}

