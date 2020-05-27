package com.chiragbohet.ecommerce.dtos.CustomerApi;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDetailsDto {
    Long id;
    String firstName;
    String lastName;
    boolean isActive;
    String contact;
    // TODO : Add image here
}
