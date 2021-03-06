package com.chiragbohet.ecommerce.controllers;

import com.chiragbohet.ecommerce.co.CategoryCo;
import com.chiragbohet.ecommerce.co.CategoryMetadataFieldCo;
import com.chiragbohet.ecommerce.co.CategoryMetadataFieldValuesCo;
import com.chiragbohet.ecommerce.co.CategoryUpdateCo;
import com.chiragbohet.ecommerce.services.CategoryService;
import com.chiragbohet.ecommerce.utilities.GlobalVariables;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Api("Various Category related tasks")
@Log4j2
@RestController
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header")})
    @ApiOperation("Add new Metadatafield")
    @Secured("ROLE_ADMIN")
    @PostMapping("/metadata-fields")
    public ResponseEntity addNewMetadataField(@Valid @RequestBody CategoryMetadataFieldCo co) {
        return categoryService.addNewMetadataField(co);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header")})
    @ApiOperation("Get a List of all Metadatafields")
    @Secured("ROLE_ADMIN")
    @GetMapping("/metadata-fields")
    public ResponseEntity viewAllMetadataFields(@RequestParam(value = "page", defaultValue = GlobalVariables.DEFAULT_PAGE_OFFSET) Optional<Integer> page,
                                                @RequestParam(value = "size", defaultValue = GlobalVariables.DEFAULT_PAGE_SIZE) Optional<Integer> size,
                                                @RequestParam(value = "sort", defaultValue = GlobalVariables.DEFAULT_SORT_PROPERTY) Optional<String> sortProperty,
                                                @RequestParam(value = "direction", defaultValue = GlobalVariables.DEFAULT_SORT_DIRECTION) Optional<String> sortDirection) {

        return categoryService.getAllMetadataFields(page, size, sortProperty, sortDirection);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header")})
    @ApiOperation("Add a new Category")
    @PostMapping("/categories")
    public ResponseEntity addNewCategory(@Valid @RequestBody CategoryCo co) {
        return categoryService.addNewCategory(co);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header")})
    @ApiOperation("Update a Category")
    @Secured("ROLE_ADMIN")
    @PutMapping("/categories")
    public ResponseEntity updateCategory(@Valid @RequestBody CategoryUpdateCo co) {
        return categoryService.updateCategory(co);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header")})
    @ApiOperation("View a Category")
    @GetMapping("/categories/{id}")
    public ResponseEntity viewCategory(@PathVariable("id") Long id) {
        return categoryService.getCategory(id);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header")})
    @ApiOperation("Get a List of all registered Categories")
    @Secured("ROLE_ADMIN")
    @GetMapping("/categories")
    public ResponseEntity viewAllCategories(@RequestParam(value = "page", defaultValue = GlobalVariables.DEFAULT_PAGE_OFFSET) Optional<Integer> page,
                                            @RequestParam(value = "size", defaultValue = GlobalVariables.DEFAULT_PAGE_SIZE) Optional<Integer> size,
                                            @RequestParam(value = "sort", defaultValue = GlobalVariables.DEFAULT_SORT_PROPERTY) Optional<String> sortProperty,
                                            @RequestParam(value = "direction", defaultValue = GlobalVariables.DEFAULT_SORT_DIRECTION) Optional<String> sortDirection) {
        return categoryService.getAllCategories(page, size, sortProperty, sortDirection);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header")})
    @ApiOperation("Add a new Metadatafield Value")
    @Secured("ROLE_ADMIN")
    @PostMapping("/metadata-field-values")
    public ResponseEntity addNewMetadataFieldValues(@Valid @RequestBody CategoryMetadataFieldValuesCo co) {
        return categoryService.addNewMetadataFieldValues(co);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header")})
    @ApiOperation("Update a Metadatafield Value")
    @Secured("ROLE_ADMIN")
    @PutMapping("/metadata-field-values")
    public ResponseEntity updateMetadataFieldValues(@Valid @RequestBody CategoryMetadataFieldValuesCo co) {
        return categoryService.updateMetadataFieldValues(co);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header")})
    @ApiOperation("Get a list of all available leaf node categories and their metadatafields with possible values and parent node chain details")
    @Secured("ROLE_SELLER")
    @GetMapping("/seller/categories")
    public ResponseEntity getAllCategoriesForSeller() {
        return categoryService.getAllCategoriesForSeller();
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header")})
    @ApiOperation("Get a list of all Categories available to the Customer")
    @Secured("ROLE_CUSTOMER")
    @GetMapping({"/customer/categories", "/customer/categories/{id}"})
    public ResponseEntity getAllCategoriesForCustomer(@PathVariable(name = "id", required = false) Optional<Long> categoryId) {
        log.info("inside : controller -> getAllCategoriesForCustomer");
        return categoryService.getAllCategoriesForCustomer(categoryId);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header")})
    @ApiOperation("Get filtering details for a Category")
    @Secured("ROLE_CUSTOMER")
    @GetMapping("/customer/category/filter/{categoryId}")
    public ResponseEntity getCategoryFilteringDetailsForCustomer(@PathVariable("categoryId") Long categoryId) {
        return categoryService.getCategoryFilteringDetailsForCustomer(categoryId);
    }

}
