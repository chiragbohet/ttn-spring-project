package com.chiragbohet.ecommerce.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressViewDto {

    Long id;
    String city;
    String state;
    String country;
    String addressLine;
    String label;
    String zipCode;

}
