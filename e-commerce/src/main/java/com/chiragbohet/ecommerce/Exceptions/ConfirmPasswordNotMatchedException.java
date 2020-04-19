package com.chiragbohet.ecommerce.Exceptions;

public class ConfirmPasswordNotMatchedException extends RuntimeException {
    public ConfirmPasswordNotMatchedException(String message) {
        super(message);
    }
}
