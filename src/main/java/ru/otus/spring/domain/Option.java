package ru.otus.spring.domain;

public class Option {
    private final int number;
    private final String text;
    private boolean right;

    public Option(int number, String text) {
        this.number = number;
        this.text = text;
    }

    public int getNumber() {
        return number;
    }

    public String getText() {
        return text;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }
}
