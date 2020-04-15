package com.chiragbohet.ecommerce.Entities.UserRelated;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "ADDRESS")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    Long id;

    @Column(name = "CITY")
    String city;

    @Column(name = "STATE")
    String state;

    @Column(name = "COUNTRY")
    String country;

    @Column(name = "ADDRESS_LINE")
    String addressLine;

    @Column(name = "ZIP_CODE")
    String zipCode;

    // ex - office/home
    @Column(name = "LABEL")
    String label;   // TODO : Doubt - should this be a enum instead?

    @OneToOne
    User user;
}
