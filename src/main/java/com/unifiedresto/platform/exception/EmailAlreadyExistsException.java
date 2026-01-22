package com.unifiedresto.platform.exception;
///Email duplicado (409)
public class EmailAlreadyExistsException extends RuntimeException{
  public EmailAlreadyExistsException() {
   super("Email already exists");
   }
}
