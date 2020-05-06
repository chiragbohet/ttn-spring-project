package com.chiragbohet.ecommerce.Controllers;

import com.chiragbohet.ecommerce.Entities.ProductRelated.ProductVariation;
import com.chiragbohet.ecommerce.Services.ProductService;
import com.chiragbohet.ecommerce.co.ProductCo;
import com.chiragbohet.ecommerce.co.ProductVariationCo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;

@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @Secured("ROLE_SELLER")
    @PostMapping("/seller/products")
    public ResponseEntity addNewProduct(@Valid @RequestBody ProductCo co, Principal principal)
    {
       return productService.addNewProduct(co, principal.getName());
    }

    @Secured("ROLE_SELLER")
    @PostMapping("/seller/product-variation")
    public ResponseEntity addNewProductVariation(@Valid @RequestBody ProductVariationCo co)
    {
        return productService.addNewProductVariation(co);
    }

}
