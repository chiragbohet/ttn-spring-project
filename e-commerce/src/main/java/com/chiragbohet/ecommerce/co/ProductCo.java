package com.chiragbohet.ecommerce.co;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ProductCo {

    // mandatory

    @NotNull
    String name;

    @NotNull
    String brand;

    @NotNull
    Long categoryId;

    // optional

    String description;

    Boolean isCancellable;

    Boolean isReturnable;
}
