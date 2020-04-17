package com.chiragbohet.ecommerce.Controllers;

import com.chiragbohet.ecommerce.Dtos.SellerRegistrationDto;
import com.chiragbohet.ecommerce.Services.CustomerService;
import com.chiragbohet.ecommerce.Dtos.CustomerRegistrationDto;
import com.chiragbohet.ecommerce.Services.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class RegistrationController {

    @Autowired
    CustomerService customerService;

    @Autowired
    SellerService sellerService;


    @PostMapping("/register/customer")
    ResponseEntity registerCustomer(@Valid @RequestBody CustomerRegistrationDto customerRegistrationDto){
       return customerService.registerNewCustomer(customerRegistrationDto);
    }

    @PutMapping("register/confirm")
    String confirmRegistration(@RequestParam("token") String userToken){

        return customerService.validateRegistrationToken(userToken);
    }

    @PostMapping("/register/seller")
    ResponseEntity registerSeller(@Valid @RequestBody SellerRegistrationDto sellerRegistrationDto){
        return sellerService.registerNewSeller(sellerRegistrationDto);
    }
}
