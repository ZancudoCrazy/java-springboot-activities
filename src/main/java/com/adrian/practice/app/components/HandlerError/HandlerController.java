package com.adrian.practice.app.components.HandlerError;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import lombok.Getter;

@RestControllerAdvice
public class HandlerController {

     @ExceptionHandler(HttpException.class)
    public ResponseEntity<? > httpExceptionhandle(HttpException ex) {
        HttpStatus status = ex.getHttpError();
        return ResponseEntity.status(status).body(ErrorResponse.create(status, ex.getMessage()));
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<?> noHandlerExceptionFound(NoHandlerFoundException ex) {
        ErrorResponse response = ErrorResponse.create(HttpStatus.NOT_FOUND, ex.getMessage());
        return ResponseEntity.status(ex.getStatusCode()).body(response);
    }

    @ExceptionHandler({NullPointerException.class, IllegalArgumentException.class})
    public ResponseEntity<?> runtimeExceptionHandle(Exception ex) {
        ErrorResponse response = ErrorResponse.create(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @Getter
    private static class ErrorResponse{
        private final String error;
        private final String message;
        private final int code;

        private ErrorResponse(String error, String message, int code) {
            this.error = error;
            this.message = message;
            this.code = code;
        }

        public static ErrorResponse create(HttpStatus status, String message) {
            return new ErrorResponse(status.getReasonPhrase(), message, status.value());
        }

    }
}
