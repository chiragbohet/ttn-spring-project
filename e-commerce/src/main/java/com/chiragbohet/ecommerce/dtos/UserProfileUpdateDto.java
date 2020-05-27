package com.chiragbohet.ecommerce.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
public class UserProfileUpdateDto {

    @Size(min = 1)
    private String firstName;

    @Size(min = 1)
    private String middleName;

    @Size(min = 1)
    private String lastName;
}
