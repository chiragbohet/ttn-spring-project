package com.chiragbohet.ecommerce.Repositories;

import com.chiragbohet.ecommerce.Security.LoginAttempt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginAttemptRepository extends JpaRepository<LoginAttempt, String> {
}
