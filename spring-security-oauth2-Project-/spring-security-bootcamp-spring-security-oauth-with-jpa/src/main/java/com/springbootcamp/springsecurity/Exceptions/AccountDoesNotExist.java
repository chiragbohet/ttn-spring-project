package com.springbootcamp.springsecurity.Exceptions;

public class AccountDoesNotExist extends Throwable {
    public String AccountDoesNotExist() {
        return "Invalid account Credentials";
    }
}
