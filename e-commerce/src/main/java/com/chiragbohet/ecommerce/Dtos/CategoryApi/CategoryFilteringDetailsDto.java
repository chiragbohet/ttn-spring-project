package com.chiragbohet.ecommerce.Dtos.CategoryApi;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class CategoryFilteringDetailsDto {

    // List<CategoryViewDto>  sameLevelAndWithSameParent;
    Map<String, String> fieldAndValues;
    List<String> brandsList;
    BigDecimal maxPrice;
    BigDecimal minPrice;


}
