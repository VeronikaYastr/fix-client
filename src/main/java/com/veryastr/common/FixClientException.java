package com.veryastr.common;

public class FixClientException extends RuntimeException {

    public FixClientException(String message) {
        super(message);
    }

    public FixClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
