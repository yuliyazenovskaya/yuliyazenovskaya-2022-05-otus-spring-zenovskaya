package ru.otus.spring.domain;

public abstract class Question {
    private final long id;
    private final String type;
    private final String text;

    public Question(long id, String type, String text) {
        this.id = id;
        this.type = type;
        this.text = text;
    }

    public long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getText() {
        return text;
    }
}
