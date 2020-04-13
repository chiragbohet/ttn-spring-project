package com.springbootcamp.springsecurity.DTOs;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class SellerDto extends com.springbootcamp.springsecurity.dto.UserDto {
        @NotEmpty
        private int gst;
        @NotEmpty
        private String companyName;
        @NotEmpty
        private String companyContact;

    @Override
    public String toString() {
        return "SellerDto{" +
                "gst=" + gst +
                ", companyName='" + companyName + '\'' +
                ", companyContact='" + companyContact + '\'' +
                '}';
    }
}
