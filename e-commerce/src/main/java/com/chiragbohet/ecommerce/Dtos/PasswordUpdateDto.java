package com.chiragbohet.ecommerce.Dtos;

import com.chiragbohet.ecommerce.Utilities.GlobalVariables;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class PasswordUpdateDto {


    @Pattern(regexp = GlobalVariables.REGEX_PASSWORD, message = "8-15 Characters with atleast 1 Lower case, 1 Upper case, 1 Special Character, 1 Number")
    @NotNull(message = "Password cannot be null!")
    private String password;

    @Pattern(regexp = GlobalVariables.REGEX_PASSWORD, message = "8-15 Characters with atleast 1 Lower case, 1 Upper case, 1 Special Character, 1 Number")
    @NotNull(message = "Password cannot be null!")
    private String confirmPassword;

}
