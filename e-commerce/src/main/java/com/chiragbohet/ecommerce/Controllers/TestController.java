package com.chiragbohet.ecommerce.Controllers;

import com.chiragbohet.ecommerce.Entities.UserRelated.Customer;
import com.chiragbohet.ecommerce.Entities.UserRelated.Seller;
import com.chiragbohet.ecommerce.Repositories.CustomerRepository;
import com.chiragbohet.ecommerce.Repositories.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// TODO : Remove this
@RestController
public class TestController {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    SellerRepository sellerRepository;


    @GetMapping("/test/customer")
    public List<Customer> testCustomer(){

        return (List<Customer>) customerRepository.findAll();
    }

    @GetMapping("/test/seller")
    public List<Seller> testSeller(){

        return sellerRepository.findAll();
    }

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/admin/")
    public String adminHome(){
        return "Admin home";
    }

    @GetMapping("/customer/")
    public String userHome(){
        return "customer home";
    }

    @GetMapping("/seller/")
    public String sellerHome(){
        return "seller home";
    }


}
