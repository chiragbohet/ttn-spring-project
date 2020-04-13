package com.springbootcamp.springsecurity.Entities;

import javax.persistence.Embeddable;

@Embeddable
public class Addresscopy
{
    private String city;
    private String state;
    private String country;
    private String addressline;
    private Integer zipcode;
    private String label;


    Addresscopy(Address orderAddress)
    {
        this.city=orderAddress.getCity();
        this.state=orderAddress.getState();
        this.country=orderAddress.getCountry();
        this.addressline=orderAddress.getAddressLine();
        this.label=orderAddress.getLable();
        this.zipcode=orderAddress.getZipcode();

    }
}
