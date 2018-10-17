package com.epam.esm.exception;


/**
 * This custom exception for
 * service module
 */
public class ServiceException extends RuntimeException {
    public ServiceException() {
    }

    public ServiceException(final String message) {
        super(message);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }
}
