package com.chiragbohet.ecommerce.dtos.productapi;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

// similar to admin view dto, just does not contain is active field

@Getter
@Setter
public class ProductCustomerViewDto {

    String name;

    String description;

    String brand;

    Boolean isCancellable;

    Boolean isReturnable;

    Set<ProductVariationCustomerViewDto> productVariationSet;
}
