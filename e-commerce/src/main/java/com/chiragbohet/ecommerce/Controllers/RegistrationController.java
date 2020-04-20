package com.chiragbohet.ecommerce.Controllers;

import com.chiragbohet.ecommerce.Dtos.PasswordUpdateDto;
import com.chiragbohet.ecommerce.Dtos.RegistrationApi.EmailDto;
import com.chiragbohet.ecommerce.Dtos.RegistrationApi.SellerRegistrationDto;
import com.chiragbohet.ecommerce.Services.CustomerService;
import com.chiragbohet.ecommerce.Dtos.RegistrationApi.CustomerRegistrationDto;
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

    @PostMapping("/register/seller")
    ResponseEntity registerSeller(@Valid @RequestBody SellerRegistrationDto sellerRegistrationDto){
        return sellerService.registerNewSeller(sellerRegistrationDto);
    }

    @PutMapping("register/confirm")
    ResponseEntity confirmRegistration(@RequestParam("token") String userToken){

        return customerService.validateRegistrationToken(userToken);
    }

    @PostMapping("/register/resend-activation-link")
    ResponseEntity resendCustomerActivationLink(@Valid @RequestBody EmailDto emailDto)
    {
        return customerService.resendActivationLink(emailDto);
    }

    @PostMapping("/register/forgot-password")
    ResponseEntity forgotPassword(@Valid @RequestBody EmailDto emailDto)
    {
        return customerService.forgotPassword(emailDto);
    }

    @PutMapping("/register/forgot-password")
    ResponseEntity validateForgotPasswordRequestAndResetPassword(@RequestParam("token") String token, @Valid @RequestBody PasswordUpdateDto passwordUpdateDto)
    {
        return customerService.validateForgotPasswordRequestAndResetPassword(token, passwordUpdateDto);
    }


}
