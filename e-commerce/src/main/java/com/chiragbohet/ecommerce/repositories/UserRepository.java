package com.chiragbohet.ecommerce.repositories;

import com.chiragbohet.ecommerce.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);


    @Modifying
    @Query(value = "UPDATE USER SET IS_ENABLED = TRUE WHERE EMAIL = :userEmail", nativeQuery = true)
    void enableAccountsByEmail(@Param("userEmail") String userEmail);

    @Query(value = "SELECT COUNT(*) FROM USER WHERE NOT IS_DELETED AND ID IN (SELECT ID FROM CUSTOMER)", nativeQuery = true)
    Long getNonDeletedCustomersCount();

    @Query(value = "SELECT COUNT(*) FROM USER WHERE NOT IS_DELETED AND ID IN (SELECT ID FROM SELLER)", nativeQuery = true)
    Long getNonDeletedSellersCount();

}
