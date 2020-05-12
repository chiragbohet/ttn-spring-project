package com.chiragbohet.ecommerce.Repositories;

import com.chiragbohet.ecommerce.Security.LoginAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LoginAttemptRepository extends JpaRepository<LoginAttempt, String> {

    @Query(value = "DELETE FROM LoginAttempt WHERE accountBlockedAtTimestamp IS NOT NULL AND TIMESTAMPDIFF(HOUR,firstFailedLoginAttemptTimeStamp, CURRENT_TIMESTAMP()) >= :timeDifferenceHour", nativeQuery = true)
    void deleteEntriesLockedForHours(@Param("timeDifferenceHour") Long hours);

}
