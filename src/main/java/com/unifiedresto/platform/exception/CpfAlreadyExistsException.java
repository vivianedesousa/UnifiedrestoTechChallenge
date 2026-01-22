package com.unifiedresto.platform.exception;
//CPF duplicado (409)
public class CpfAlreadyExistsException extends RuntimeException{
    public CpfAlreadyExistsException() {
        super("CPF already exists");
    }
}
