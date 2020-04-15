package com.chiragbohet.ecommerce.Controllers;

import com.chiragbohet.ecommerce.Entities.UserRelated.Customer;
import com.chiragbohet.ecommerce.Repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class TestController {

    @Autowired
    CustomerRepository customerRepository;

    @GetMapping("/test/customer")
    public List<Customer> testCustomer(){

        return (List<Customer>) customerRepository.findAll();
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
