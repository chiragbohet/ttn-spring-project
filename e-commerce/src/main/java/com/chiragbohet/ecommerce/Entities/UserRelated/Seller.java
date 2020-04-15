package com.chiragbohet.ecommerce.Entities.UserRelated;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "SELLER")
public class Seller extends User {

    //TODO :  add validation using REGEX
    @Column(name = "GST")
    private String Gst;

    @Column(name = "COMPANY_CONTACT")
    private String companyContact;

    @Column(name = "COMPANY_NAME")
    private String companyName;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    private Address address;



}
