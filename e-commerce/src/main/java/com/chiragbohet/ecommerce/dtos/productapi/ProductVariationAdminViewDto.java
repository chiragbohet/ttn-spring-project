package com.chiragbohet.ecommerce.dtos.productapi;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
public class ProductVariationAdminViewDto {

    Long quantityAvailable;

    BigDecimal price;

    Boolean isActive;

    String primaryImageName;

    Map<String, String> metadata;

}
