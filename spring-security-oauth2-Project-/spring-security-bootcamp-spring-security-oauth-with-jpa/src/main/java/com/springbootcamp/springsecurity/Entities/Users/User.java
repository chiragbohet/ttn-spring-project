package com.springbootcamp.springsecurity.Entities.Users;

import com.springbootcamp.springsecurity.Entities.Address;
import com.springbootcamp.springsecurity.Entities.Role;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.Set;



@Entity
@Inheritance(strategy = InheritanceType.JOINED)
//@Table(name = "USER")
@Getter
@Setter
public  abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // @NotNull(message = "Email must be unique")
    // @Column(unique = true)
    private String email;

    //  @NotNull(message = "First Name cannot be Null")
    private String firstName;

    //  @NotNull(message = "Last Name cannot be Null")
    private String lastName;

    //  @NotNull
    //  @Pattern(regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,64})", message = "Password must be 8 characters long")
    private String password;

    //  @NotNull
    private boolean isDeleted;

    //@NotNull
    private boolean isActive;


    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinTable(name = "USERS_ROLE", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    List<Role> roleList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Address> addressList;



}

    //    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
//    private List<Seller> sellers;
//
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
//    private List<Customer> customers;

//    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
//    private Cart cart;

