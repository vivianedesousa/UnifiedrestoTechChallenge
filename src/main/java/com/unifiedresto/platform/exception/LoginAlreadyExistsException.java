package com.unifiedresto.platform.exception;
//Login duplicado (409)
public class LoginAlreadyExistsException extends RuntimeException{
    public LoginAlreadyExistsException() {
        super("Login already exists");
    }
}
