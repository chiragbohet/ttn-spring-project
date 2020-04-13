package com.springbootcamp.springsecurity.Entities.Users;


import com.springbootcamp.springsecurity.Entities.Cart;
import com.springbootcamp.springsecurity.Entities.Order.OrderClass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Set;
@Getter
@Setter
@Entity
//@Table(name = "CUSTOMER")
@PrimaryKeyJoinColumn(name = "ID")
public class Customer extends User {
   // @NotNull
 //   @Pattern( regexp ="(\\+91|0)[0-9]{9}")
    private String contact;
    public Customer(){

    }
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(value= FetchMode.SUBSELECT)

    private Set<OrderClass> orderSet;


    @OneToOne(mappedBy = "customer")
    private Cart cart;


}
