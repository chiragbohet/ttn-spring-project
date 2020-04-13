package com.springbootcamp.springsecurity.Repositories;

import com.springbootcamp.springsecurity.Entities.Users.Seller;
import org.springframework.data.repository.CrudRepository;

public interface SellerRepository extends CrudRepository<Seller,Integer> {
}
