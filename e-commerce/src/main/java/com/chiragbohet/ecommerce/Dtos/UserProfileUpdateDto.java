package com.chiragbohet.ecommerce.Dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class UserProfileUpdateDto {

    private String firstName;

    private String middleName;

    private String lastName;
}
