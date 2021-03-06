package com.chiragbohet.ecommerce.services;

import com.chiragbohet.ecommerce.co.ProductCo;
import com.chiragbohet.ecommerce.co.ProductUpdateCo;
import com.chiragbohet.ecommerce.co.ProductVariationCo;
import com.chiragbohet.ecommerce.co.ProductVariationUpdateCo;
import com.chiragbohet.ecommerce.dtos.productapi.ProductAdminViewDto;
import com.chiragbohet.ecommerce.dtos.productapi.ProductCustomerViewDto;
import com.chiragbohet.ecommerce.dtos.productapi.ProductDto;
import com.chiragbohet.ecommerce.dtos.productapi.ProductVariationDto;
import com.chiragbohet.ecommerce.entities.Category;
import com.chiragbohet.ecommerce.entities.Product;
import com.chiragbohet.ecommerce.entities.ProductVariation;
import com.chiragbohet.ecommerce.entities.Seller;
import com.chiragbohet.ecommerce.exceptions.GenericUserValidationFailedException;
import com.chiragbohet.ecommerce.exceptions.ResourceNotFoundException;
import com.chiragbohet.ecommerce.repositories.*;
import com.chiragbohet.ecommerce.utilities.EmailSenderService;
import com.chiragbohet.ecommerce.utilities.ObjectMapperUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Autowired
    RabbitTemplate rabbitTemplate;

    // for serializing and deserializing of objects sent using RabbitMQ
    private ObjectMapper objectMapper = new ObjectMapper();


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
        product.setId(null);    //TODO : ModelMapper is setting this id as the category id, fix this bug.
        //log.trace("ProductService -> addNewProduct -> Product object : " + product);

        // setting these explicitly because model mapper uses reflection and hence default constructor is not called
        product.setIsReturnable(co.getIsReturnable() != null ? co.getIsReturnable() : false);
        product.setIsCancellable(co.getIsCancellable() != null ? co.getIsReturnable() : false);

        product.setIsActive(false);
        product.setCategory(productCategory.get());

        Seller productSeller = sellerRepository.findByEmail(sellerEmail);
        product.setSeller(productSeller);

        // persisting
        productRepository.save(product);

        // sending email to Admin for intimidation
        emailSenderService.sendEmail(emailSenderService.getAdminNewProductAddedIntimidationEmail(product.toString()));

        // writing to RabbitMQ
        log.trace("ProductService -> addNewProduct -> writing product to RabbitMQ");
        rabbitTemplate.convertAndSend("ProjectExchange", "productUpdate", product);

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
        log.info("Inside addNewProductVariation() - > about to map co to object using modelMapper");

        ProductVariation productVariation = modelMapper.map(co, ProductVariation.class);
        productVariation.setIsActive(true);
        product.get().addProductVariation(productVariation);    // changes
        log.info("Inside addNewProductVariation() - > mapped co to object, trying to persist");

        // setter will validate and populate metadata fields
        productVariation.setMetadata(co.getMetadata());

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

        // writing to RabbitMQ
        log.trace("ProductService -> updateProductForSeller -> writing product to RabbitMQ");
        rabbitTemplate.convertAndSend("ProjectExchange", "productUpdate", product);

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
            products = productRepository.getAllProductsByLeafCategoryId(requestedCategory.get().getId(), PageRequest.of(page.get(), size.get(), sortingDirection, sortProperty.get()));

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
        Page<Product> productList = productRepository.findAll(PageRequest.of(page.get(), size.get(), sortingDirection, sortProperty.get()));
        List<ProductAdminViewDto> dtos = ObjectMapperUtils.mapAllPage(productList, ProductAdminViewDto.class);

        return new ResponseEntity<>(dtos, null, HttpStatus.OK);
    }

    public ResponseEntity getSimilarProductsForCustomer(Long productId, Optional<Integer> page, Optional<Integer> size, Optional<String> sortDirection, Optional<String> sortProperty) {

        Sort.Direction sortingDirection = sortDirection.get().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;

        Optional<Product> product = productRepository.findById(productId);

        if (!product.isPresent())
            throw new ResourceNotFoundException("No product found with product ID : " + productId);

        //TODO : Add a better matching criteria, rn returning list of products in the same category.
        List<Product> similarProducts = productRepository.getAllActiveProductsByCategoryId(product.get().getCategory().getId(), PageRequest.of(page.get(), size.get(), sortingDirection, sortProperty.get()));

        if (similarProducts.isEmpty())
            throw new ResourceNotFoundException("No similar products found!");

        List<ProductCustomerViewDto> dtoList = ObjectMapperUtils.mapAllList(similarProducts, ProductCustomerViewDto.class);

        return new ResponseEntity<>(dtoList, null, HttpStatus.OK);
    }
}
