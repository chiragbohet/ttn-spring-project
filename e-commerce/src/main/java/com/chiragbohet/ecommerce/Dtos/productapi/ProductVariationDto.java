package com.chiragbohet.ecommerce.Dtos.productapi;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
public class ProductVariationDto {

    ProductDto product;

    private Long quantityAvailable;

    private BigDecimal price;

    Boolean isActive;

    private String primaryImageName;
    
    private Map<String, String> metadata;

}
