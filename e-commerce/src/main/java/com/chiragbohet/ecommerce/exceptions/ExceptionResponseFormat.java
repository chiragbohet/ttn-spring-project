package com.chiragbohet.ecommerce.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class ExceptionResponseFormat {
    private Date timestamp;
    private String message;
    private String details;
}
