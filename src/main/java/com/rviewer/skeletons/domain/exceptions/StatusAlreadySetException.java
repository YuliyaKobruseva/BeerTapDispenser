package com.rviewer.skeletons.domain.exceptions;

public class StatusAlreadySetException extends RuntimeException {
    public StatusAlreadySetException(String message) {
        super(message);
    }
}

