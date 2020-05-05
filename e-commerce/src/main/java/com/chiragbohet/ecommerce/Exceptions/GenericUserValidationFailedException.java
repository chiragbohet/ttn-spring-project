package com.chiragbohet.ecommerce.Exceptions;

public class GenericUserValidationFailedException extends RuntimeException {
    public GenericUserValidationFailedException(String message) {
        super(message);
    }
}
