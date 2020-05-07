package com.chiragbohet.ecommerce.co;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductUpdateCo {

    String name;

    String description;

    Boolean isCancellable;

    Boolean isReturnable;

}
