package com.chiragbohet.ecommerce.Dtos.CustomerApi;

import com.chiragbohet.ecommerce.Dtos.UserProfileUpdateDto;
import com.chiragbohet.ecommerce.Utilities.GlobalVariables;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class CustomerProfileUpdateDto extends UserProfileUpdateDto {

    @Pattern(regexp = GlobalVariables.REGEX_MOBILE_NUMBER, message = "Please enter a valid number!")
    private String contact;

}
