package com.assignment.spring.web.client.exception;

import org.springframework.http.HttpStatus;

public class ServerErrorException extends BaseException {
    public ServerErrorException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }
}
