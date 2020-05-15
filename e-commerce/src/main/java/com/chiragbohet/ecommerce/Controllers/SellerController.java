package com.chiragbohet.ecommerce.Controllers;

import com.chiragbohet.ecommerce.Dtos.NewAddressDto;
import com.chiragbohet.ecommerce.Dtos.PasswordUpdateDto;
import com.chiragbohet.ecommerce.Dtos.SellerApi.SellerProfileUpdateDto;
import com.chiragbohet.ecommerce.Services.SellerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Api("Various Seller Related Tasks")
@RestController
public class SellerController {

    @Autowired
    SellerService sellerService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header")})
    @ApiOperation("View Seller Profile")
    @GetMapping("/seller")
    ResponseEntity getSellerByEmail(Principal principal) {
        return sellerService.getSellerByEmail(principal.getName());
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header")})
    @ApiOperation("Update details of a Seller")
    @PatchMapping("/seller")
    ResponseEntity updateSellerDetails(Principal principal, @Valid @RequestBody SellerProfileUpdateDto sellerProfileUpdateDto) {
        return sellerService.updateSellerDetails(principal.getName(), sellerProfileUpdateDto);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header")})
    @ApiOperation("Update password of a Seller")
    @PatchMapping("/seller/update-password")
    ResponseEntity updateCustomerPassword(Principal principal, @Valid @RequestBody PasswordUpdateDto passwordUpdateDto) {
        return sellerService.updateSellerPassword(principal.getName(), passwordUpdateDto);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header")})
    @ApiOperation("Update address of a Seller")
    @PatchMapping("/seller/address/{id}")
    ResponseEntity updateSellerAddress(Principal principal, @PathVariable Long id, @Valid @RequestBody NewAddressDto newAddressDto) {
        return sellerService.updateSellerAddress(principal.getName(), id, newAddressDto);
    }

}
