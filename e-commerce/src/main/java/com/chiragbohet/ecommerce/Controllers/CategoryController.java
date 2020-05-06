package com.chiragbohet.ecommerce.Controllers;

import com.chiragbohet.ecommerce.co.CategoryUpdateCo;
import com.chiragbohet.ecommerce.Services.CategoryService;
import com.chiragbohet.ecommerce.Utilities.GlobalVariables;
import com.chiragbohet.ecommerce.co.CategoryCo;
import com.chiragbohet.ecommerce.co.CategoryMetadataFieldCo;
import com.chiragbohet.ecommerce.co.CategoryMetadataFieldValuesCo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Log4j2
@RestController
public class CategoryController {

    @Autowired
    CategoryService categoryService;


    @Secured("ROLE_ADMIN")
    @PostMapping("/metadata-fields")
    public ResponseEntity addNewMetadataField(@Valid @RequestBody CategoryMetadataFieldCo co)
    {
        return categoryService.addNewMetadataField(co);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/metadata-fields")
    public ResponseEntity viewAllMetadataFields(@RequestParam(value = "page", defaultValue = GlobalVariables.DEFAULT_PAGE_OFFSET) Optional<Integer> page,
                                                @RequestParam(value = "size", defaultValue = GlobalVariables.DEFAULT_PAGE_SIZE) Optional<Integer> size,
                                                @RequestParam(value = "sort", defaultValue = GlobalVariables.DEFAULT_SORT_PROPERTY) Optional<String> sortProperty,
                                                @RequestParam(value = "direction", defaultValue = GlobalVariables.DEFAULT_SORT_DIRECTION) Optional<String> sortDirection){

        return categoryService.getAllMetadataFields(page, size, sortProperty, sortDirection);
    }

    @PostMapping("/categories")
    public ResponseEntity addNewCategory(@Valid @RequestBody CategoryCo co){
        return categoryService.addNewCategory(co);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/categories")
    public ResponseEntity updateCategory(@Valid @RequestBody CategoryUpdateCo co)
    {
        return categoryService.updateCategory(co);
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity viewCategory(@PathVariable("id") Long id)
    {
        return categoryService.getCategory(id);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/categories")
    public ResponseEntity viewAllCategories(@RequestParam(value = "page", defaultValue = GlobalVariables.DEFAULT_PAGE_OFFSET) Optional<Integer> page,
                                            @RequestParam(value = "size", defaultValue = GlobalVariables.DEFAULT_PAGE_SIZE) Optional<Integer> size,
                                            @RequestParam(value = "sort", defaultValue = GlobalVariables.DEFAULT_SORT_PROPERTY) Optional<String> sortProperty,
                                            @RequestParam(value = "direction", defaultValue = GlobalVariables.DEFAULT_SORT_DIRECTION) Optional<String> sortDirection)
    {
        return categoryService.getAllCategories(page, size, sortProperty, sortDirection);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/metadata-field-values")
    public ResponseEntity addNewMetadataFieldValues(@Valid @RequestBody CategoryMetadataFieldValuesCo co)
    {
        return categoryService.addNewMetadataFieldValues(co);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/metadata-field-values")
    public ResponseEntity updateMetadataFieldValues(@Valid @RequestBody CategoryMetadataFieldValuesCo co)
    {
        return categoryService.updateMetadataFieldValues(co);
    }

    @Secured("ROLE_SELLER")
    @GetMapping("/seller/categories")
    public ResponseEntity getAllCategoriesForSeller()
    {
        return categoryService.getAllCategoriesForSeller();
    }

    @Secured("ROLE_CUSTOMER")
    @GetMapping({"/customer/categories","/customer/categories/{id}"})
    public ResponseEntity getAllCategoriesForCustomer(@PathVariable(name = "id", required = false) Optional<Long> categoryId) {
        log.info("inside : controller -> getAllCategoriesForCustomer");
        return categoryService.getAllCategoriesForCustomer(categoryId);
    }

}
