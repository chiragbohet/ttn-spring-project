package com.chiragbohet.ecommerce.Controllers;

import com.chiragbohet.ecommerce.Dtos.CustomerApi.CustomerProfileUpdateDto;
import com.chiragbohet.ecommerce.Dtos.NewAddressDto;
import com.chiragbohet.ecommerce.Dtos.PasswordUpdateDto;
import com.chiragbohet.ecommerce.Services.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.security.Principal;

@Api("Various Customer related tasks")
@RestController
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @ApiOperation("View Customer details")
    @GetMapping("/customer")
    ResponseEntity getCustomerDetails(Principal principal){
        return customerService.getCustomerByEmail(principal.getName());
    }

    @ApiOperation("View address of a Customer")
    @GetMapping("/customer/address")
    ResponseEntity getCustomerAddress(Principal principal){
        return customerService.getCustomerAddress(principal.getName());
    }

    @ApiOperation("Update details of a Customer")
    @PatchMapping("/customer")
    ResponseEntity updateCustomerDetails(Principal principal, @Valid @RequestBody CustomerProfileUpdateDto customerProfileUpdateDto){
        return customerService.updateCustomerDetails(principal.getName(), customerProfileUpdateDto);
    }

    @ApiOperation("Update password of a Customer")
    @PatchMapping("/customer/update-password")
    ResponseEntity updateCustomerPassword(Principal principal, @Valid @RequestBody PasswordUpdateDto passwordUpdateDto){
        return customerService.updateCustomerPassword(principal.getName(), passwordUpdateDto);
    }

    @ApiOperation("Add new Customer Address")
    @PostMapping("/customer")
    ResponseEntity addNewCustomerAddress(Principal principal, @RequestBody NewAddressDto newAddressDto){
        return customerService.addNewCustomerAddress(principal.getName(), newAddressDto);
    }

    @ApiOperation("Delete a Customer Address")
    @DeleteMapping("/customer/address/{id}")
    ResponseEntity deleteCustomerAddress(Principal principal, @PathVariable Long id) {
        return customerService.deleteCustomerAddress(principal.getName(), id);
    }

    @ApiOperation("Update a Customer Address")
    @PatchMapping("/customer/address/{id}")
    ResponseEntity updateCustomerAddress(Principal principal, @PathVariable Long id, @Valid @RequestBody @NotNull NewAddressDto newAddressDto) {
        return customerService.updateCustomerAddress(principal.getName(), id, newAddressDto);
    }

}
