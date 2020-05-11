package com.chiragbohet.ecommerce.Services;

import com.chiragbohet.ecommerce.Dtos.CategoryApi.CategoryViewDto;
import com.chiragbohet.ecommerce.Entities.CategoryRelated.Category;
import com.chiragbohet.ecommerce.Entities.CategoryRelated.CategoryMetadataField;
import com.chiragbohet.ecommerce.Entities.CategoryRelated.CategoryMetadataFieldValues;
import com.chiragbohet.ecommerce.Entities.CategoryRelated.CategoryMetadataFieldValuesId;
import com.chiragbohet.ecommerce.Exceptions.GenericUserValidationFailedException;
import com.chiragbohet.ecommerce.Exceptions.ResourceAlreadyExistsException;
import com.chiragbohet.ecommerce.Exceptions.ResourceNotFoundException;
import com.chiragbohet.ecommerce.Repositories.CategoryMetadataFieldRepository;
import com.chiragbohet.ecommerce.Repositories.CategoryMetadataFieldValuesRepository;
import com.chiragbohet.ecommerce.Repositories.CategoryRepository;
import com.chiragbohet.ecommerce.Utilities.ObjectMapperUtils;
import com.chiragbohet.ecommerce.co.CategoryCo;
import com.chiragbohet.ecommerce.co.CategoryMetadataFieldCo;
import com.chiragbohet.ecommerce.co.CategoryMetadataFieldValuesCo;
import com.chiragbohet.ecommerce.co.CategoryUpdateCo;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.util.*;

