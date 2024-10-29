package com.carrot.item.global.exception;

import com.carrot.item.global.ApplicationResponse;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Log
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ApplicationResponse<Void>> handleApplicationException(ApplicationException e) {
        HttpStatus status = e.getHttpStatus();
        ApplicationResponse<Void> response = ApplicationResponse.fail(e.getMessage());
        return ResponseEntity.status(status).body(response);
    }
}
