package ru.otus.spring.domain;

public class FreeFormatQuestion extends Question{
    private String rightAnswer;

    public FreeFormatQuestion(long id, String type, String text) {
        super(id, type, text);
    }

    public String getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(String rightAnswer) {
        this.rightAnswer = rightAnswer;
    }
}
