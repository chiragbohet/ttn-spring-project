package com.chiragbohet.ecommerce.Services;

import com.chiragbohet.ecommerce.Entities.CategoryRelated.CategoryMetadataField;
import com.chiragbohet.ecommerce.Exceptions.ResourceAlreadyExistsException;
import com.chiragbohet.ecommerce.Repositories.CategoryMetadataFieldRepository;
import com.chiragbohet.ecommerce.co.CategoryMetadataFieldCo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    CategoryMetadataFieldRepository categoryMetadataFieldRepository;

    @Secured("ROLE_ADMIN")
    public ResponseEntity addNewMetadataField(CategoryMetadataFieldCo co)
    {
        if(categoryMetadataFieldRepository.findByName(co.getName()) != null)
            throw new ResourceAlreadyExistsException("A metadata field already exists with name : " + co.getName());

        CategoryMetadataField categoryMetadataField = modelMapper.map(co, CategoryMetadataField.class);
        categoryMetadataFieldRepository.save(categoryMetadataField);

        return new ResponseEntity<CategoryMetadataField>(categoryMetadataField, null, HttpStatus.CREATED);
    }

}
