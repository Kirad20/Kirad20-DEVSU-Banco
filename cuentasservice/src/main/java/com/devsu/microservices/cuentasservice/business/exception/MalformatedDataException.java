package com.devsu.microservices.cuentasservice.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseBody
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class MalformatedDataException extends RuntimeException{
    public MalformatedDataException(String message) {
        super(message);
    }

    public String getMensaje() {
        return super.getMessage();
    }
}
