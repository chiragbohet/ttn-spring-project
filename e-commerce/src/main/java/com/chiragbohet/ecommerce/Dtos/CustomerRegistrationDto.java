package com.chiragbohet.ecommerce.Dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class CustomerRegistrationDto extends UserRegistrationDto {

    // ref : https://stackoverflow.com/questions/3813195/regular-expression-for-indian-mobile-numbers
    // for conversion to java compatible regex : https://stackoverflow.com/questions/2945783/easy-way-to-convert-regex-to-a-java-compatible-regex
    @Pattern(regexp = "^[789]\\d{9}$", message = "Please enter a valid number!")
    @NotNull(message = "Contact number cannot be null!")
    private String contact;

}
