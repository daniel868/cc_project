package org.business.exceptions;

public class NotEnoughSpotsException extends RuntimeException {
    public NotEnoughSpotsException(String message) {
        super(message);
    }

    public NotEnoughSpotsException(String message, Throwable cause) {
        super(message, cause);
    }
}