@Log4j2
@Service
public class CategoryService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    CategoryMetadataFieldRepository categoryMetadataFieldRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CategoryMetadataFieldValuesRepository categoryMetadataFieldValuesRepository;

    @Secured("ROLE_ADMIN")
    public ResponseEntity addNewMetadataField(CategoryMetadataFieldCo co) {
        if (categoryMetadataFieldRepository.findByName(co.getName()) != null)
            throw new ResourceAlreadyExistsException("A metadata field already exists with name : " + co.getName());

        CategoryMetadataField categoryMetadataField = modelMapper.map(co, CategoryMetadataField.class);
        categoryMetadataFieldRepository.save(categoryMetadataField);

        return new ResponseEntity<>(categoryMetadataField.getId(), null, HttpStatus.CREATED);
    }

    // checks name uniqueness within subtree of the parent category, performs a iterative dfs
    public void checkCategoryNameUniquenessWithinSubtreeUtil(String newCategoryName, Category parentCategory) {
        Set<Category> visitedSubcategories = new HashSet<>();
        Stack<Category> categoryStack = new Stack<>();
        categoryStack.push(parentCategory);

        while (!categoryStack.empty()) {
            Category currentCategory = categoryStack.pop(); // popping TOS

            if (!visitedSubcategories.contains(currentCategory)) {
                visitedSubcategories.add(currentCategory);
                if (currentCategory.getName().equals(newCategoryName))
                    throw new ResourceAlreadyExistsException("A category already exists with the name : " + newCategoryName + ", Within the sub categories of : " + parentCategory.getName());
            }

            // Pushing all non visited sub categories onto the stack
            Set<Category> subCategories = currentCategory.getSubCategoriesSet();
            for (Category category : subCategories) {
                if (!visitedSubcategories.contains(category))
                    categoryStack.push(category);
            }

        }
    }

    public void checkCategoryNameUniquenessAtTheRootLevelUtil(String newCategoryName) {
        List<Category> rootLevelCategories = categoryRepository.getAllRootCategories();

        for (Category category : rootLevelCategories) {
            if (category.getName().equals(newCategoryName))
                throw new ResourceAlreadyExistsException("A category already exists at the root level with name : " + newCategoryName);

        }

    }

    @Secured("ROLE_ADMIN")
    public ResponseEntity addNewCategory(CategoryCo co) {

        checkCategoryNameUniquenessAtTheRootLevelUtil(co.getName());

        Category newCategory = new Category();
        Optional<Category> parentCategory = null;

        if (co.getParentId() != null) {
            parentCategory = categoryRepository.findById(co.getParentId());

            if (!parentCategory.isPresent())
                throw new ResourceNotFoundException("No category found with id : " + co.getParentId());

            // checking name uniqueness within the subtree
            checkCategoryNameUniquenessWithinSubtreeUtil(co.getName(), parentCategory.get());

            newCategory.setParentCategory(parentCategory.get());
            parentCategory.get().addSubCategory(newCategory);

        }

        newCategory.setName(co.getName());  // since no need to check name uniqueness within the subtree

        categoryRepository.save(newCategory);

        return new ResponseEntity<Long>(newCategory.getId(), null, HttpStatus.CREATED);
    }

    public ResponseEntity updateCategory(CategoryUpdateCo co) {

        Optional<Category> category = categoryRepository.findById(co.getId());

        if (!category.isPresent())
            throw new ResourceNotFoundException("No category found with ID : " + co.getId());

        checkCategoryNameUniquenessAtTheRootLevelUtil(co.getName());    // checking name uniqueness at root level

        if (category.get().getParentCategory() != null)  //if parent category already exists
            checkCategoryNameUniquenessWithinSubtreeUtil(co.getName(), category.get().getParentCategory()); // check name uniqueness withing the subtree

        // setting new name
        category.get().setName(co.getName());

        //persisting
        categoryRepository.save(category.get());

        return new ResponseEntity(HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN")
    public ResponseEntity getCategory(Long id) {

        Optional<Category> category = categoryRepository.findById(id);

        if(!category.isPresent())
            throw new ResourceNotFoundException("No category with id : " + id + " found.");

        CategoryViewDto dto = modelMapper.map(category.get(), CategoryViewDto.class);

        return new ResponseEntity<CategoryViewDto>(dto,null, HttpStatus.OK);
    }

    public ResponseEntity getAllCategories(Optional<Integer> page, Optional<Integer> size, Optional<String> sortProperty, Optional<String> sortDirection) {

        Sort.Direction sortingDirection = sortDirection.get().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;

        Page<Category> categoryList = categoryRepository.findAll(PageRequest.of(page.get(), size.get(), sortingDirection, sortProperty.get()));
        List<CategoryViewDto> dtoList = ObjectMapperUtils.mapAllPage(categoryList, CategoryViewDto.class);

        return new ResponseEntity<List<CategoryViewDto>>(dtoList, null, HttpStatus.OK);

    }

    public ResponseEntity getAllMetadataFields(Optional<Integer> page, Optional<Integer> size, Optional<String> sortProperty, Optional<String> sortDirection) {

        Sort.Direction sortingDirection = sortDirection.get().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;

        Page<CategoryMetadataField> fields = categoryMetadataFieldRepository.findAll(PageRequest.of(page.get(), size.get(), sortingDirection, sortProperty.get()));

        return new ResponseEntity<Page<CategoryMetadataField>>(fields,null, HttpStatus.OK);

    }

    public ResponseEntity addNewMetadataFieldValues(CategoryMetadataFieldValuesCo co) {

        Optional<Category> category = categoryRepository.findById(co.getCategoryId());
        Optional<CategoryMetadataField> field = categoryMetadataFieldRepository.findById(co.getMetadataFieldId());

        if(!category.isPresent())
            throw new ResourceNotFoundException("No Category found with ID : " + co.getCategoryId());
        if(!field.isPresent())
            throw new ResourceNotFoundException("No CategoryMetadataField found with ID : " + co.getCategoryId());
        //check if the category is a leaf category
        if(category.get().getSubCategoriesSet() != null)
            throw new GenericUserValidationFailedException("The category is not a leaf category, please enter ID of a valid leaf category.");

        // checking if all the values are unique
        String[] fieldValues = co.getValues().split(",");
        Set<String> fieldValuesSet = new HashSet<String>(Arrays.asList(fieldValues));

        if(fieldValues.length != fieldValuesSet.size())
            throw new GenericUserValidationFailedException("Values should be Unique!");

        // checking if a value already exists for given combination of Category and CategoryMetadataField
        CategoryMetadataFieldValuesId id = new CategoryMetadataFieldValuesId();
        id.setCategoryId(category.get().getId());
        id.setCategoryMetadataFieldId(field.get().getId());

        if(categoryMetadataFieldValuesRepository.findById(id).isPresent())
            throw new ResourceAlreadyExistsException("Value already exists for given combination of Category and CategoryMetadataField, if you want to update an existing value use a PUT instead.");

        // if all checks passed, persisting the values
        CategoryMetadataFieldValues values = new CategoryMetadataFieldValues();

        values.setCategory(category.get());
        values.setCategoryMetadataField(field.get());
        values.setValues(co.getValues());

        categoryMetadataFieldValuesRepository.save(values);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    public ResponseEntity updateMetadataFieldValues(CategoryMetadataFieldValuesCo co) {

        Optional<Category> category = categoryRepository.findById(co.getCategoryId());
        Optional<CategoryMetadataField> field = categoryMetadataFieldRepository.findById(co.getMetadataFieldId());

        if(!category.isPresent())
            throw new ResourceNotFoundException("No Category found with ID : " + co.getCategoryId());
        if(!field.isPresent())
            throw new ResourceNotFoundException("No CategoryMetadataField found with ID : " + co.getCategoryId());
        //check if the category is a leaf category
        if(category.get().getSubCategoriesSet() != null)
            throw new GenericUserValidationFailedException("The category is not a leaf category, please enter ID of a valid leaf category.");

        // checking if all the values are unique
        String[] fieldValues = co.getValues().split(",");
        Set<String> fieldValuesSet = new HashSet<String>(Arrays.asList(fieldValues));

        if(fieldValues.length != fieldValuesSet.size())
            throw new GenericUserValidationFailedException("Values should be Unique!");

        // checking if a value already exists for given combination of Category and CategoryMetadataField
        CategoryMetadataFieldValuesId id = new CategoryMetadataFieldValuesId();
        id.setCategoryId(category.get().getId());
        id.setCategoryMetadataFieldId(field.get().getId());

        Optional<CategoryMetadataFieldValues> values = categoryMetadataFieldValuesRepository.findById(id);

        if(!values.isPresent())
            throw new ResourceNotFoundException("No values found for given combination of Category and CategoryMetadataField!");


        //adding new field values
        List<String> oldFieldValues = Arrays.asList(values.get().getValues().split(","));
        List<String> newFieldValues = Arrays.asList(co.getValues().split(","));

        Set<String> uniqueFieldValuesSet = new HashSet<>();
        uniqueFieldValuesSet.addAll(oldFieldValues);
        uniqueFieldValuesSet.addAll(newFieldValues);

        String uniqueFieldValues = String.join(",",uniqueFieldValuesSet);

        values.get().setValues(uniqueFieldValues);

        // persisting
        categoryMetadataFieldValuesRepository.save(values.get());

        return new ResponseEntity(HttpStatus.OK);

    }

    public ResponseEntity getAllCategoriesForSeller() {

        List<Category> leafCategories = categoryRepository.getAllLeafCategories();
        List<CategoryViewDto> dtoList = ObjectMapperUtils.mapAllList(leafCategories, CategoryViewDto.class);
        return new ResponseEntity<List<CategoryViewDto>>(dtoList, null, HttpStatus.OK);

    }

    public ResponseEntity getAllCategoriesForCustomer(Optional<Long> categoryId) {

        log.info("inside : service -> getAllCategoriesForCustomer");

        if(categoryId.isPresent())
        {
            log.info("inside : service -> if -> getAllCategoriesForCustomer");
            List<Category> immediateChildCategoryList = categoryRepository.getImmediateChildCategories(categoryId.get());
            List<CategoryViewDto> dtoList = ObjectMapperUtils.mapAllList(immediateChildCategoryList, CategoryViewDto.class);
            return new ResponseEntity<List<CategoryViewDto>>(dtoList, null, HttpStatus.OK);
        }
        else
         {
              log.info("inside : service -> else -> getAllCategoriesForCustomer");
              List<Category> rootCategoryList = categoryRepository.getAllRootCategories();
              List<CategoryViewDto> dtoList = ObjectMapperUtils.mapAllList(rootCategoryList, CategoryViewDto.class);
              return new ResponseEntity<List<CategoryViewDto>>(dtoList, null, HttpStatus.OK);
         }

    }
}
