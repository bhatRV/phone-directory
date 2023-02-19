package com.rv.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NoRecordsFoundException extends RuntimeException {

    public NoRecordsFoundException(String message) {
        super(message);
    }

}
