package com.chiragbohet.ecommerce.controllers;

import com.chiragbohet.ecommerce.services.CustomerService;
import com.chiragbohet.ecommerce.services.SellerService;
import com.chiragbohet.ecommerce.utilities.GlobalVariables;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Api("Various Admin related tasks")
@RestController
public class AdminController {

    @Autowired
    CustomerService customerService;

    @Autowired
    SellerService sellerService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header")})
    @ApiOperation("Get a list of all registered Customers")
    @GetMapping("/admin/customer")
    ResponseEntity getAllCustomers(@RequestParam(value = "page", defaultValue = GlobalVariables.DEFAULT_PAGE_OFFSET) Optional<Integer> page,
                                   @RequestParam(value = "size", defaultValue = GlobalVariables.DEFAULT_PAGE_SIZE) Optional<Integer> size,
                                   @RequestParam(value = "sort", defaultValue = GlobalVariables.DEFAULT_SORT_PROPERTY) Optional<String> sortProperty,
                                   @RequestParam(value = "direction", defaultValue = GlobalVariables.DEFAULT_SORT_DIRECTION) Optional<String> sortDirection) {

        return customerService.getAllCustomers(page, size, sortProperty, sortDirection);

    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header")})
    @ApiOperation("Get a list of all registered Sellers")
    @GetMapping("/admin/seller")
    ResponseEntity getAllSellers(@RequestParam(value = "page", defaultValue = GlobalVariables.DEFAULT_PAGE_OFFSET) Optional<Integer> page,
                                 @RequestParam(value = "size", defaultValue = GlobalVariables.DEFAULT_PAGE_SIZE) Optional<Integer> size,
                                 @RequestParam(value = "sort", defaultValue = GlobalVariables.DEFAULT_SORT_PROPERTY) Optional<String> sortProperty,
                                 @RequestParam(value = "direction", defaultValue = GlobalVariables.DEFAULT_SORT_DIRECTION) Optional<String> sortDirection) {

        return sellerService.getAllSellers(page, size, sortProperty, sortDirection);

    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header")})
    @ApiOperation("Activate a customer")
    @PatchMapping("admin/customer/activate/{id}")
    ResponseEntity activateCustomer(@PathVariable Long id) {
        return customerService.activateCustomer(id);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header")})
    @ApiOperation("Deactivate a customer")
    @PatchMapping("admin/customer/deactivate/{id}")
    ResponseEntity deactivateCustomer(@PathVariable Long id) {
        return customerService.deactivateCustomer(id);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header")})
    @ApiOperation("Activate a seller")
    @PatchMapping("admin/seller/activate/{id}")
    ResponseEntity activateSeller(@PathVariable Long id) {
        return sellerService.activateSeller(id);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header")})
    @ApiOperation("Deactivate a seller")
    @PatchMapping("admin/seller/deactivate/{id}")
    ResponseEntity deactivateSeller(@PathVariable Long id) {
        return sellerService.deactivateSeller(id);
    }

}
