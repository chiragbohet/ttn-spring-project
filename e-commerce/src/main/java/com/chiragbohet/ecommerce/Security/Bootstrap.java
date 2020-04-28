package com.chiragbohet.ecommerce.Security;

import com.chiragbohet.ecommerce.Entities.UserRelated.Address;
import com.chiragbohet.ecommerce.Entities.UserRelated.Seller;
import com.chiragbohet.ecommerce.Repositories.RoleRepository;
import com.chiragbohet.ecommerce.Repositories.SellerRepository;
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
    SellerRepository sellerRepository;

    @Autowired
    RoleRepository roleRepository;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(userRepository.count()<1){

            addRoles();
            addTestSeller();
            addTestCustomer();
            addTestAdmin();

            System.out.println("Total users saved::"+userRepository.count());

        }
    }

    void addRoles()
    {
        Role ROLE_SELLER = new Role("ROLE_SELLER");
        Role ROLE_ADMIN = new Role("ROLE_SELLER");
        Role ROLE_CUSTOMER = new Role("ROLE_SELLER");

        roleRepository.save(ROLE_SELLER);
        roleRepository.save(ROLE_ADMIN);
        roleRepository.save(ROLE_ADMIN);

    }

    void addTestSeller()
    {
        Seller seller = new Seller();

        // common fields
        seller.setFirstName("Test");
        seller.setMiddleName("Seller");
        seller.setEmail("testseller@localhost.com");
        seller.setPassword(passwordEncoder.encode("1A2a$5AA"));

        //seller specific fields
        Address address = new Address();
        address.setAddressLine("2nd Floor, NSL Techzone IT SEZ");
        address.setCity("Noida");
        address.setState("Uttar Pradesh");
        address.setCountry("India");
        address.setLabel("Primary");
        address.setZipCode("201306");

        seller.setCompanyContact("9999999999");
        seller.setCompanyName("To The New");
        seller.setAddress(address);
        address.setUser(seller);// TODO : Fix this infinite loop
        seller.setGst("18AABCT3518Q1ZV");


        seller.setActive(false);  // will be activated by Admin
        seller.setDeleted(false);
        // spring security related fields
        seller.setAccountNonExpired(true);
        seller.setAccountNonLocked(true);
        seller.setCredentialsNonExpired(true);
        seller.setEnabled(true);
        sellerRepository.save(seller);

    }


    void addTestCustomer()
    {
        Customer customer = new Customer();

        customer.setFirstName("Test");
        customer.setMiddleName("Customer");
        customer.setEmail("testcustomer@localhost.com");
        customer.setPassword(passwordEncoder.encode("1A2a$5AA"));
        customer.setContact("9999999999");

        customer.setActive(false);  // will be activated via email
        customer.setDeleted(false);

        // spring security related fields
        customer.setAccountNonExpired(true);
        customer.setAccountNonLocked(true);
        customer.setCredentialsNonExpired(true);
        customer.setEnabled(true);
        customer.setEnabled(true);

        // address
        Address address = new Address();
        address.setAddressLine("2nd Floor, NSL Techzone IT SEZ");
        address.setCity("Noida");
        address.setState("Uttar Pradesh");
        address.setCountry("India");
        address.setLabel("Primary");
        address.setZipCode("201306");
        customer.addAddress(address);

        Address address2 = new Address();
        address2.setAddressLine("2nd Floor, NSL Techzone IT SEZ");
        address2.setCity("Noida");
        address2.setState("Uttar Pradesh");
        address2.setCountry("India");
        address2.setLabel("Primary");
        address2.setZipCode("201306");

        customer.addAddress(address2);

        customerRepository.save(customer);

    }

    void addTestAdmin()
    {
        Customer admin = new Customer();

        admin.setFirstName("Test");
        admin.setMiddleName("Admin");
        admin.setEmail("testadmin@localhost.com");
        admin.setPassword(passwordEncoder.encode("1A2a$5AA"));
        admin.setContact("9999999999");

        admin.setActive(true);  // will be activated via email
        admin.setDeleted(false);

        // spring security related fields
        admin.setAccountNonExpired(true);
        admin.setAccountNonLocked(true);
        admin.setCredentialsNonExpired(true);
        admin.setEnabled(true);
        admin.setEnabled(true);

        // adding ADMIN role

        Role ROLE_ADMIN = new Role("ROLE_ADMIN");
        admin.addRoles(ROLE_ADMIN);

        customerRepository.save(admin);
    }
}
