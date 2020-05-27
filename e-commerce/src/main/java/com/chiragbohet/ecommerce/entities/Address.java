package com.chiragbohet.ecommerce.entities;

import com.chiragbohet.ecommerce.utilities.Auditable;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Entity
@Table(name = "ADDRESS")
public class Address extends Auditable {

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

    @Column(name = "IS_DELETED")
    Boolean isDeleted;

    // ex - office/home
    @Column(name = "LABEL")
    String label;   // TODO : Doubt - should this be a enum instead?

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    User user;

}
