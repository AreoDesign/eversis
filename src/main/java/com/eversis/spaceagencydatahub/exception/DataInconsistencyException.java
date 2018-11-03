package com.eversis.spaceagencydatahub.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DataInconsistencyException extends RuntimeException {
    public DataInconsistencyException(String message) {
        super(message);
    }
}
