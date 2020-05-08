package com.chiragbohet.ecommerce.Repositories;

import com.chiragbohet.ecommerce.Security.Role;
import org.springframework.data.repository.CrudRepository;

//TODO : Delete this if not necessary
public interface RoleRepository extends CrudRepository<Role, Long> {

    Role findByAuthority(String role);
}
