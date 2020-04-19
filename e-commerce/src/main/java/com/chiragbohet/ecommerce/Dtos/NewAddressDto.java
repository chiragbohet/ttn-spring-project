package com.chiragbohet.ecommerce.Dtos;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewAddressDto {

    String city;

    String state;

    String country;

    String addressLine;

    String zipCode;

    String label;
}
