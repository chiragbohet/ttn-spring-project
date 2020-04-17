package com.chiragbohet.ecommerce.Entities.UserRelated;

import com.chiragbohet.ecommerce.Security.Role;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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

     //TODO : Uncomment this if not done

    public Seller(){

        List<Role> roles = new ArrayList<>();
        Role ROLE_SELLER = new Role("ROLE_SELLER");
        roles.add(ROLE_SELLER);

        this.setRoleList(roles);

        this.setActive(false);  // will be activated by Admin
        this.setDeleted(false);
        // spring security related fields
        this.setAccountNonExpired(true);
        this.setAccountNonLocked(true);
        this.setCredentialsNonExpired(true);
        this.setEnabled(true);
    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Address address;



}
