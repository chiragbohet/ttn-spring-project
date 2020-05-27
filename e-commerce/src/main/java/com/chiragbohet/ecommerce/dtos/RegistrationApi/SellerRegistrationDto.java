package com.chiragbohet.ecommerce.dtos.RegistrationApi;

import com.chiragbohet.ecommerce.entities.Address;
import com.chiragbohet.ecommerce.utilities.GlobalVariables;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class SellerRegistrationDto extends UserRegistrationDto {

    @NotNull(message = "GST cannot be null!")
    @Pattern(regexp = GlobalVariables.REGEX_GST_NUMBER, message = "Please enter a valid GST Number!")
    private String Gst;

    @NotNull(message = "Company contact cannot be null!")
    @Pattern(regexp = GlobalVariables.REGEX_MOBILE_NUMBER, message = "Please enter a valid number!")
    private String companyContact;

    @NotNull(message = "Company name cannot be null!")
    private String companyName;

    @NotNull(message = "Address cannot be null!")
    private Address address;

}
