package com.chiragbohet.ecommerce.Security;

import com.chiragbohet.ecommerce.Entities.UserRelated.User;
import com.chiragbohet.ecommerce.Repositories.LoginAttemptRepository;
import com.chiragbohet.ecommerce.Repositories.UserRepository;
import com.chiragbohet.ecommerce.Utilities.GlobalVariables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class LoginAttemptService {

    @Autowired
    LoginAttemptRepository loginAttemptRepository;

    @Autowired
    UserRepository userRepository;

    public void loginFailed(String userEmail) {
        Optional<LoginAttempt> previousFailedLoginAttempt = loginAttemptRepository.findById(userEmail);

        if (previousFailedLoginAttempt.isPresent()) {
            if (previousFailedLoginAttempt.get().attemptCount + 1 >= GlobalVariables.MAX_ATTEMPTS) {
                // Locking account
                User user = userRepository.findByEmail(userEmail);
                user.setEnabled(false);
                //user.setActive(false); TODO : Should I set this instead? Pros and Cons?
                userRepository.save(user);

                // Updating blocked timestamp in the table
                previousFailedLoginAttempt.get().incrementAttemptCount();
                previousFailedLoginAttempt.get().setAccountBlockedAtTimestamp(LocalDateTime.now()); // can be used by scheduler to re enable accounts after some specified time
                loginAttemptRepository.save(previousFailedLoginAttempt.get());

                throw new RuntimeException("Your account has been locked due to 3 failed wrong credential attempts.");

            } else {
                // if done within a specified time,
                // increment count, set new timestamp and throw a account about to lock warning
                // else simply update value

                Long timeDifferenceSeconds = ChronoUnit.SECONDS.between(previousFailedLoginAttempt.get().firstFailedLoginAttemptTimeStamp, LocalDateTime.now());

                if (timeDifferenceSeconds <= GlobalVariables.MINIMUM_TIME_BETWEEN_FAILED_ATTEMPTS_SECONDS) {
                    previousFailedLoginAttempt.get().incrementAttemptCount();
                    loginAttemptRepository.save(previousFailedLoginAttempt.get());
                } else {
                    previousFailedLoginAttempt.get().reset();
                    loginAttemptRepository.save(previousFailedLoginAttempt.get());
                }

                throw new RuntimeException("You have entered wrong credentials " + previousFailedLoginAttempt.get().getAttemptCount() + " times. After " + GlobalVariables.MAX_ATTEMPTS + " continuous failed attempts, your account will be locked!");
            }
        } else {
            loginAttemptRepository.save(new LoginAttempt(userEmail));
        }

    }

    public void loginSuccess(String userEmail) {
        Optional<LoginAttempt> previousFailedLoginAttempt = loginAttemptRepository.findById(userEmail);

        if (previousFailedLoginAttempt.isPresent()) {
            loginAttemptRepository.delete(previousFailedLoginAttempt.get());
        }

    }

}
