package com.unifiedresto.platform.exception;
//CPF duplicado (409)do
public class CpfAlreadyExistsException extends RuntimeException{
    public CpfAlreadyExistsException() {
        super("CPF already exists");
    }
}
