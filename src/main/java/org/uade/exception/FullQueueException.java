package org.uade.exception;

public class FullQueueException extends RuntimeException {

    public FullQueueException(String message) {
        super(message);
    }
}