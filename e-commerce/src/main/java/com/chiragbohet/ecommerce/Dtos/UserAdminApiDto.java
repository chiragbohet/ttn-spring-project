package com.chiragbohet.ecommerce.Dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAdminApiDto {

    Long id;
    String firstName;
    String lastName;
    boolean isActive;

}
