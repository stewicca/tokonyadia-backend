package com.enigma.tokonyadia_api.controller;

import com.enigma.tokonyadia_api.util.LogUtil;
import com.enigma.tokonyadia_api.util.ResUtil;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;

@RestControllerAdvice
public class ErrorController {
    @ExceptionHandler({Exception.class})
    public ResponseEntity<?> handleException(Exception e) {
        LogUtil.error(e.getMessage());
        LogUtil.error(Arrays.toString(e.getStackTrace()));
        return ResUtil.buildRes(
                HttpStatus.INTERNAL_SERVER_ERROR,
                e.getMessage(),
                null
        );
    }

    @ExceptionHandler({ResponseStatusException.class})
    public ResponseEntity<?> handlingResponseStatusException(ResponseStatusException e) {
        LogUtil.error(e.getReason());
        return ResUtil.buildRes(
                HttpStatus.valueOf(e.getStatusCode().value()),
                e.getReason(),
                null
        );
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<?> handlingConstraintViolationException(ConstraintViolationException e) {
        LogUtil.error(e.getMessage());
        return ResUtil.buildRes(
                HttpStatus.BAD_REQUEST,
                e.getMessage(),
                null
        );
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<?> handlingDataIntegrityViolationException(DataIntegrityViolationException e) {
        String message = "";
        HttpStatus status = HttpStatus.CONFLICT;

        if (e.getCause() != null) {
            String causeMessage = e.getCause().getMessage();
            if (causeMessage.contains("duplicate key value")) {
                message = "Data already exist.";
            } else if (causeMessage.contains("null value in column")) {
                message = "Data cannot be null.";
                status = HttpStatus.BAD_REQUEST;
            } else if (causeMessage.contains("violates check constraint")) {
                message = "Data must be appropriate.";
                status = HttpStatus.BAD_REQUEST;
            } else if (causeMessage.contains("violates foreign key constraint")) {
                message = "Data cannot be deleted because it is used by other data";
                status = HttpStatus.BAD_REQUEST;
            } else {
                message = "Unexpected error occurred";
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
        }

        LogUtil.error(message);
        return ResUtil.buildRes(
                status,
                message,
                null
        );
    }
}