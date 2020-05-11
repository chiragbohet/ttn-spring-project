package com.chiragbohet.ecommerce.Dtos.productapi;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Map;

// similar to admin product variation admin view dto, just does not contain the isActive field

@Getter
@Setter
public class ProductVariationCustomerViewDto {

    Long quantityAvailable;

    BigDecimal price;

    String primaryImageName;

    Map<String, String> metadata;

}
