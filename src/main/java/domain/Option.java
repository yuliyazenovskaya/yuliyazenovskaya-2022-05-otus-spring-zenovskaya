package domain;

public class Option {
    public long id;
    public long questionId;
    public String text;

    public Option(long id, long questionId, String text) {
        this.id = id;
        this.questionId = questionId;
        this.text = text;
    }
}
