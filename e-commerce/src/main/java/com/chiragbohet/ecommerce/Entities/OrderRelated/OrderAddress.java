package com.chiragbohet.ecommerce.Entities.OrderRelated;

import javax.persistence.*;

@Embeddable
public class OrderAddress {

        @Column(name = "CUSTOMER_ADDRESS_CITY")
        String city;

        @Column(name = "CUSTOMER_ADDRESS_STATE")
        String state;

        @Column(name = "CUSTOMER_ADDRESS_COUNTRY")
        String country;

        @Column(name = "CUSTOMER_ADDRESS_ADDRESS_LINE")
        String addressLine;

        @Column(name = "CUSTOMER_ADDRESS_ZIP_CODE")
        String zipCode;

        // ex - office/home
        @Column(name = "CUSTOMER_ADDRESS_LABEL")
        String label;   // TODO : Doubt - should this be a enum instead?


}
