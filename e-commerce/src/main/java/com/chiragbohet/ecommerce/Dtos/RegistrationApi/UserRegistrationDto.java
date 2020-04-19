package com.chiragbohet.ecommerce.Dtos.RegistrationApi;

import com.chiragbohet.ecommerce.Utilities.GlobalVariables;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class UserRegistrationDto {


    @Pattern(regexp = GlobalVariables.REGEX_PASSWORD)
    @NotNull(message = "Email cannot be null!")
    private String email;

    @NotNull(message = "First name cannot be null!")
    private String firstName;

    private String middleName;

    @NotNull(message = "Last name cannot be null!")
    private String lastName;

    @Pattern(regexp = GlobalVariables.REGEX_PASSWORD)
    @NotNull(message = "Password cannot be null!")
    private String password;


    @Pattern(regexp = GlobalVariables.REGEX_PASSWORD)
    @NotNull(message = "Confirm password cannot be null!")
    private String confirmPassword;

}
