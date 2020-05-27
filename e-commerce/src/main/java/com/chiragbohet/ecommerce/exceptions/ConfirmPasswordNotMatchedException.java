package com.chiragbohet.ecommerce.exceptions;

public class ConfirmPasswordNotMatchedException extends RuntimeException {
    public ConfirmPasswordNotMatchedException(String message) {
        super(message);
    }
}
