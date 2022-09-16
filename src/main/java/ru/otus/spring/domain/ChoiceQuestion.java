package ru.otus.spring.domain;

import java.util.List;

public class ChoiceQuestion extends Question{
    private List<Option> options;

    public ChoiceQuestion(long id, String type, String text) {
        super(id, type, text);
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }
}
