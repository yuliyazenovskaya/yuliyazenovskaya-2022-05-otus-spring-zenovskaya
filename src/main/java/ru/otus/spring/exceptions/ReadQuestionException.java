package ru.otus.spring.exceptions;

public class ReadQuestionException extends RuntimeException{
    public ReadQuestionException(String message, Exception e) {
        super(message, e.getCause());
    }
}
