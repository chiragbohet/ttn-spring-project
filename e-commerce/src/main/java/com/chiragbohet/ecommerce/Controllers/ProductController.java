package com.chiragbohet.ecommerce.Controllers;

import com.chiragbohet.ecommerce.Services.ProductService;
import com.chiragbohet.ecommerce.Utilities.GlobalVariables;
import com.chiragbohet.ecommerce.co.ProductCo;
import com.chiragbohet.ecommerce.co.ProductUpdateCo;
import com.chiragbohet.ecommerce.co.ProductVariationCo;
import com.chiragbohet.ecommerce.co.ProductVariationUpdateCo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@Api("Various Product related tasks")
@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @ApiOperation("Add new Product")
    @Secured("ROLE_SELLER")
    @PostMapping("/seller/products")
    public ResponseEntity addNewProduct(@Valid @RequestBody ProductCo co, Principal principal) {
        return productService.addNewProduct(co, principal.getName());
    }

    @ApiOperation("Add new Product Variations")
    @Secured("ROLE_SELLER")
    @PostMapping("/seller/product-variations")
    public ResponseEntity addNewProductVariation(@Valid @RequestBody ProductVariationCo co) {
        return productService.addNewProductVariation(co);
    }

    @ApiOperation("Get Product Details")
    @Secured("ROLE_SELLER")
    @GetMapping("/seller/products/{id}")
    public ResponseEntity getSellerProduct(Principal principal, @PathVariable(name = "id") Long productId) {
        return productService.getSellerProduct(principal.getName(), productId);
    }

    @ApiOperation("Get Product Variation Details")
    @Secured("ROLE_SELLER")
    @GetMapping("/seller/product-variations/{id}")
    public ResponseEntity getSellerProductVariation(Principal principal, @PathVariable(name = "id") Long productVariationId) {
        return productService.getSellerProductVariation(principal.getName(), productVariationId);
    }

    @ApiOperation("Get a list of all Products by Seller")
    @Secured("ROLE_SELLER")
    @GetMapping("/seller/products")
    public ResponseEntity getAllProductsBySeller(@RequestParam(value = "page", defaultValue = GlobalVariables.DEFAULT_PAGE_OFFSET) Optional<Integer> page,
                                                 @RequestParam(value = "size", defaultValue = GlobalVariables.DEFAULT_PAGE_SIZE) Optional<Integer> size,
                                                 @RequestParam(value = "sort", defaultValue = GlobalVariables.DEFAULT_SORT_PROPERTY) Optional<String> sortProperty,
                                                 @RequestParam(value = "direction", defaultValue = GlobalVariables.DEFAULT_SORT_DIRECTION) Optional<String> sortDirection,
                                                 Principal principal) {
        return productService.getAllSellerProducts(page, size, sortProperty, sortDirection, principal.getName());
    }

    @ApiOperation("Get a list of all Variations of a Product by Seller")
    @Secured("ROLE_SELLER")
    @GetMapping("/seller/products/{productid}/product-variations")
    public ResponseEntity getAllVariationsOfAProductBySeller(@RequestParam(value = "page", defaultValue = GlobalVariables.DEFAULT_PAGE_OFFSET) Optional<Integer> page,
                                                             @RequestParam(value = "size", defaultValue = GlobalVariables.DEFAULT_PAGE_SIZE) Optional<Integer> size,
                                                             @RequestParam(value = "sort", defaultValue = GlobalVariables.DEFAULT_SORT_PROPERTY) Optional<String> sortProperty,
                                                             @RequestParam(value = "direction", defaultValue = GlobalVariables.DEFAULT_SORT_DIRECTION) Optional<String> sortDirection,
                                                             Principal principal,
                                                             @PathVariable(name = "productid") Long productId) {
        return productService.getAllVariationsOfAProductBySeller(page, size, sortProperty, sortDirection, principal.getName(), productId);
    }

    @ApiOperation("Delete a Product")
    @Secured("ROLE_SELLER")
    @DeleteMapping("/seller/products/{productId}")
    public ResponseEntity deleteProductForSeller(Principal principal, @PathVariable(name = "productId") Long productId) {
        return productService.deleteProductForSeller(principal.getName(), productId);
    }

    @ApiOperation("Update a Product")
    @Secured("ROLE_SELLER")
    @PutMapping("/seller/products/{productId}")
    public ResponseEntity updateProductForSeller(Principal principal, @PathVariable(name = "productId") Long productId, @RequestBody @Valid ProductUpdateCo co) {
        return productService.updateProductForSeller(principal.getName(), productId, co);

    }

    @ApiOperation("Update a Product Variation")
    @Secured("ROLE_SELLER")
    @PutMapping("/seller/product-variations/{productVariationId}")
    public ResponseEntity updateProductVariationForSeller(Principal principal, @PathVariable(name = "productVariationId") Long productVariationId, @RequestBody @Valid ProductVariationUpdateCo co) {
        return productService.updateProductVariationForSeller(principal.getName(), productVariationId, co);
    }

    @ApiOperation("Get Product details for Admin")
    @Secured("ROLE_ADMIN")
    @GetMapping("/admin/products/{productId}")
    public ResponseEntity getProductForAdmin(@PathVariable("productId") Long productId) {
        return productService.getProductForAdmin(productId);
    }

    @ApiOperation("Get a list of Products for Admin")
    @Secured("ROLE_ADMIN")
    @GetMapping("/admin/products")
    public ResponseEntity getAllProductsForAdmin(@RequestParam(value = "page", defaultValue = GlobalVariables.DEFAULT_PAGE_OFFSET) Optional<Integer> page,
                                                 @RequestParam(value = "size", defaultValue = GlobalVariables.DEFAULT_PAGE_SIZE) Optional<Integer> size,
                                                 @RequestParam(value = "sort", defaultValue = GlobalVariables.DEFAULT_SORT_PROPERTY) Optional<String> sortProperty,
                                                 @RequestParam(value = "direction", defaultValue = GlobalVariables.DEFAULT_SORT_DIRECTION) Optional<String> sortDirection) {
        return productService.getAllProductsForAdmin(page, size, sortDirection, sortProperty);
    }


    @ApiOperation("Deactivate a Product")
    @Secured("ROLE_ADMIN")
    @PutMapping("/admin/deactivate/products/{productId}")
    public ResponseEntity deactivateProductForAdmin(@PathVariable("productId") Long productId) {
        return productService.deactivateProductForAdmin(productId);
    }

    @ApiOperation("Activate a Product")
    @Secured("ROLE_ADMIN")
    @PutMapping("/admin/activate/products/{productId}")
    public ResponseEntity activateProductForAdmin(@PathVariable("productId") Long productId) {
        return productService.activateProductForAdmin(productId);
    }

    @ApiOperation("Get details of a Product for Customer")
    @Secured("ROLE_CUSTOMER")
    @GetMapping("/customer/products/{productId}")
    public ResponseEntity getProductForCustomer(@PathVariable("productId") Long productId) {
        return productService.getProductForCustomer(productId);
    }


    @ApiOperation("Get all Products in a Category for a Customer")
    @Secured("ROLE_CUSTOMER")
    @GetMapping("/customer/products/categories/{categoryId}")
    public ResponseEntity getProductForCustomer(@RequestParam(value = "page", defaultValue = GlobalVariables.DEFAULT_PAGE_OFFSET) Optional<Integer> page,
                                                @RequestParam(value = "size", defaultValue = GlobalVariables.DEFAULT_PAGE_SIZE) Optional<Integer> size,
                                                @RequestParam(value = "sort", defaultValue = GlobalVariables.DEFAULT_SORT_PROPERTY) Optional<String> sortProperty,
                                                @RequestParam(value = "direction", defaultValue = GlobalVariables.DEFAULT_SORT_DIRECTION) Optional<String> sortDirection,
                                                @PathVariable(value = "categoryId") Long categoryId) {
        return productService.getAllProductsForCustomer(categoryId, page, size, sortDirection, sortProperty);
    }

    @ApiOperation("Get similar Products for a Customer")
    @Secured("ROLE_CUSTOMER")
    @GetMapping("/customer/products/similar/{productId}")
    public ResponseEntity getSimilarProductsForCustomer(@RequestParam(value = "page", defaultValue = GlobalVariables.DEFAULT_PAGE_OFFSET) Optional<Integer> page,
                                                        @RequestParam(value = "size", defaultValue = GlobalVariables.DEFAULT_PAGE_SIZE) Optional<Integer> size,
                                                        @RequestParam(value = "sort", defaultValue = GlobalVariables.DEFAULT_SORT_PROPERTY) Optional<String> sortProperty,
                                                        @RequestParam(value = "direction", defaultValue = GlobalVariables.DEFAULT_SORT_DIRECTION) Optional<String> sortDirection,
                                                        @PathVariable(value = "productId") Long productId) {
        return productService.getSimilarProductsForCustomer(productId, page, size, sortDirection, sortProperty);
    }

}