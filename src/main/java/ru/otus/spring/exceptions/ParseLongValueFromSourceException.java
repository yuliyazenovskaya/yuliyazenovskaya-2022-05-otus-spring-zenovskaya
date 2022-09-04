package ru.otus.spring.exceptions;

public class ParseLongValueFromSourceException extends RuntimeException {
    public ParseLongValueFromSourceException(String message) {
        super(message);
    }
}
