package com.chiragbohet.ecommerce.Controllers;

import com.chiragbohet.ecommerce.Services.CategoryService;
import com.chiragbohet.ecommerce.co.CategoryMetadataFieldCo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

}
