package com.chiragbohet.ecommerce.Services;

import com.chiragbohet.ecommerce.Entities.CategoryRelated.Category;
import com.chiragbohet.ecommerce.Entities.CategoryRelated.CategoryMetadataField;
import com.chiragbohet.ecommerce.Entities.CategoryRelated.CategoryMetadataFieldValues;
import com.chiragbohet.ecommerce.Entities.CategoryRelated.CategoryMetadataFieldValuesId;
import com.chiragbohet.ecommerce.Entities.ProductRelated.Product;
import com.chiragbohet.ecommerce.Entities.ProductRelated.ProductVariation;
import com.chiragbohet.ecommerce.Entities.UserRelated.Seller;
import com.chiragbohet.ecommerce.Exceptions.GenericUserValidationFailedException;
import com.chiragbohet.ecommerce.Exceptions.ResourceNotFoundException;
import com.chiragbohet.ecommerce.Repositories.*;
import com.chiragbohet.ecommerce.Utilities.EmailSenderService;
import com.chiragbohet.ecommerce.co.ProductCo;
import com.chiragbohet.ecommerce.co.ProductVariationCo;
import lombok.extern.log4j.Log4j2;
import net.minidev.json.JSONUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.RefreshTokenReactiveOAuth2AuthorizedClientProvider;
import org.springframework.stereotype.Service;
import org.w3c.dom.ls.LSOutput;

import java.util.*;

@Log4j2
@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    SellerRepository sellerRepository;

    @Autowired
    CategoryMetadataFieldRepository categoryMetadataFieldRepository;

   @Autowired
   ProductVariationRepository productVariationRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    EmailSenderService emailSenderService;

    public ResponseEntity addNewProduct(ProductCo co, String sellerEmail) {

        //TODO : Check product name unique with respect to (brand, category, seller) combination

        Optional<Category> productCategory = categoryRepository.findById(co.getCategoryId());

        // checking if valid category
        if(!productCategory.isPresent())
            throw new ResourceNotFoundException("No category found with id : " + co.getCategoryId());

        // checking if category is a leaf category
        if(productCategory.get().isLeafCategory())
            throw new GenericUserValidationFailedException("Category associated with id : " + co.getCategoryId() + " is not a leaf category.");


        // setting fields
        Product product = modelMapper.map(co, Product.class);

        // setting these explicitly because model mapper uses reflection and hence default constructor is not called
        product.setIsReturnable(co.getIsReturnable() != null? co.getIsReturnable() : false);
        product.setIsCancellable(co.getIsCancellable() != null? co.getIsReturnable() : false);

        product.setIsActive(false);
        product.setCategory(productCategory.get());
        product.setSeller(sellerRepository.findByEmail(sellerEmail));

        //persisting
        productRepository.save(product);

        // sending email to Admin for intimidation
        emailSenderService.sendEmail(emailSenderService.getAdminNewProductAddedIntimidationEmail(product.toString()));

        return new ResponseEntity(HttpStatus.CREATED);
    }

    public ResponseEntity addNewProductVariation(ProductVariationCo co) {

        log.info("Inside addNewProductVariation()");

        Optional<Product> product = productRepository.findById(co.getProductId());

        if(!product.isPresent())
            throw new ResourceNotFoundException("No product found with ID : " + co.getProductId());
        // TODO : Check isDeleted after adding auditing support, add proper image support
        if(!product.get().getIsActive())
            throw new GenericUserValidationFailedException("The product with ID : " + co.getProductId() + " is not yet Activated, please get it activated by Admin first.");

        // validating metadata
        Set<CategoryMetadataFieldValues> fieldValuesSet = product.get().getCategory().getFieldValuesSet();

        Set<String> validFieldNames = new HashSet<>();

        for(CategoryMetadataFieldValues fieldValue : fieldValuesSet)
            validFieldNames.add(fieldValue.getCategoryMetadataField().getName());


        for(Map.Entry<String, String> entry : co.getMetadata().entrySet())
        {
            // TODO : Improve this, not very optimized. PS : It ain't much but its honest work.

            if(validFieldNames.contains(entry.getKey()))
            {
                Set<String> validFieldValues = new HashSet<>();

                for(CategoryMetadataFieldValues fieldValue : fieldValuesSet)
                {
                    if(fieldValue.getCategoryMetadataField().getName().equals(entry.getKey()))
                        validFieldValues.addAll(Arrays.asList(fieldValue.getValues().split(",")));
                }

                if(!validFieldValues.contains(entry.getValue()))
                    throw new GenericUserValidationFailedException("Value : " + entry.getValue() + ", is not a valid value for Field : " + entry.getKey() + ".");

            }
            else
                throw new GenericUserValidationFailedException("Field : " + entry.getKey() + ", is not to be associated with any product with ID " + co.getProductId());

        }
        log.info("Inside addNewProductVariation() - > about to map co to object using modelMapper");

        ProductVariation productVariation = modelMapper.map(co, ProductVariation.class);
        productVariation.setIsActive(true);
        productVariation.setProduct(product.get());

        log.info("Inside addNewProductVariation() - > mapped co to object, trying to persist");
        productVariationRepository.save(productVariation);
        log.info("Inside addNewProductVariation() - > mapped co to object, persisted!");
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
