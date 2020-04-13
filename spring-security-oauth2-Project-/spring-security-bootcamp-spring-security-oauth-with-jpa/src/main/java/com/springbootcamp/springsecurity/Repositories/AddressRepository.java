package com.springbootcamp.springsecurity.Repositories;

import com.springbootcamp.springsecurity.Entities.Address;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository <Address,Integer> {
}
