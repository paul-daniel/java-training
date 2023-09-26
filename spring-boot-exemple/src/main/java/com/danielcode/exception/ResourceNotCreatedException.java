package com.danielcode.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ResourceNotCreatedException extends RuntimeException{
    public ResourceNotCreatedException(String message){
        super(message);
    }
}
