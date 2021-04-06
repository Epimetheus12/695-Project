package com.worker.people.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class CustomException extends RuntimeException{

    public CustomException(String exception) {
        super(exception);
    }
}
