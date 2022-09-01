package service;

import domain.Question;

import java.io.IOException;
import java.util.List;

public interface QuestionService {
    List<Question> getQuestions() throws IOException;
}
