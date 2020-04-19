package com.chiragbohet.ecommerce.Controllers;

import com.chiragbohet.ecommerce.Dtos.CustomerApi.CustomerProfileUpdateDto;
import com.chiragbohet.ecommerce.Dtos.NewAddressDto;
import com.chiragbohet.ecommerce.Dtos.PasswordUpdateDto;
import com.chiragbohet.ecommerce.Repositories.CustomerRepository;
import com.chiragbohet.ecommerce.Services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.security.Principal;

@RestController
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @GetMapping("/customer")
    ResponseEntity getCustomerDetails(Principal principal){
        return customerService.getCustomerByEmail(principal.getName());
    }

    @GetMapping("/customer/address")
    ResponseEntity getCustomerAddress(Principal principal){
        return customerService.getCustomerAddress(principal.getName());
    }

    @PatchMapping("/customer")
    ResponseEntity updateCustomerDetails(Principal principal, @Valid @RequestBody CustomerProfileUpdateDto customerProfileUpdateDto){
        return customerService.updateCustomerDetails(principal.getName(), customerProfileUpdateDto);
    }

    @PatchMapping("/customer/update-password")
    ResponseEntity updateCustomerPassword(Principal principal, @Valid @RequestBody PasswordUpdateDto passwordUpdateDto){
        return customerService.updateCustomerPassword(principal.getName(), passwordUpdateDto);
    }

    @PostMapping("/customer")
    ResponseEntity addNewCustomerAddress(Principal principal, @RequestBody NewAddressDto newAddressDto){
        return customerService.addNewCustomerAddress(principal.getName(), newAddressDto);
    }

    @DeleteMapping("/customer/address/{id}")
    ResponseEntity deleteCustomerAddress(Principal principal, @PathVariable Long id)
    {
        return customerService.deleteCustomerAddress(principal.getName(), id);
    }

    @PatchMapping("/customer/address/{id}")
    ResponseEntity updateCustomerAddress(Principal principal, @PathVariable Long id, @Valid @RequestBody @NotNull NewAddressDto newAddressDto)
    {
        return customerService.updateCustomerAddress(principal.getName(), id, newAddressDto);
    }

}
