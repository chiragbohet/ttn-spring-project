package com.chiragbohet.ecommerce.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@NoArgsConstructor
public class LoginAttempt {

    @Id
    String userEmail;

    Integer attemptCount;

    LocalDateTime firstFailedLoginAttemptTimeStamp;

    LocalDateTime accountBlockedAtTimestamp;

    public LoginAttempt(String userEmail) {
        this.userEmail = userEmail;
        this.attemptCount = 1;
        this.accountBlockedAtTimestamp = null;
        this.firstFailedLoginAttemptTimeStamp = LocalDateTime.now();
    }

    public void incrementAttemptCount() {
        attemptCount++;
    }

    public void reset() {
        this.attemptCount = 1;
        this.accountBlockedAtTimestamp = null;
        this.firstFailedLoginAttemptTimeStamp = LocalDateTime.now();
    }

}
