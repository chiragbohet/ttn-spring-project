package com.chiragbohet.ecommerce.co;

import com.chiragbohet.ecommerce.utilities.GlobalVariables;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class CustomerRegistrationCo extends UserRegistrationCo {

    @Pattern(regexp = GlobalVariables.REGEX_MOBILE_NUMBER, message = "Please enter a valid mobile number")
    @NotNull(message = "Contact number cannot be null!")
    private String contact;

}
