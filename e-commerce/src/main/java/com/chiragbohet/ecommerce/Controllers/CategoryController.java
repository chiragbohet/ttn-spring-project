package com.chiragbohet.ecommerce.Controllers;

import com.chiragbohet.ecommerce.Services.CategoryService;
import com.chiragbohet.ecommerce.Utilities.GlobalVariables;
import com.chiragbohet.ecommerce.co.CategoryCo;
import com.chiragbohet.ecommerce.co.CategoryMetadataFieldCo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping("/metadata-fields")
    public ResponseEntity addNewMetadataField(@Valid @RequestBody CategoryMetadataFieldCo co)
    {
        return categoryService.addNewMetadataField(co);
    }

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

    @GetMapping("/categories/{id}")
    public ResponseEntity viewCategory(@PathVariable("id") Long id)
    {
        return categoryService.getCategory(id);
    }

    @GetMapping("/categories")
    public ResponseEntity viewAllCategories(@RequestParam(value = "page", defaultValue = GlobalVariables.DEFAULT_PAGE_OFFSET) Optional<Integer> page,
                                            @RequestParam(value = "size", defaultValue = GlobalVariables.DEFAULT_PAGE_SIZE) Optional<Integer> size,
                                            @RequestParam(value = "sort", defaultValue = GlobalVariables.DEFAULT_SORT_PROPERTY) Optional<String> sortProperty,
                                            @RequestParam(value = "direction", defaultValue = GlobalVariables.DEFAULT_SORT_DIRECTION) Optional<String> sortDirection)
    {
        return categoryService.getAllCategories();
    }



}
