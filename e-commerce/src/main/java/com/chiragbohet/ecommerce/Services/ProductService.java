package com.chiragbohet.ecommerce.Services;

import com.chiragbohet.ecommerce.Dtos.productapi.ProductAdminViewDto;
import com.chiragbohet.ecommerce.Dtos.productapi.ProductDto;
import com.chiragbohet.ecommerce.Dtos.productapi.ProductVariationDto;
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
import com.chiragbohet.ecommerce.Utilities.ObjectMapperUtils;
import com.chiragbohet.ecommerce.co.ProductCo;
import com.chiragbohet.ecommerce.co.ProductUpdateCo;
import com.chiragbohet.ecommerce.co.ProductVariationCo;
import com.chiragbohet.ecommerce.co.ProductVariationUpdateCo;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.RefreshTokenReactiveOAuth2AuthorizedClientProvider;
import org.springframework.stereotype.Service;
import org.w3c.dom.ls.LSOutput;

import java.util.*;

@Slf4j
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
        if (!productCategory.isPresent())
            throw new ResourceNotFoundException("No category found with id : " + co.getCategoryId());

        // checking if category is a leaf category
        if (productCategory.get().isLeafCategory())
            throw new GenericUserValidationFailedException("Category associated with id : " + co.getCategoryId() + " is not a leaf category.");


        // setting fields
        Product product = modelMapper.map(co, Product.class);

        // setting these explicitly because model mapper uses reflection and hence default constructor is not called
        product.setIsReturnable(co.getIsReturnable() != null ? co.getIsReturnable() : false);
        product.setIsCancellable(co.getIsCancellable() != null ? co.getIsReturnable() : false);

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

        if (!product.isPresent())
            throw new ResourceNotFoundException("No product found with ID : " + co.getProductId());
        // TODO : Check isDeleted after adding auditing support, add proper image support
        if (!product.get().getIsActive())
            throw new GenericUserValidationFailedException("The product with ID : " + co.getProductId() + " is not yet Activated, please get it activated by Admin first.");

        // validating metadata
        Set<CategoryMetadataFieldValues> fieldValuesSet = product.get().getCategory().getFieldValuesSet();

        Set<String> validFieldNames = new HashSet<>();

        for (CategoryMetadataFieldValues fieldValue : fieldValuesSet)
            validFieldNames.add(fieldValue.getCategoryMetadataField().getName());


        for (Map.Entry<String, String> entry : co.getMetadata().entrySet()) {
            // TODO : Improve this, not very optimized. PS : It ain't much but its honest work.

            if (validFieldNames.contains(entry.getKey())) {
                Set<String> validFieldValues = new HashSet<>();

                for (CategoryMetadataFieldValues fieldValue : fieldValuesSet) {
                    if (fieldValue.getCategoryMetadataField().getName().equals(entry.getKey()))
                        validFieldValues.addAll(Arrays.asList(fieldValue.getValues().split(",")));
                }

                if (!validFieldValues.contains(entry.getValue()))
                    throw new GenericUserValidationFailedException("Value : " + entry.getValue() + ", is not a valid value for Field : " + entry.getKey() + ".");

            } else
                throw new GenericUserValidationFailedException("Field : " + entry.getKey() + ", is not to be associated with any product with ID " + co.getProductId());

        }
        log.info("Inside addNewProductVariation() - > about to map co to object using modelMapper");

        ProductVariation productVariation = modelMapper.map(co, ProductVariation.class);
        productVariation.setIsActive(true);
        //productVariation.setProduct(product.get());
        product.get().addProductVariation(productVariation);    // changes
        log.info("Inside addNewProductVariation() - > mapped co to object, trying to persist");

        //productVariationRepository.save(productVariation);
        productRepository.save(product.get());
        log.info("Inside addNewProductVariation() - > mapped co to object, persisted!");
        return new ResponseEntity(HttpStatus.CREATED);
    }

    public ResponseEntity getSellerProduct(String sellerEmail, Long productId) {

        Seller seller = sellerRepository.findByEmail(sellerEmail);

        // unlikely, but still handling.
        if (seller == null)
            throw new ResourceNotFoundException("No seller found with email : " + sellerEmail + ". Please contact customer care.");

        Optional<Product> product = productRepository.findById(productId);
        if (!product.isPresent())
            throw new ResourceNotFoundException("No product found with ID : " + productId);
        if (product.get().getSeller() != seller)
            throw new GenericUserValidationFailedException("The product ID : " + productId + " is not associated with your account.");

        // TODO : Check product should be non deleted (soft delete)

        ProductDto dto = modelMapper.map(product.get(), ProductDto.class);

        return new ResponseEntity<ProductDto>(dto, null, HttpStatus.OK);
    }

    public ResponseEntity getSellerProductVariation(String sellerEmail, Long productVariationId) {

        Seller seller = sellerRepository.findByEmail(sellerEmail);

        // unlikely, but still handling.
        if (seller == null)
            throw new ResourceNotFoundException("No seller found with email : " + sellerEmail + ". Please contact customer care.");

        Optional<ProductVariation> productVariation = productVariationRepository.findById(productVariationId);

        if (!productVariation.isPresent())
            throw new ResourceNotFoundException("No product variation found with ID : " + productVariationId);
        if (productVariation.get().getProduct().getSeller() != seller)
            throw new GenericUserValidationFailedException("The product variation with ID : " + productVariation + " is not associated with your account.");

        // TODO : Check product should be non deleted (soft delete)

        ProductVariationDto dto = modelMapper.map(productVariation.get(), ProductVariationDto.class);

        return new ResponseEntity<ProductVariationDto>(dto, null, HttpStatus.OK);
    }

    public ResponseEntity getAllSellerProducts(Optional<Integer> page, Optional<Integer> size, Optional<String> sortProperty, Optional<String> sortDirection, String sellerEmail) {

        Seller seller = sellerRepository.findByEmail(sellerEmail);

        // unlikely, but still handling.
        if (seller == null)
            throw new ResourceNotFoundException("No seller found with email : " + sellerEmail + ". Please contact customer care.");

        Sort.Direction sortingDirection = sortDirection.get().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;

        List<Product> sellerProductsList = productRepository.getAllProductsBySellerId(seller.getId(), PageRequest.of(page.get(), size.get(), sortingDirection, sortProperty.get()));
        List<ProductDto> dtos = ObjectMapperUtils.mapAllList(sellerProductsList, ProductDto.class);

        return new ResponseEntity<List<ProductDto>>(dtos, null, HttpStatus.OK);
    }

    public ResponseEntity getAllVariationsOfAProductBySeller(Optional<Integer> page, Optional<Integer> size, Optional<String> sortProperty, Optional<String> sortDirection, String sellerEmail, Long productId) {

        Sort.Direction sortingDirection = sortDirection.get().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;

        Seller seller = sellerRepository.findByEmail(sellerEmail);

        // unlikely, but still handling.
        if (seller == null)
            throw new ResourceNotFoundException("No seller found with email : " + sellerEmail + ". Please contact customer care.");

        Optional<Product> product = productRepository.findById(productId);

        if (!product.isPresent())
            throw new ResourceNotFoundException("No product found with ID : " + productId);
        if (product.get().getSeller() != seller)
            throw new GenericUserValidationFailedException("The product ID : " + productId + " is not associated with your account.");

        List<ProductVariation> productVariationSet = productVariationRepository.getAllProductVariationsByProductId(productId, PageRequest.of(page.get(), size.get(), sortingDirection, sortProperty.get()));
        List<ProductVariationDto> dtoList = ObjectMapperUtils.mapAllList(productVariationSet, ProductVariationDto.class);

        return new ResponseEntity<List<ProductVariationDto>>(dtoList, null, HttpStatus.OK);

    }

    public ResponseEntity deleteProductForSeller(String sellerEmail, Long productId) {

        Seller seller = sellerRepository.findByEmail(sellerEmail);

        // unlikely, but still handling.
        if (seller == null)
            throw new ResourceNotFoundException("No seller found with email : " + sellerEmail + ". Please contact customer care.");

        Optional<Product> product = productRepository.findById(productId);

        if (!product.isPresent())
            throw new ResourceNotFoundException("No product found with ID : " + productId);
        if (product.get().getSeller() != seller)
            throw new GenericUserValidationFailedException("The product ID : " + productId + " is not associated with your account.");

        // TODO : Do a soft delete here

        productRepository.delete(product.get());

        return new ResponseEntity(HttpStatus.OK);
    }

    public ResponseEntity updateProductForSeller(String sellerEmail, Long productId, ProductUpdateCo co) {

        Seller seller = sellerRepository.findByEmail(sellerEmail);

        // unlikely, but still handling.
        if (seller == null)
            throw new ResourceNotFoundException("No seller found with email : " + sellerEmail + ". Please contact customer care.");

        Optional<Product> product = productRepository.findById(productId);

        if (!product.isPresent())
            throw new ResourceNotFoundException("No product found with ID : " + productId);
        if (product.get().getSeller() != seller)
            throw new GenericUserValidationFailedException("The product ID : " + productId + " is not associated with your account.");

        // updating fields if present in the co
        if (co.getName() != null)
            product.get().setName(co.getName());
        if (co.getDescription() != null)
            product.get().setDescription(co.getDescription());
        if (co.getIsCancellable() != null)
            product.get().setIsCancellable(co.getIsCancellable());
        if (co.getIsReturnable() != null)
            product.get().setIsReturnable(co.getIsReturnable());

        productRepository.save(product.get());

        return new ResponseEntity(HttpStatus.OK);
    }


    public ResponseEntity updateProductVariationForSeller(String sellerEmail, Long productVariationId, ProductVariationUpdateCo co) {

        Seller seller = sellerRepository.findByEmail(sellerEmail);

        // unlikely, but still handling.
        if (seller == null)
            throw new ResourceNotFoundException("No seller found with email : " + sellerEmail + ". Please contact customer care.");

        Optional<ProductVariation> productVariation = productVariationRepository.findById(productVariationId);

        if (!productVariation.isPresent())
            throw new ResourceNotFoundException("No product variation found with ID : " + productVariationId);
        if (productVariation.get().getProduct().getSeller() != seller)
            throw new GenericUserValidationFailedException("The product variation with ID : " + productVariation + " is not associated with your account.");

        // updating the fields if present
        if (co.getQuantityAvailable() != null)
            productVariation.get().setQuantityAvailable(co.getQuantityAvailable());
        if (co.getPrice() != null)
            productVariation.get().setPrice(co.getPrice());
        // TODO : Add validation for metadata fields here
//        if(co.getMetadata() != null)
//            productVariation.get().setMetadata(co.getMetadata());
//
        if (co.getPrimaryImageName() != null)
            productVariation.get().setPrimaryImageName(co.getPrimaryImageName());
        if (co.getIsActive() != null)
            productVariation.get().setIsActive(co.getIsActive());

        productVariationRepository.save(productVariation.get());

        return new ResponseEntity(HttpStatus.OK);
    }

    public ResponseEntity getProductForAdmin(Long productId) {

        Optional<Product> product = productRepository.findById(productId);

        if (!product.isPresent())
            throw new ResourceNotFoundException("No product found with ID " + productId);

        ProductAdminViewDto dto = modelMapper.map(product.get(), ProductAdminViewDto.class);

        return new ResponseEntity<ProductAdminViewDto>(dto, null, HttpStatus.OK);
    }

    public ResponseEntity deactivateProductForAdmin(Long productId) {

        Optional<Product> product = productRepository.findById(productId);

        //checking if product id is valid
        if (!product.isPresent())   // TODO : Check if product is not soft deleted
            throw new ResourceNotFoundException("No product found with ID " + productId);

        //checking if product is already inactive
        if (!product.get().getIsActive())
            throw new GenericUserValidationFailedException("Product with ID " + productId + " is already inactive!");


        product.get().setIsActive(false);
        productRepository.save(product.get());
        emailSenderService.sendEmail(emailSenderService.getSellerProductDeactivatedIntimidationEmail(product.get().getSeller().getEmail(), product.get().toString()));

        return new ResponseEntity(HttpStatus.OK);
    }

    public ResponseEntity activateProductForAdmin(Long productId) {

        Optional<Product> product = productRepository.findById(productId);

        //checking if product id is valid
        if (!product.isPresent())   // TODO : Check if product is not soft deleted
            throw new ResourceNotFoundException("No product found with ID " + productId);

        //checking if product is already active
        if (product.get().getIsActive())
            throw new GenericUserValidationFailedException("Product with ID " + productId + " is already active!");


        product.get().setIsActive(true);
        productRepository.save(product.get());
        emailSenderService.sendEmail(emailSenderService.getSellerProductActivatedIntimidationEmail(product.get().getSeller().getEmail(), product.get().toString()));

        return new ResponseEntity(HttpStatus.OK);
    }

    public ResponseEntity getProductForCustomer(Long productId) {

        Optional<Product> product = productRepository.findById(productId);

        if (!product.isPresent())
            throw new ResourceNotFoundException("No Product found with ID : " + productId);
        if (!product.get().getIsActive())
            throw new GenericUserValidationFailedException("Product with ID " + productId + " is not active yet.");
        // TODO : Check if the product is not soft deleted
        if (product.get().getProductVariationSet() == null)
            throw new GenericUserValidationFailedException("Product with ID " + productId + " has no variations associated with it yet");

        ProductAdminViewDto dto = modelMapper.map(product.get(), ProductAdminViewDto.class);

        return new ResponseEntity<ProductAdminViewDto>(dto, null, HttpStatus.OK);
    }

    public ResponseEntity getAllProductsForCustomer(Long categoryId, Optional<Integer> page, Optional<Integer> size, Optional<String> sortDirection, Optional<String> sortProperty) {

        Sort.Direction sortingDirection = sortDirection.get().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;

        log.trace("Inside getAllProductsForCustomer()");
        Optional<Category> requestedCategory = categoryRepository.findById(categoryId);

        if(!requestedCategory.isPresent())
            throw new ResourceNotFoundException("No category found with ID : " + categoryId);

        List<Product> products = null;

        if(requestedCategory.get().isLeafCategory())
        {
            log.trace("Inside getAllProductsForCustomer() -> valid leaf category");
            products = productRepository.getAllProductsByCategoryId(requestedCategory.get().getId(), PageRequest.of(page.get(), size.get(), sortingDirection, sortProperty.get()));

        }
        else
        {
            log.trace("Inside getAllProductsForCustomer()  -> not a leaf category");
            products = productRepository.getAllLeafNodeCategoryProducts(PageRequest.of(page.get(), size.get(), sortingDirection, sortProperty.get()));
        }

        List<ProductAdminViewDto> dtos = ObjectMapperUtils.mapAllList(products, ProductAdminViewDto.class);


        return new ResponseEntity<>(dtos,null,HttpStatus.OK);
    }


    public ResponseEntity getAllProductsForAdmin(Optional<Integer> page, Optional<Integer> size, Optional<String> sortDirection, Optional<String> sortProperty) {

        Sort.Direction sortingDirection = sortDirection.get().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        // TODO : Return all non (soft) deleted products.
        Page<Product> productList = productRepository.findAll(PageRequest.of(page.get(),size.get(),sortingDirection,sortProperty.get()));
        List<ProductAdminViewDto> dtos = ObjectMapperUtils.mapAllPage (productList, ProductAdminViewDto.class);

        return new ResponseEntity<>(dtos,null,HttpStatus.OK);
    }
}
