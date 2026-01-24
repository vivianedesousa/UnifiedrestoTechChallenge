package com.unifiedresto.platform.exception;
//Login inválido (401) dados invalido
public class InvalidLoginException extends RuntimeException{
    public InvalidLoginException(String invalidLoginOrPassword) {
        super("Invalid login or password");
    }
}
