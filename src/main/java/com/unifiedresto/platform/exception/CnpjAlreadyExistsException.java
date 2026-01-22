package com.unifiedresto.platform.exception;
public class CnpjAlreadyExistsException extends RuntimeException{
    public CnpjAlreadyExistsException (){
     super("Cnpj alreday exists");
    }
}
