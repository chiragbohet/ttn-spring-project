package com.chiragbohet.ecommerce.dtos.SellerApi;

import com.chiragbohet.ecommerce.dtos.AddressViewDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SellerDetailsDto {

    Long id;
    String firstName;
    String lastName;
    String email;
    boolean isActive;
    private String Gst;
    private String companyContact;
    private String companyName;

    AddressViewDto address;

}
