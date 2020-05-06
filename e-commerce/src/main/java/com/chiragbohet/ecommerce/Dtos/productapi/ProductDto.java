package com.chiragbohet.ecommerce.Dtos.productapi;

import com.chiragbohet.ecommerce.Dtos.CategoryApi.CategoryViewDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {

    String name;

    String description;

    String brand;

    Boolean isCancellable;

    Boolean isReturnable;

    Boolean isActive;

    CategoryViewDto category;

}
