package com.springbootcamp.springsecurity.Entities;

import com.springbootcamp.springsecurity.Entities.Users.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;
@Getter
@Setter
@Entity
//@Table(name = "ROLE")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
  //  @Column(name = "ROLE_ID")
    private int id;
    private String authority;

    @ManyToMany(mappedBy = "roleList")
    private Set<User> userSet;



}
