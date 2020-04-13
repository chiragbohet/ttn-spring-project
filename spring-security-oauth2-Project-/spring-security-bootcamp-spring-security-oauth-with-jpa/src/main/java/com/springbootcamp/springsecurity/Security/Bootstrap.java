package com.springbootcamp.springsecurity.Security;


import com.springbootcamp.springsecurity.Entities.Role;
import com.springbootcamp.springsecurity.Entities.Users.Customer;
import com.springbootcamp.springsecurity.Entities.Users.User;
import com.springbootcamp.springsecurity.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Bootstrap {

    @Autowired
    UserRepository userRepository;


    public void initialize()  {

        if(userRepository.count()<1){
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            User user1 = new Customer();
            user1.setFirstName("Jai Shree");
            user1.setLastName("Ram");
            user1.setEmail("jaishreeram@gmail.com");
            user1.setActive(true);
            user1.setDeleted(false);
            user1.setPassword(passwordEncoder.encode("ram"));
            List<Role> roleList=new ArrayList<>();
            Role role1=new Role();
            role1.setAuthority("USER_ADMIN");
            Role role2=new Role();
            role2.setAuthority("ROLE_USER");
            user1.setRoleList(roleList);
            userRepository.save(user1);
            System.out.println("Total users saved::"+userRepository.count());

        }
    }
}
