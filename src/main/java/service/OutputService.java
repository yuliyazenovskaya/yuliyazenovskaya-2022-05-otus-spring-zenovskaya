package service;

import domain.Question;

import java.io.IOException;
import java.util.List;

public interface OutputService {
    List<Question> printQuestions() throws IOException;
}
