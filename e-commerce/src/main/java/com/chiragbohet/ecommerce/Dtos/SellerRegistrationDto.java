package com.chiragbohet.ecommerce.Dtos;

import com.chiragbohet.ecommerce.Entities.UserRelated.Address;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class SellerRegistrationDto extends UserRegistrationDto {

    @NotNull(message = "GST cannot be null!")
    @Pattern(regexp = "\\d{2}[A-Z]{5}\\d{4}[A-Z]{1}[A-Z\\d]{1}[Z]{1}[A-Z\\d]{1}", message = "Please enter a valid GST Number!")
    private String Gst;

    @NotNull(message = "Company contact cannot be null!")
    @Pattern(regexp = "^[789]\\d{9}$", message = "Please enter a valid number!")
    private String companyContact;

    @NotNull(message = "Company name cannot be null!")
    private String companyName;

    @NotNull(message = "Address cannot be null!")
    private Address address;

}
