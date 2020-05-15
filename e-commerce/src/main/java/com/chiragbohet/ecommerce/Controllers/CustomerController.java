package com.chiragbohet.ecommerce.Controllers;

import com.chiragbohet.ecommerce.Dtos.CustomerApi.CustomerProfileUpdateDto;
import com.chiragbohet.ecommerce.Dtos.NewAddressDto;
import com.chiragbohet.ecommerce.Dtos.PasswordUpdateDto;
import com.chiragbohet.ecommerce.Services.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header")})
    @ApiOperation("View Customer details")
    @GetMapping("/customer")
    ResponseEntity getCustomerDetails(Principal principal) {
        return customerService.getCustomerByEmail(principal.getName());
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header")})
    @ApiOperation("View address of a Customer")
    @GetMapping("/customer/address")
    ResponseEntity getCustomerAddress(Principal principal) {
        return customerService.getCustomerAddress(principal.getName());
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header")})
    @ApiOperation("Update details of a Customer")
    @PatchMapping("/customer")
    ResponseEntity updateCustomerDetails(Principal principal, @Valid @RequestBody CustomerProfileUpdateDto customerProfileUpdateDto) {
        return customerService.updateCustomerDetails(principal.getName(), customerProfileUpdateDto);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header")})
    @ApiOperation("Update password of a Customer")
    @PatchMapping("/customer/update-password")
    ResponseEntity updateCustomerPassword(Principal principal, @Valid @RequestBody PasswordUpdateDto passwordUpdateDto) {
        return customerService.updateCustomerPassword(principal.getName(), passwordUpdateDto);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header")})
    @ApiOperation("Add new Customer Address")
    @PostMapping("/customer")
    ResponseEntity addNewCustomerAddress(Principal principal, @RequestBody NewAddressDto newAddressDto) {
        return customerService.addNewCustomerAddress(principal.getName(), newAddressDto);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header")})
    @ApiOperation("Delete a Customer Address")
    @DeleteMapping("/customer/address/{id}")
    ResponseEntity deleteCustomerAddress(Principal principal, @PathVariable Long id) {
        return customerService.deleteCustomerAddress(principal.getName(), id);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header")})
    @ApiOperation("Update a Customer Address")
    @PatchMapping("/customer/address/{id}")
    ResponseEntity updateCustomerAddress(Principal principal, @PathVariable Long id, @Valid @RequestBody @NotNull NewAddressDto newAddressDto) {
        return customerService.updateCustomerAddress(principal.getName(), id, newAddressDto);
    }

}
