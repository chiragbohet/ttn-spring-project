package com.chiragbohet.ecommerce.Entities.UserRelated;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "CUSTOMER")
public class Customer extends User {

    @Column(name = "CONTACT")
    private String contact; //TODO : is this for Contact No? or something else?

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID")
    private Set<Address> addressSet;

    /***
     * Sets Address(s) given as input to the Address of the Customer
     * @param addresses
     */
    public void setAddress(Address... addresses)
    {
        if(addresses != null)
        {
            if(addressSet == null)
                addressSet = new HashSet<Address>();

            for(Address address : addresses)
            {
                address.setUser(this);
                addressSet.add(address);
            }

        }
    }

}