package com.chiragbohet.ecommerce.dtos.AdminApi;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAdminApiDto {

    Long id;
    String firstName;
    String lastName;
    String email;
    boolean isActive;

}
