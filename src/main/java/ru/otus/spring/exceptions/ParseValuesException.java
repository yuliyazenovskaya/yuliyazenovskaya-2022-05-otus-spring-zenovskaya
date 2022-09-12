package ru.otus.spring.exceptions;

public class ParseValuesException extends RuntimeException {
    public ParseValuesException(String message, Exception e) {
        super(message, e.getCause());
    }
}
