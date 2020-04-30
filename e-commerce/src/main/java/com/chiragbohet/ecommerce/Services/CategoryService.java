package com.chiragbohet.ecommerce.Services;

import com.chiragbohet.ecommerce.Dtos.CategoryApi.CategoryViewDto;
import com.chiragbohet.ecommerce.Entities.CategoryRelated.Category;
import com.chiragbohet.ecommerce.Entities.CategoryRelated.CategoryMetadataField;
import com.chiragbohet.ecommerce.Exceptions.ResourceAlreadyExistsException;
import com.chiragbohet.ecommerce.Exceptions.ResourceNotFoundException;
import com.chiragbohet.ecommerce.Repositories.CategoryMetadataFieldRepository;
import com.chiragbohet.ecommerce.Repositories.CategoryRepository;
import com.chiragbohet.ecommerce.co.CategoryCo;
import com.chiragbohet.ecommerce.co.CategoryMetadataFieldCo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    CategoryMetadataFieldRepository categoryMetadataFieldRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Secured("ROLE_ADMIN")
    public ResponseEntity addNewMetadataField(CategoryMetadataFieldCo co)
    {
        if(categoryMetadataFieldRepository.findByName(co.getName()) != null)
            throw new ResourceAlreadyExistsException("A metadata field already exists with name : " + co.getName());

        CategoryMetadataField categoryMetadataField = modelMapper.map(co, CategoryMetadataField.class);
        categoryMetadataFieldRepository.save(categoryMetadataField);

        return new ResponseEntity<Long>(categoryMetadataField.getId(), null, HttpStatus.CREATED);
    }

    @Secured("ROLE_ADMIN")
    public ResponseEntity addNewCategory(CategoryCo co) {

        if(categoryRepository.findByName(co.getName()) != null)
            throw new ResourceAlreadyExistsException("A category already exists with name : " + co.getName());

        Category newCategory = new Category();
        newCategory.setName(co.getName());

        Optional<Category> parentCategory = null;

        if(co.getParentId() != null)
        {
             parentCategory = categoryRepository.findById(co.getParentId());

            if(!parentCategory.isPresent())
                throw new ResourceNotFoundException("No category found with id : " + co.getParentId());

            newCategory.setParentCategory(parentCategory.get());
            parentCategory.get().addSubCategory(newCategory);

        }

        categoryRepository.save(newCategory);

        return new ResponseEntity<Long>(newCategory.getId(),null,HttpStatus.CREATED);
    }

    @Secured("ROLE_ADMIN")
    public ResponseEntity getCategory(Long id) {

        Optional<Category> category = categoryRepository.findById(id);

        if(!category.isPresent())
            throw new ResourceNotFoundException("No category with id : " + id + " found.");

        CategoryViewDto dto = modelMapper.map(category.get(), CategoryViewDto.class);

        return new ResponseEntity<CategoryViewDto>(dto,null, HttpStatus.OK);

    }
}
