package com.chiragbohet.ecommerce.Security;

import com.chiragbohet.ecommerce.Repositories.RoleRepository;
import com.chiragbohet.ecommerce.Repositories.UserRepository;
import com.chiragbohet.ecommerce.Entities.UserRelated.Customer;
import com.chiragbohet.ecommerce.Repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements ApplicationRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    RoleRepository roleRepository;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(userRepository.count()<1){

            //addTestAdmin();
            //addTestCustomer();

            System.out.println("Total users saved::"+userRepository.count());

        }
    }

//    void addTestAdmin(){
//
//        Role ROLE_CUSTOMER = new Role("ROLE_CUSTOMER");
//        Role ROLE_SELLER = new Role("ROLE_SELLER");
//        Role ROLE_ADMIN = new Role("ROLE_ADMIN");
//
//        roleRepository.save(ROLE_ADMIN);
//        roleRepository.save(ROLE_SELLER);
//
//
//        User user3 = new Seller();
//        user3.setEmail("chirag@gmail.com");
//        user3.setPassword(passwordEncoder.encode("1234567Tt#"));
//        //user3.addRoles(ROLE_ADMIN);
//        user3.addRoles( roleRepository.findById(2L).get(), roleRepository.findById(1L).get());  //TODO : Error - org.springframework.dao.InvalidDataAccessApiUsageException: detached entity passed to persist
//
//        // spring security fields, mandatory for acc to function
//        user3.setAccountNonExpired(true);
//        user3.setAccountNonLocked(true);
//        user3.setCredentialsNonExpired(true);
//        user3.setEnabled(true);
//        userRepository.save(user3);
//
//    }

    void addTestCustomer()
    {
        Customer customer = new Customer();

        customer.setFirstName("Test");
        customer.setMiddleName("Customer");
        customer.setEmail("testcustomer@localhost.com");
        customer.setPassword(passwordEncoder.encode("pass"));
        customer.setActive(true);
        customer.setDeleted(false);
        customer.setContact("99999999999");
        customer.setAccountNonExpired(true);
        customer.setAccountNonLocked(true);
        customer.setCredentialsNonExpired(true);
        customer.setEnabled(true);

        customerRepository.save(customer);

    }
}
