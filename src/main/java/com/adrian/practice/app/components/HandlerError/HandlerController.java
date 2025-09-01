package com.adrian.practice.app.components.HandlerError;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.adrian.practice.app.dto.context.ExceptionContext;

import lombok.Getter;

/**
 * Class to handle Exceptions globally in the application.
 * It uses @RestControllerAdvice to intercept exceptions thrown by controllers
 * and return appropriate HTTP responses.
 * It handles custom HttpException, NoHandlerFoundException, and common runtime exceptions.
 * It constructs a standardized error response with error details.
 * The ErrorResponse class is a static inner class that represents the structure of the error response.
 * It includes fields for error message, detailed message, and HTTP status code.
 * The ErrorResponse class has a static factory method to create instances based on HttpStatus and Exception
 * details.
 * @author Adrian SA
 * @version 1.0
 * @since 2025-08
 */
@RestControllerAdvice
public class HandlerController {
    /**
     * Handles custom HttpException and constructs a ResponseEntity of ErrorResponse.
     * @param HttpException 
     * @return ResponseEntity<ErrorResponse> 
     */
    @ExceptionHandler(HttpException.class)
    public ResponseEntity<?> httpExceptionhandle(HttpException ex) {
        HttpStatus status = ex.getHttpError();
        loadExceptionContext(ex);
        return ResponseEntity.status(status).body(ErrorResponse.create(status, ex));
    }

    /**
     * Handles NoHandlerFoundException and constructs a ResponseEntity of ErrorResponse.
     * @param NoHandlerFoundException
     * @return ResponseEntity<ErrorResponse>
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<?> noHandlerExceptionFound(NoHandlerFoundException ex) {
        ErrorResponse response = ErrorResponse.create(HttpStatus.NOT_FOUND, ex);
        loadExceptionContext(ex);
        return ResponseEntity.status(ex.getStatusCode()).body(response);
    }

    /**
     * Handles common runtime exceptEions like NullPointerException and IllegalArgumentException.
     * Constructs a ResponseEntity of ErrorResponse with HTTP status 500 (Internal Server Error).
     * @param Exception ex
     * @return ResponseEntity<ErrorResponse>
     */
    @ExceptionHandler({NullPointerException.class, IllegalArgumentException.class})
    public ResponseEntity<?> runtimeExceptionHandle(Exception ex) {
        ErrorResponse response = ErrorResponse.create(HttpStatus.INTERNAL_SERVER_ERROR, ex);
        loadExceptionContext(ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    /**
     * Loads the exception into the ExceptionContext for thread-local storage.
     * @param Exception ex
     */
    private void loadExceptionContext(Exception ex) {
        System.err.println("Loading exception into context..."+ex.getMessage());
        ExceptionContext.setCurrentException(ex);
    }

    /**
     * Static inner class representing the structure of the error response.
     * It includes fields for error message, detailed message, and HTTP status code.
     * @author Adrian SA
     * @version 1.0
     * @since 2025-08
     */
    @Getter
    private static class ErrorResponse{
        private final String error;
        private final String message;
        private final int code;

        /**
         * Private constructor to initialize ErrorResponse fields.
         * @param String error
         * @param String message
         * @param int code
         */
        private ErrorResponse(String error, String message, int code) {
            this.error = error;
            this.message = message;
            this.code = code;
        }

        /**
         * Static factory method to create an ErrorResponse instance based on HttpStatus and Exception details.
         * @param HttpStatus status
         * @param Exception ex
         * @return ErrorResponse
         */
        public static ErrorResponse create(HttpStatus status, Exception ex) {
            return new ErrorResponse(status.getReasonPhrase(), ex.getMessage(), status.value());
        }

    }
}
