package com.assignment.spring.web.client.exception;

import org.springframework.http.HttpStatus;

public class ClientErrorException extends BaseException {
    public ClientErrorException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }
}
