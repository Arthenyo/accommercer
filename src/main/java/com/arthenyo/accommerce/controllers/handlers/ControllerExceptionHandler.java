package com.arthenyo.accommerce.controllers.handlers;

import com.arthenyo.accommerce.DTO.CustomError;
import com.arthenyo.accommerce.services.excptions.ResouceNotFoundExcption;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {


    @ExceptionHandler(ResouceNotFoundExcption.class)
    public ResponseEntity<CustomError> resouceNotFound(ResouceNotFoundExcption e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        CustomError err = new CustomError(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }
}
