package com.chiragbohet.ecommerce.Dtos.RegistrationApi;

import com.chiragbohet.ecommerce.Utilities.GlobalVariables;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class EmailDto {

    @Pattern(regexp = GlobalVariables.REGEX_EMAIL, message = "Please enter a valid email id!")
    @NotNull(message = "Please enter a email id!")
    String email;
}
