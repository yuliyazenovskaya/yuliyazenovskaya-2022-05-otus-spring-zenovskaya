package domain;

import com.sun.istack.internal.Nullable;

import java.util.List;

public class Question {
    public long id;
    public String type;
    public String text;

    @Nullable
    public List<Option> options;

    public Question(long id, String type, String text) {
        this.id = id;
        this.type = type;
        this.text = text;
    }

    public Question(long id, String type, String text, @Nullable List<Option> options) {
        this.id = id;
        this.type = type;
        this.text = text;
        this.options = options;
    }
}
