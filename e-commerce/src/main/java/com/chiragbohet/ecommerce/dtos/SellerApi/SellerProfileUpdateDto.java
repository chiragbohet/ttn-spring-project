package com.chiragbohet.ecommerce.dtos.SellerApi;

import com.chiragbohet.ecommerce.dtos.UserProfileUpdateDto;
import com.chiragbohet.ecommerce.utilities.GlobalVariables;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class SellerProfileUpdateDto extends UserProfileUpdateDto {

    @Pattern(regexp = GlobalVariables.REGEX_GST_NUMBER, message = "Please enter a valid GST number!")
    private String Gst;

    @Pattern(regexp = GlobalVariables.REGEX_MOBILE_NUMBER, message = "Please enter a valid mobile number!")
    @Column(name = "COMPANY_CONTACT")
    private String companyContact;

    @Column(name = "COMPANY_NAME")
    private String companyName;

}
