package com.chiragbohet.ecommerce.repositories;

import com.chiragbohet.ecommerce.entities.User;
import com.chiragbohet.ecommerce.utilities.ConfirmationToken;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ConfirmationTokenRepository extends CrudRepository<ConfirmationToken, Long> {

    ConfirmationToken findByConfirmationToken(String confirmationToken);

    ConfirmationToken findByUser(User user);

    @Modifying
    @Query(value = "DELETE FROM ConfirmationToken WHERE USER_ID = :userId", nativeQuery = true)
    void deleteByUserId(@Param("userId") Long userId);

}
