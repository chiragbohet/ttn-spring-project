package com.chiragbohet.ecommerce.co;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CategoryMetadataFieldCo {

    @NotNull(message = "field name cannot be null!")
    String name;
}
