package com.chiragbohet.ecommerce.Dtos;

import com.chiragbohet.ecommerce.Entities.UserRelated.Address;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SellerAdminApiDto extends UserAdminApiDto {

    private String companyContact;
    private String companyName;
    private Address address;

}
