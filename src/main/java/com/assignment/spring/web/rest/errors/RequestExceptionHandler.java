package com.assignment.spring.web.rest.errors;

import com.assignment.spring.web.client.exception.ClientErrorException;
import com.assignment.spring.web.client.exception.ServerErrorException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RequestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ServerErrorException.class)
    protected ResponseEntity<Object> handleServerErrorException(ServerErrorException exception, WebRequest request) {
        return handleExceptionInternal(exception, exception.getMessage(), new HttpHeaders(), exception.getHttpStatus(), request);
    }

    @ExceptionHandler(ClientErrorException.class)
    protected ResponseEntity<Object> handleServerErrorException(ClientErrorException exception, WebRequest request) {
        return handleExceptionInternal(exception, exception.getMessage(), new HttpHeaders(), exception.getHttpStatus(), request);
    }
}
