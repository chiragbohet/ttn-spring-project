package com.chiragbohet.ecommerce.controllers;

import com.chiragbohet.ecommerce.co.CustomerRegistrationCo;
import com.chiragbohet.ecommerce.co.EmailCo;
import com.chiragbohet.ecommerce.co.SellerRegistrationCo;
import com.chiragbohet.ecommerce.dtos.PasswordUpdateDto;
import com.chiragbohet.ecommerce.services.CustomerService;
import com.chiragbohet.ecommerce.services.SellerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api("Various Registration related tasks")
@RestController
public class RegistrationController {

    @Autowired
    CustomerService customerService;

    @Autowired
    SellerService sellerService;


    @ApiOperation("Register a new Customer")
    @PostMapping("/register/customer")
    ResponseEntity registerCustomer(@Valid @RequestBody CustomerRegistrationCo customerRegistrationDto) {
        return customerService.registerNewCustomer(customerRegistrationDto);
    }

    @ApiOperation("Register a new Seller")
    @PostMapping("/register/seller")
    ResponseEntity registerSeller(@Valid @RequestBody SellerRegistrationCo sellerRegistrationDto) {
        return sellerService.registerNewSeller(sellerRegistrationDto);
    }

    @ApiOperation("Confirm registration of a Customer")
    @PutMapping("register/confirm")
    ResponseEntity confirmRegistration(@RequestParam("token") String userToken){

        return customerService.validateRegistrationToken(userToken);
    }

    @ApiOperation("Resend Activation link for Customer")
    @PostMapping("/register/resend-activation-link")
    ResponseEntity resendCustomerActivationLink(@Valid @RequestBody EmailCo emailCo) {
        return customerService.resendActivationLink(emailCo);
    }

    @ApiOperation("Get a forgot password link")
    @PostMapping("/register/forgot-password")
    ResponseEntity forgotPassword(@Valid @RequestBody EmailCo emailCo) {
        return customerService.forgotPassword(emailCo);
    }

    @ApiOperation("Validate a forgot password request")
    @PutMapping("/register/forgot-password")
    ResponseEntity validateForgotPasswordRequestAndResetPassword(@RequestParam("token") String token, @Valid @RequestBody PasswordUpdateDto passwordUpdateDto) {
        return customerService.validateForgotPasswordRequestAndResetPassword(token, passwordUpdateDto);
    }


}
