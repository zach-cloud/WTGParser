package com.github.zachcloud.exception;

public class InternalException extends RuntimeException {

    public InternalException() {
        super();
    }

    public InternalException(String message) {
        super(message);
    }

    public InternalException(Exception root) {
        super(root);
    }
}
