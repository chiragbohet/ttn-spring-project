package com.chiragbohet.ecommerce.exceptions;

public class GenericUserValidationFailedException extends RuntimeException {
    public GenericUserValidationFailedException(String message) {
        super(message);
    }
}
