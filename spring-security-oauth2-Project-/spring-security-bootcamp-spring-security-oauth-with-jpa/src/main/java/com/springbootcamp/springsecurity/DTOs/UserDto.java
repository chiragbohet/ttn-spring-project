package com.springbootcamp.springsecurity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
@Getter
@Setter

public class UserDto {
    @NotEmpty
    private Integer id;
    @NotEmpty
    private String email;
    @NotEmpty
    @JsonIgnore  //Using Json ignore here will create internal server error at registration time
    private String password;
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;

    public UserDto(){

    }

    public UserDto(@NotEmpty Integer id, @NotEmpty String email, @NotEmpty String firstName, @NotEmpty String lastName) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }


    @Override
    public String toString() {
        return "UserDto{" +
                "email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

}
