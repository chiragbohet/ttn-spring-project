package com.chiragbohet.ecommerce.Repositories;

import com.chiragbohet.ecommerce.Entities.UserRelated.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);


    @Modifying
    @Query(value = "UPDATE USER SET IS_ENABLED = TRUE WHERE EMAIL = :userEmail", nativeQuery = true)
    void enableAccountsByEmail(@Param("userEmail") String userEmail);

}
