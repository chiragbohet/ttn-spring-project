package com.chiragbohet.ecommerce.repositories;

import com.chiragbohet.ecommerce.security.LoginAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LoginAttemptRepository extends JpaRepository<LoginAttempt, String> {

    @Modifying
    @Query(value = "DELETE FROM LoginAttempt WHERE accountBlockedAtTimestamp IS NOT NULL AND TIMESTAMPDIFF(HOUR,firstFailedLoginAttemptTimeStamp, CURRENT_TIMESTAMP()) >= :timeDifferenceHour", nativeQuery = true)
    void deleteEntriesLockedForHours(@Param("timeDifferenceHour") Long hours);

    @Modifying
    @Query(value = "DELETE FROM LoginAttempt WHERE accountBlockedAtTimestamp IS NOT NULL AND TIMESTAMPDIFF(MINUTE ,firstFailedLoginAttemptTimeStamp, CURRENT_TIMESTAMP()) >= :timeDifferenceMinutes", nativeQuery = true)
    void deleteEntriesLockedForMinutes(@Param("timeDifferenceMinutes") Long minutes);

    @Modifying
    @Query(value = "UPDATE USER " +
            "SET IS_ENABLED = TRUE " +
            "WHERE EMAIL IN " +
            "(SELECT userEmail " +
            "FROM LoginAttempt " +
            "WHERE accountBlockedAtTimestamp IS NOT NULL AND TIMESTAMPDIFF(MINUTE ,firstFailedLoginAttemptTimeStamp, CURRENT_TIMESTAMP()) >= :timeDifferenceMinutes )", nativeQuery = true)
    void enableAccountsLockedForMinutes(@Param("timeDifferenceMinutes") Long minutes);


    @Query(value = "SELECT userEmail " +
            "FROM LoginAttempt " +
            "WHERE accountBlockedAtTimestamp IS NOT NULL AND " +
            "TIMESTAMPDIFF(SECOND ,firstFailedLoginAttemptTimeStamp, CURRENT_TIMESTAMP()) >= :timeDifferenceSeconds", nativeQuery = true)
    List<Object[]> getEmailOfAccountsLockedForSeconds(@Param("timeDifferenceSeconds") Long seconds);

    @Modifying
    @Query(value = "DELETE FROM LoginAttempt WHERE userEmail = :userEmail", nativeQuery = true)
    void deleteEntryByUserEmail(@Param("userEmail") String email);

}
