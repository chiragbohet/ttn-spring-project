package com.chiragbohet.ecommerce.dtos.AdminApi;

import com.chiragbohet.ecommerce.dtos.AddressViewDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SellerAdminApiDto extends UserAdminApiDto {

    private String companyContact;
    private String companyName;
    private AddressViewDto address;

}
