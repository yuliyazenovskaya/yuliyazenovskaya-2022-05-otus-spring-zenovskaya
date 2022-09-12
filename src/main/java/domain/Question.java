package domain;

import java.util.List;

public class Question {
    public long id;
    public String type;
    public String text;

    public List<String> options;

    public Question(long id, String type, String text) {
        this.id = id;
        this.type = type;
        this.text = text;
    }
}
