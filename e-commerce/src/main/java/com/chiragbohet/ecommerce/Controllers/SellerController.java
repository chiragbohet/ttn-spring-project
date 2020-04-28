package com.chiragbohet.ecommerce.Controllers;

import com.chiragbohet.ecommerce.Dtos.NewAddressDto;
import com.chiragbohet.ecommerce.Dtos.PasswordUpdateDto;
import com.chiragbohet.ecommerce.Dtos.SellerApi.SellerDetailsDto;
import com.chiragbohet.ecommerce.Dtos.SellerApi.SellerProfileUpdateDto;
import com.chiragbohet.ecommerce.Entities.UserRelated.Seller;
import com.chiragbohet.ecommerce.Services.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
public class SellerController {

    @Autowired
    SellerService sellerService;

    @GetMapping("/seller")
    ResponseEntity getSellerByEmail(Principal principal)
    {
        return sellerService.getSellerByEmail(principal.getName());
    }

    @PatchMapping("/seller")
    ResponseEntity updateSellerDetails(Principal principal, @Valid @RequestBody SellerProfileUpdateDto sellerProfileUpdateDto){
        return sellerService.updateSellerDetails(principal.getName(), sellerProfileUpdateDto);
    }

    @PatchMapping("/seller/update-password")
    ResponseEntity updateCustomerPassword(Principal principal, @Valid @RequestBody PasswordUpdateDto passwordUpdateDto){
        return sellerService.updateSellerPassword(principal.getName(), passwordUpdateDto);
    }

    @PatchMapping("/seller/address/{id}")
    ResponseEntity updateSellerAddress(Principal principal, @PathVariable Long id, @Valid @RequestBody NewAddressDto newAddressDto)
    {
        return sellerService.updateSellerAddress(principal.getName(), id, newAddressDto );
    }

}
