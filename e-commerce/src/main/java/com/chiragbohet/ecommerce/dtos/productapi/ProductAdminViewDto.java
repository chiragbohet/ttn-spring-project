package com.chiragbohet.ecommerce.dtos.productapi;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class ProductAdminViewDto {

    String name;

    String description;

    String brand;

    Boolean isCancellable;

    Boolean isReturnable;

    Boolean isActive;

    CategoryAdminViewDto category;

    Set<ProductVariationAdminViewDto> productVariationSet;

}
