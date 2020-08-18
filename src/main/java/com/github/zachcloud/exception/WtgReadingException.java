package com.github.zachcloud.exception;

public class WtgReadingException extends RuntimeException {

    public WtgReadingException() {
        super();
    }

    public WtgReadingException(String message) {
        super(message);
    }

    public WtgReadingException(Exception root) {
        super(root);
    }
}
