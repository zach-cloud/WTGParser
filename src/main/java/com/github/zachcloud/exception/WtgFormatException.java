package com.github.zachcloud.exception;

public class WtgFormatException extends RuntimeException {

    public WtgFormatException() {
        super();
    }

    public WtgFormatException(String message) {
        super(message);
    }

    public WtgFormatException(Exception root) {
        super(root);
    }
}
