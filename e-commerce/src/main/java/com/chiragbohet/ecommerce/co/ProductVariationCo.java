package com.chiragbohet.ecommerce.co;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class ProductVariationCo {

    @NotNull
    Long productId;

    @NotNull
    Map<String,String> metadata;

    @NotNull
    String primaryImageName;

    @Min(value = 0, message = "Minimum quantity should be 0")
    Long quantityAvailable;

    @Min(value = 0, message = "Minimum price should be 0")
    BigDecimal price;

    // TODO : Implement this
    //List<String> secondaryImages;
}
