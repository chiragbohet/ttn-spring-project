package com.chiragbohet.ecommerce.dtos.CustomerApi;

import com.chiragbohet.ecommerce.dtos.UserProfileUpdateDto;
import com.chiragbohet.ecommerce.utilities.GlobalVariables;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;

@Getter
@Setter
public class CustomerProfileUpdateDto extends UserProfileUpdateDto {

    @Pattern(regexp = GlobalVariables.REGEX_MOBILE_NUMBER, message = "Please enter a valid number!")
    private String contact;

}
