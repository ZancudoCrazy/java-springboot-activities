package com.adrian.practice.app.components.HandlerError;

import java.util.Optional;

import org.springframework.http.HttpStatus;

import lombok.Getter;

/**
 * Represents a custom HTTP exception with various static methods to create specific HTTP status exceptions.
 * *@author AdrianSA
 * @version 1.0
 * @since 2025-08
 */

public class HttpException extends RuntimeException {
    @Getter
    private final HttpStatus httpError;

    /**
     * Private constructor to initialize the HttpException with a message and status.
     * @param message
     * @param status
     */
    private HttpException(String message, HttpStatus status) {
        super(message);
        this.httpError = status;
    }

    /**
     * Creates a Bad Request (400) HttpException.
     * @param message
     * @return HttpException
     */
    public static HttpException badRequest(String message) {
        return new BadRequestException(Optional.ofNullable(message));
    }
    
    /**
     * Creates an Unauthorized (401) HttpException.
     * @param message
     * @return HttpException
     */
    public static HttpException unauthorized(String message) {
        return new UnauthorizedException(Optional.ofNullable(message));
    }

    
    /**
     * Creates a Forbidden (403) HttpException.
     * @param message
     * @return HttpException
     */
    public static HttpException forbidden(String message) {
        return new ForbiddenException(Optional.ofNullable(message));
    }
    
    /**
     * Creates a Not Found (404) HttpException.
     * @param message
     * @return HttpException
     */
    public static HttpException notFound(String message) {
        return new NotFoundException(Optional.ofNullable(message));
    }

    /**
     * Creates an Internal Server Error (500) HttpException.
     * @param message
     * @return HttpException
     */
    public static HttpException internalServerError(String message) {
        return new InternalServerErrorException(Optional.ofNullable(message));
    }

    /**
     * Nested class that represent a Not Found Error (404) from HttpException.
     * @author AdrianSA
     * @version 1.0
     * @since 2025-08
     */
    static class NotFoundException extends HttpException {
        public NotFoundException(Optional<String> message) {
            super(message.orElse(""), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Nested class that represent a Bad Request Error (400) from HttpException.
     * @author AdrianSA
     * @version 1.0
     * @since 2025-08
     */
    static class BadRequestException extends HttpException {
        public BadRequestException(Optional<String> message) {
            super(message.orElse(""), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Nested class that represent an Internal Server Error (500) from HttpException.
     * @author AdrianSA
     * @version 1.0
     * @since 2025-08
     */
    static class InternalServerErrorException extends HttpException {
        public InternalServerErrorException(Optional<String> message) {
            super(message.orElse(""), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Nested class that represent an Unauthorized Error (401) from HttpException.
     * @author AdrianSA
     * @version 1.0
     * @since 2025-08
     */
    static class UnauthorizedException extends HttpException {
        public UnauthorizedException(Optional<String> message) {
            super(message.orElse(""), HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Nested class that represent a Forbidden Error (403) from HttpException.
     * @author AdrianSA
     * @version 1.0
     * @since 2025-08
     */
    static class ForbiddenException extends HttpException {
        public ForbiddenException(Optional<String> message) {
            super(message.orElse(""), HttpStatus.FORBIDDEN);
        }
    }
}
