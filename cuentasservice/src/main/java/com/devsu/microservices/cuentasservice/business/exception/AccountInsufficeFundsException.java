package com.devsu.microservices.cuentasservice.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseBody
@ResponseStatus(HttpStatus.OK)
public class AccountInsufficeFundsException extends RuntimeException{
    public AccountInsufficeFundsException(String message){
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
