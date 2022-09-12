package ru.otus.spring.domain;

import java.util.List;

public class Question {
    private long id;
    private String type;
    private String text;

    private List<String> options;
    private String rightAnswer;

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

    public List<String> getOptions() {
        return options;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public void setRightAnswer(String rightAnswer) {
        this.rightAnswer = rightAnswer;
    }
}
