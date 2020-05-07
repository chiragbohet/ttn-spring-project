package com.chiragbohet.ecommerce.co;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
public class ProductVariationUpdateCo {

    Long quantityAvailable;

    BigDecimal price;

    Map<String, String> metadata;

    String primaryImageName;


    // TODO : Add secondary images

    Boolean isActive;





}
