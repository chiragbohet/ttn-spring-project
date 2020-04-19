package com.chiragbohet.ecommerce.Dtos.SellerApi;

import com.chiragbohet.ecommerce.Dtos.UserProfileUpdateDto;
import com.chiragbohet.ecommerce.Utilities.GlobalVariables;

import javax.persistence.Column;
import javax.validation.constraints.Pattern;

public class SellerProfileUpdateDto extends UserProfileUpdateDto {

    @Pattern(regexp = GlobalVariables.REGEX_GST_NUMBER, message = "Please enter a valid GST number!")
    private String Gst;

    @Column(name = "COMPANY_CONTACT")
    private String companyContact;

    @Column(name = "COMPANY_NAME")
    private String companyName;

}
