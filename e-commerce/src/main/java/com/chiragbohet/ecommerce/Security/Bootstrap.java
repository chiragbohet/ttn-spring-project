package com.chiragbohet.ecommerce.Security;

import com.chiragbohet.ecommerce.Entities.CategoryRelated.Category;
import com.chiragbohet.ecommerce.Entities.CategoryRelated.CategoryMetadataField;
import com.chiragbohet.ecommerce.Entities.CategoryRelated.CategoryMetadataFieldValues;
import com.chiragbohet.ecommerce.Entities.CategoryRelated.CategoryMetadataFieldValuesId;
import com.chiragbohet.ecommerce.Entities.ProductRelated.Product;
import com.chiragbohet.ecommerce.Entities.ProductRelated.ProductVariation;
import com.chiragbohet.ecommerce.Entities.UserRelated.Address;
import com.chiragbohet.ecommerce.Entities.UserRelated.Seller;
import com.chiragbohet.ecommerce.Repositories.*;
import com.chiragbohet.ecommerce.Entities.UserRelated.Customer;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
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

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductVariationRepository productVariationRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryMetadataFieldRepository categoryMetadataFieldRepository;

    @Autowired
    CategoryMetadataFieldValuesRepository categoryMetadataFieldValuesRepository;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(userRepository.count()<1){

            addRoles();

            addTestSeller();
            addTestCustomer();
            addTestAdmin();
            addDummyCategories();

            addMetadataFields();
            addDummyProduct();
            addDummyProductVariation();

        }
    }

    private void addAdmin()
    {
        Customer admin = new Customer();
        admin.setFirstName("Admin");
        admin.setMiddleName("Chirag");
        admin.setLastName("Bohet");
        admin.setEmail("bohet.chirag@gmail.com");
        admin.setPassword(passwordEncoder.encode("1A2a$5AA"));
        admin.setContact("9654476321");

        // explicitly setting because in constructor this is set as false
        admin.setActive(true);

        // giving the admin all three roles
        admin.addRoles(roleRepository.findByAuthority("ROLE_ADMIN"),
                       roleRepository.findByAuthority("ROLE_SELLER"),
                       roleRepository.findByAuthority("ROLE_CUSTOMER"));

        customerRepository.save(admin);

    }

    void addTestAdmin()
    {
        Customer admin = new Customer();

        admin.setFirstName("Chirag");
        admin.setMiddleName("Admin");
        admin.setLastName("Bohet");
        admin.setEmail("bohet.chirag@gmail.com");
        admin.setPassword(passwordEncoder.encode("1A2a$5AA"));
        admin.setContact("9654476321");

        admin.setActive(true);  // will be activated via email
        admin.setDeleted(false);

        // spring security related fields
        admin.setAccountNonExpired(true);
        admin.setAccountNonLocked(true);
        admin.setCredentialsNonExpired(true);
        admin.setEnabled(true);

        // adding ADMIN role

        Role ROLE_ADMIN = new Role("ROLE_ADMIN");
        admin.addRoles(ROLE_ADMIN);

        customerRepository.save(admin);
    }

    void addDummyProductVariation(){
        log.info("inside addDummyProductVariation()");
        ProductVariation productVariation = new ProductVariation();
        productVariation.setIsActive(true);
        productVariation.setPrice(new BigDecimal("12000"));
        productVariation.setQuantityAvailable(1L);
        productVariation.setPrimaryImageName("Mia2.jpg");
        productVariation.setProduct(productRepository.findById(1L).get());

        Map<String,String> map = new HashMap<>();
        map.put("RAM","4gb");
        productVariation.setMetadata(map);
        log.info("inside addDummyProductVariation() -> trying to persist");
        productVariationRepository.save(productVariation);
        log.info("inside addDummyProductVariation() -> persisted");
    }

    void addDummyProduct()
    {   log.trace("Inside addDummyProduct()");
        Product product = new Product();
        product.setIsActive(true);
        product.setCategory(categoryRepository.findById(5l).get());
        product.setDescription("Android smartphone");
        product.setBrand("Xiaomi");
        product.setName("Mi A2");
        productRepository.save(product);
    }

    void addDummyCategories()
    {
        log.info("Adding dummy categories...");


        // adding root categories
        Category electronics = new Category();
        electronics.setName("Electronics");
        electronics.setParentCategory(null);
        categoryRepository.save(electronics);

        Category TVAndAppliances = new Category();
        TVAndAppliances.setName("TVs & Appliances");
        TVAndAppliances.setParentCategory(null);
        categoryRepository.save(TVAndAppliances);

        Category men = new Category();
        men.setName("Men");
        men.setParentCategory(null);
        categoryRepository.save(men);

        Category women = new Category();
        women.setName("Women");
        women.setParentCategory(null);
        categoryRepository.save(women);

        // adding child categories

        Category electronicsParent = categoryRepository.findById(electronics.getId()).get();

        Category mobiles = new Category();
        mobiles.setName("Mobiles");

        //mobiles.setParentCategory(categoryRepository.findByName("Electronics"));
        //categoryRepository.save(mobiles);

        Category laptops = new Category();
        laptops.setName("Laptops");
        //laptops.setParentCategory(categoryRepository.findByName("Electronics"));
        //categoryRepository.save(laptops);

        electronicsParent.addSubCategory(mobiles, laptops);
        categoryRepository.save(electronicsParent);

        log.info("Finished adding dummy categories...");
    }


    void addMetadataFields()
    {
        CategoryMetadataField ram = new CategoryMetadataField();
        ram.setName("RAM");
        categoryMetadataFieldRepository.save(ram);

        //Mobile RAM field
        CategoryMetadataFieldValues mobileRam = new CategoryMetadataFieldValues();
        mobileRam.setCategory(categoryRepository.findByName("Mobiles"));
        mobileRam.setCategoryMetadataField(categoryMetadataFieldRepository.findByName("RAM"));
        mobileRam.setValues("2gb,4gb,6gb,8gb");
        categoryMetadataFieldValuesRepository.save(mobileRam);

        //Laptop RAM field
        CategoryMetadataFieldValues laptopRam = new CategoryMetadataFieldValues();
        laptopRam.setCategory(categoryRepository.findByName("Laptops"));
        laptopRam.setCategoryMetadataField(categoryMetadataFieldRepository.findByName("RAM"));
        laptopRam.setValues("4gb,8gb,16gb,32gb");
        categoryMetadataFieldValuesRepository.save(laptopRam);


    }

    void addRoles()
    {
        roleRepository.save(new Role("ROLE_SELLER"));
        roleRepository.save(new Role("ROLE_ADMIN"));
        roleRepository.save(new Role("ROLE_CUSTOMER"));

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


}
