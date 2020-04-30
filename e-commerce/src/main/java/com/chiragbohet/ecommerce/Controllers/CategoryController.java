package com.chiragbohet.ecommerce.Controllers;

import com.chiragbohet.ecommerce.Services.CategoryService;
import com.chiragbohet.ecommerce.co.CategoryCo;
import com.chiragbohet.ecommerce.co.CategoryMetadataFieldCo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping("/metadata-fields")
    public ResponseEntity addNewMetadataField(@Valid @RequestBody CategoryMetadataFieldCo co)
    {
        return categoryService.addNewMetadataField(co);
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

}
