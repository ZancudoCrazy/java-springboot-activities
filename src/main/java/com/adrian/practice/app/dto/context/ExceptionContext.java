package com.adrian.practice.app.dto.context;

/**
 * Class to hold exception context information using ThreadLocal.
 * This allows storing and retrieving exception details specific to the current thread.
 * It provides methods to set, get, and clear the exception context.
 * This is useful for passing exception information across different layers of the application
 * without using method parameters.
 * @author Adrian SA
 * @version 1.0
 * @since 2025-08
 */
public class ExceptionContext {
    private static final ThreadLocal<Exception> exceptionMessage = new ThreadLocal<>();

    /**
     * Sets the current exception in the ThreadLocal context.
     * @param Exception ex
     */
    public static void setCurrentException(Exception ex) {
        exceptionMessage.set(ex);
    }

    /**
     * Retrieves the current exception from the ThreadLocal context.
     * @return Exception
     */
    public static Exception getCurrentException() {
        return exceptionMessage.get();
    }

    /**
     * Clears the current exception from the ThreadLocal context.
     */
    public static void clear() {
        exceptionMessage.remove();
    }
}
