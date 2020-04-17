package com.chiragbohet.ecommerce.Repositories;

import com.chiragbohet.ecommerce.Entities.UserRelated.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByEmail(String email);
}
