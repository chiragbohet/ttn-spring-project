package com.springbootcamp.springsecurity.DAO;

import com.springbootcamp.springsecurity.Entities.Role;
import com.springbootcamp.springsecurity.Entities.Users.Customer;
import com.springbootcamp.springsecurity.Entities.Users.User;
import com.springbootcamp.springsecurity.Repositories.CustomerRepository;
import com.springbootcamp.springsecurity.Repositories.UserRepository;
import com.springbootcamp.springsecurity.Security.AppUser;
import com.springbootcamp.springsecurity.Security.GrantAuthorityImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDao {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CustomerRepository customerRepository;

    public AppUser loadUserByUsername(String email) {

        User user=userRepository.findByEmail(email);

        List<GrantAuthorityImpl> grantAuthorityList = new ArrayList<>();
        List<Role> roleList = user.getRoleList();

        roleList.forEach(role ->
                {
                    grantAuthorityList.add(new GrantAuthorityImpl(role.getAuthority()));
                }
        );
        if (email != null) {
            return new AppUser(user.getEmail(),user.getPassword(), grantAuthorityList);
        } else throw new RuntimeException();
    }
}