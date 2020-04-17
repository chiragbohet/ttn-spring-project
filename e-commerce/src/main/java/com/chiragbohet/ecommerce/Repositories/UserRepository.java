package com.chiragbohet.ecommerce.Repositories;

import com.chiragbohet.ecommerce.Entities.UserRelated.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);
}
