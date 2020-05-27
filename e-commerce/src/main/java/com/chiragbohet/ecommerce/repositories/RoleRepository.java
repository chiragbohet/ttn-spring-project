package com.chiragbohet.ecommerce.repositories;

import com.chiragbohet.ecommerce.security.Role;
import org.springframework.data.repository.CrudRepository;

//TODO : Delete this if not necessary
public interface RoleRepository extends CrudRepository<Role, Long> {

    Role findByAuthority(String role);
}
