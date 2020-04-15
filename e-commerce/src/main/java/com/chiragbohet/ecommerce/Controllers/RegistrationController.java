package com.chiragbohet.ecommerce.Controllers;

import com.chiragbohet.ecommerce.Services.CustomerService;
import com.chiragbohet.ecommerce.Dtos.CustomerRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class RegistrationController {

    @Autowired
    CustomerService customerService;



    @PostMapping("/register/customer")
    ResponseEntity registerCustomer(@Valid @RequestBody CustomerRegistrationDto customerRegistrationDto){
       return customerService.createNewCustomer(customerRegistrationDto);
    }

    @GetMapping("register/confirm")
    String confirmRegistration(@RequestParam("token") String userToken){

        return customerService.validateRegistrationToken(userToken);

    }



}
