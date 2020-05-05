package com.chiragbohet.ecommerce.co;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CategoryUpdateCo {

    @NotNull
    Long id;

    @NotNull
    String name;
}
