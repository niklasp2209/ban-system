package com.lukas2o11.bansystem.web.exception.mappers;

import com.lukas2o11.bansystem.web.exception.ExceptionResponse;
import com.lukas2o11.bansystem.web.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResourceNotFoundExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleException(ResourceNotFoundException e) {
        return new ResponseEntity<>(
                new ExceptionResponse(404, e.getMessage()),
                HttpStatusCode.valueOf(404)
        );
    }
}
