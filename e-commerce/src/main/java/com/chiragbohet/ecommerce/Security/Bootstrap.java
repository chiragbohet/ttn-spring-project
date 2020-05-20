package com.chiragbohet.ecommerce.Security;

import com.chiragbohet.ecommerce.Entities.CategoryRelated.Category;
import com.chiragbohet.ecommerce.Entities.CategoryRelated.CategoryMetadataField;
import com.chiragbohet.ecommerce.Entities.CategoryRelated.CategoryMetadataFieldValues;
import com.chiragbohet.ecommerce.Entities.ProductRelated.Product;
import com.chiragbohet.ecommerce.Entities.ProductRelated.ProductVariation;
import com.chiragbohet.ecommerce.Entities.UserRelated.Address;
import com.chiragbohet.ecommerce.Entities.UserRelated.Customer;
import com.chiragbohet.ecommerce.Entities.UserRelated.Seller;
import com.chiragbohet.ecommerce.Repositories.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

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
            addAdmin();
            addTestCustomer();
            addTestSeller();
            addBasicCategories();
            addMetadataFields();
            addMetadataFieldValues();
            addDummyProduct();
            addDummyProductVariation();

        }
    }

    void addRoles()
    {
        roleRepository.save(new Role("ROLE_SELLER"));
        roleRepository.save(new Role("ROLE_ADMIN"));
        roleRepository.save(new Role("ROLE_CUSTOMER"));

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

    void addTestCustomer()
    {
        Customer testCustomer = new Customer();

        testCustomer.setFirstName("Test");
        testCustomer.setMiddleName("Customer");
        testCustomer.setEmail("chirag.mca17.du@gmail.com");
        testCustomer.setPassword(passwordEncoder.encode("1A2a$5AA"));
        testCustomer.setContact("9654476321");

        testCustomer.setActive(true);  // is deactivated by constructor so activating it

        // setting role
        testCustomer.addRoles(roleRepository.findByAuthority("ROLE_CUSTOMER"));

        // address
        Address address = new Address();

        address.setAddressLine("Plot No - 19, Gali No - 2, Opposite Radha Swami Satsang, Narela");
        address.setCity("New Delhi");
        address.setState("Delhi");
        address.setCountry("India");
        address.setLabel("Primary");
        address.setZipCode("110040");

        Address address2 = new Address();
        address2.setAddressLine("2nd Floor, NSL Techzone IT SEZ");
        address2.setCity("Noida");
        address2.setState("Uttar Pradesh");
        address2.setCountry("India");
        address2.setLabel("Primary");
        address2.setZipCode("201306");

        testCustomer.addAddress(address,address2);

        customerRepository.save(testCustomer);

    }

    void addTestSeller()
    {
        Seller seller = new Seller();

        // common fields
        seller.setFirstName("Test");
        seller.setMiddleName("Seller");
        seller.setEmail("chirag.bohet@tothenew.com");
        seller.setPassword(passwordEncoder.encode("1A2a$5AA"));


        Address address = new Address();
        address.setAddressLine("2nd Floor, NSL Techzone IT SEZ");
        address.setCity("Noida");
        address.setState("Uttar Pradesh");
        address.setCountry("India");
        address.setLabel("Primary");
        address.setZipCode("201306");

        //seller specific fields
        seller.setCompanyContact("9654476321");
        seller.setCompanyName("To The New");
        seller.addAddress(address);
        seller.setGst("18AABCT3518Q1ZV");

        seller.addRoles(roleRepository.findByAuthority("ROLE_SELLER"));
        seller.setActive(true); // false by default

        sellerRepository.save(seller);

    }

    void addBasicCategories()
    {
        /*
        * Current Structure
        *
        * Electronics -> Mobiles
        *             -> Laptops
        *             -> Cameras
        *
        * TVs and Appliances -> Television
        *                    -> Kitchen Appliances
        *
        * Men -> Footwear
        *     -> Clothing
        *
        * Women -> Footwear
        *       -> Clothing
        *
        * Home & Furniture -> Kitchen, Cookware & Serveware
        *                  -> Furnishing
        *
        * Sports, Books & More -> Sports
        *                      -> Books
        *                      -> Stationery
        *
        * */

        // Electronics Chain
        Category electronics = new Category();
        electronics.setName("Electronics");
        electronics.setParentCategory(null);

        Category mobiles = new Category();
        mobiles.setName("Mobiles");
        mobiles.setParentCategory(electronics);


        Category laptops = new Category();
        laptops.setName("Laptops");
        laptops.setParentCategory(electronics);

        Category cameras = new Category();
        cameras.setName("Cameras");
        cameras.setParentCategory(electronics);

        electronics.addSubCategory(mobiles, laptops, cameras);
        categoryRepository.save(electronics);

        // TVs and Appliances chain
        Category tvsAndAppliances = new Category();
        tvsAndAppliances.setName("TVs and Appliances");
        tvsAndAppliances.setParentCategory(null);

        Category televisions = new Category();
        televisions.setName("Televisions");
        televisions.setParentCategory(tvsAndAppliances);

        Category kitchenAppliances = new Category();
        kitchenAppliances.setName("Kitchen Appliances");
        kitchenAppliances.setParentCategory(tvsAndAppliances);

        tvsAndAppliances.addSubCategory(televisions, kitchenAppliances);
        categoryRepository.save(tvsAndAppliances);

        // Home & Appliances chain
        Category homeAndAppliances = new Category();
        homeAndAppliances.setName("Home & Appliances");
        homeAndAppliances.setParentCategory(null);

        Category kitchenCookwareAndServeware = new Category();
        kitchenCookwareAndServeware.setName("Kitchen, Cookware & Serveware");
        kitchenCookwareAndServeware.setParentCategory(homeAndAppliances);

        Category furnishing = new Category();
        furnishing.setName("Furnishing");
        furnishing.setParentCategory(homeAndAppliances);

        homeAndAppliances.addSubCategory(kitchenCookwareAndServeware, furnishing);
        categoryRepository.save(homeAndAppliances);

        // Sports,Books & More chain
        Category sportsBooksAndMore = new Category();
        sportsBooksAndMore.setName("Sports,Books & More");
        sportsBooksAndMore.setParentCategory(null);

        Category sports = new Category();
        sports.setName("Sports");
        sports.setParentCategory(sportsBooksAndMore);

        Category books = new Category();
        books.setName("Books");
        books.setParentCategory(sportsBooksAndMore);

        Category stationery = new Category();
        stationery.setName("Stationery");
        stationery.setParentCategory(sportsBooksAndMore);

        sportsBooksAndMore.addSubCategory(sports, books, stationery);
        categoryRepository.save(sportsBooksAndMore);

    }



    void addMetadataFields()
    {
        // mobile and laptop related
        categoryMetadataFieldRepository.saveAll(
                new ArrayList<>(Arrays.asList(
                new CategoryMetadataField("RAM"),
                new CategoryMetadataField("Screen Size"),
                new CategoryMetadataField("Touch Screen"),
                new CategoryMetadataField("OS"),
                new CategoryMetadataField("Primary Camera"),
                new CategoryMetadataField("Internal Storage"),
                new CategoryMetadataField("Graphics Memory"),
                new CategoryMetadataField("Hard Disk Capacity"),
                new CategoryMetadataField("Processor Brand")
        )));

    }

    void addMetadataFieldValues()
    {
        // mobile related field values
        Category mobilesCategory = categoryRepository.findByName("Mobiles");
        categoryMetadataFieldValuesRepository.save(new CategoryMetadataFieldValues(mobilesCategory,categoryMetadataFieldRepository.findByName("RAM"), "2gb,4b,6gb,8gb"));
        categoryMetadataFieldValuesRepository.save(new CategoryMetadataFieldValues(mobilesCategory, categoryMetadataFieldRepository.findByName("Screen Size"),"4 Inch,5 Inch,6 Inch"));
        categoryMetadataFieldValuesRepository.save(new CategoryMetadataFieldValues(mobilesCategory, categoryMetadataFieldRepository.findByName("OS"), "Android,IOS,Other"));
        categoryMetadataFieldValuesRepository.save(new CategoryMetadataFieldValues(mobilesCategory, categoryMetadataFieldRepository.findByName("Primary Camera"),"10mp,12mp,16mp,18mp,20mp,48mp"));
        categoryMetadataFieldValuesRepository.save(new CategoryMetadataFieldValues(mobilesCategory, categoryMetadataFieldRepository.findByName("Internal Storage"), "16gb,32gb,64gb,128gb,256gb,512gb"));


        // laptop related field values
        Category laptopsCategory = categoryRepository.findByName("Laptops");
        categoryMetadataFieldValuesRepository.save(new CategoryMetadataFieldValues(laptopsCategory, categoryMetadataFieldRepository.findByName("RAM"), "4gb,8gb,12gb,16gb,32gb,64gb"));
        categoryMetadataFieldValuesRepository.save(new CategoryMetadataFieldValues(laptopsCategory, categoryMetadataFieldRepository.findByName("OS"), "Windows,Linux,MAC OS"));
        categoryMetadataFieldValuesRepository.save(new CategoryMetadataFieldValues(laptopsCategory, categoryMetadataFieldRepository.findByName("Screen Size"), "13 Inch,14 Inch,15 Inch,17 Inch"));
        categoryMetadataFieldValuesRepository.save(new CategoryMetadataFieldValues(laptopsCategory, categoryMetadataFieldRepository.findByName("Hard Disk Capacity"), "250gb,500gb,1tb,2tb,4tb"));
        categoryMetadataFieldValuesRepository.save(new CategoryMetadataFieldValues(laptopsCategory, categoryMetadataFieldRepository.findByName("Touch Screen"), "Yes,No"));
        categoryMetadataFieldValuesRepository.save(new CategoryMetadataFieldValues(laptopsCategory, categoryMetadataFieldRepository.findByName("Processor Brand"), "Intel,AMD,Other"));
        categoryMetadataFieldValuesRepository.save(new CategoryMetadataFieldValues(laptopsCategory, categoryMetadataFieldRepository.findByName("Graphics Memory"), "2gb,4gb,6gb,8gb,12gb,16gb"));

    }

    void addDummyProduct() {
        Seller testSeller = sellerRepository.findByEmail("chirag.bohet@tothenew.com");

        // One Plus 7
        Product onePlus7 = new Product();
        onePlus7.setName("One Plus 7");
        onePlus7.setBrand("One Plus");
        onePlus7.setDescription("Android Smartphone");
        onePlus7.setIsActive(true);
        onePlus7.setIsReturnable(true);
        onePlus7.setIsCancellable(true);
        onePlus7.setIsDeleted(false);
        onePlus7.setCategory(categoryRepository.findByName("Mobiles"));
        //productRepository.save(onePlus7);


        // Xiaomi Mi A2
        Product miA2 = new Product();
        miA2.setName("Mi A2");
        miA2.setBrand("Xiaomi");
        miA2.setDescription("Android Smartphone");
        miA2.setIsActive(true);
        miA2.setIsReturnable(true);
        miA2.setIsCancellable(true);
        miA2.setIsDeleted(false);
        miA2.setCategory(categoryRepository.findByName("Mobiles"));


        // thinkpad laptop
        Product thinkpad = new Product();
        thinkpad.setName("Thinkpad");
        thinkpad.setBrand("Lenovo");
        thinkpad.setDescription("Business Laptop");
        thinkpad.setIsActive(true);
        thinkpad.setIsCancellable(true);
        thinkpad.setIsCancellable(true);
        thinkpad.setIsDeleted(false);
        thinkpad.setCategory(categoryRepository.findByName("Laptops"));
        //thinkpad.setSeller(sellerRepository.findByEmail("chirag.bohet@tothenew.com"));
        //productRepository.save(thinkpad);
        //sellerRepository.save(sellerRepository.findByEmail("chirag.bohet@tothenew.com"));


        testSeller.addProduct(onePlus7, thinkpad, miA2);
        sellerRepository.save(testSeller);

    }

    void addDummyProductVariation(){

        ProductVariation thinkpadE490 = new ProductVariation();

        Product thinkpad = productRepository.findByName("Thinkpad").get();
        thinkpad.addProductVariation(thinkpadE490);

        thinkpadE490.setIsActive(true);
        thinkpadE490.setPrimaryImageName("thinkpade490.jpg");
        thinkpadE490.setPrice(new BigDecimal("70000"));
        thinkpadE490.setQuantityAvailable(10L);

        Map<String,String> thinkpadE490Metadata = new TreeMap<>();
        thinkpadE490Metadata.put("RAM","16gb");
        thinkpadE490Metadata.put("OS","Linux");
        thinkpadE490Metadata.put("Screen Size","14 Inch");
        thinkpadE490Metadata.put("Hard Disk Capacity","1tb");
        thinkpadE490Metadata.put("Touch Screen","No");
        thinkpadE490Metadata.put("Processor Brand","Intel");

        thinkpadE490.setMetadata(thinkpadE490Metadata); // product must be set before this, else null pointer exception

        // TODO : idk if this is a bug? how can this be solved?
        // saving the product instead of the variation so that variation set is properly set -> changes are cascaded
        productRepository.save(thinkpad);


        ProductVariation onePlus7_6gb = new ProductVariation();

        Product onePlus7 = productRepository.findByName("One Plus 7").get();
        onePlus7.addProductVariation(onePlus7_6gb);

        onePlus7_6gb.setQuantityAvailable(5L);
        onePlus7_6gb.setPrice(new BigDecimal("30000"));
        onePlus7_6gb.setPrimaryImageName("oneplus7_6gb.jpg");
        onePlus7_6gb.setIsActive(true);

        Map<String, String> onePlus7_6gbMetadata = new TreeMap<>();
        onePlus7_6gbMetadata.put("RAM","6gb");
        onePlus7_6gbMetadata.put("Screen Size","6 Inch");
        onePlus7_6gbMetadata.put("OS","Android");
        onePlus7_6gbMetadata.put("Primary Camera","48mp");

        onePlus7_6gb.setMetadata(onePlus7_6gbMetadata); // product must be set before this, else null pointer exception

        // TODO : idk if this is a bug? how can this be solved?
        // saving the product instead of the variation so that variation set is properly set, changes are cascaded
        productRepository.save(onePlus7);

    }









}
