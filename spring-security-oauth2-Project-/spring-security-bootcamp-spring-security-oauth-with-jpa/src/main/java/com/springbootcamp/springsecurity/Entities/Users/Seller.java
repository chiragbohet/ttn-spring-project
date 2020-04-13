package com.springbootcamp.springsecurity.Entities.Users;
//table per class is implemented

import com.springbootcamp.springsecurity.Entities.Product.Product;
import com.springbootcamp.springsecurity.Entities.Users.User;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;
@Getter
@Setter
@Entity
//@Table(name = "SELLER")
@PrimaryKeyJoinColumn(name = "UserID")
public class Seller extends User {

  //  @NotNull(message = "GST needs to be provided")
    private int gst;
   // @NotNull(message = "Company-name cannot be blank")
    private String companyName;
 ///   @NotNull(message = "Company-contact cannot be blank")
    private String companyContact;

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(value= FetchMode.SUBSELECT)
    private Set<Product> productSet;

}
