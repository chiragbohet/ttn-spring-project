package com.chiragbohet.ecommerce.entities;

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
    private String contact;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Address> addressSet;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private Set<Order> orderSet;


    // TODO : Uncomment this
    public Customer()
    {
        this.setActive(false);  // will be activated via email
        this.setDeleted(false);
        // spring security related fields
        this.setAccountNonExpired(true);
        this.setAccountNonLocked(true);
        this.setCredentialsNonExpired(true);
        this.setEnabled(true);
    }

    /***
     * Sets Address(s) given as input to the Address of the Customer
     * @param addresses
     */
    public void addAddress(Address... addresses)
    {
        if(addresses != null)
        {
            if(addressSet == null)
                addressSet = new HashSet<Address>();

            for(Address address : addresses)
            {
                if(!addressSet.contains(address))
                {
                    address.setUser(this);
                    addressSet.add(address);
                }
            }

        }
    }

    public void deleteAddress(Address... addresses)
    {
        if(addresses != null)
        {
            if(addressSet == null)
                return;

            for(Address address : addresses)
            {
                if(addressSet.contains(address))
                {
                    addressSet.remove(address);
                    address.setUser(null);
                }
            }
        }
    }

}