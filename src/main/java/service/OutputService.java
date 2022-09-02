package service;

import domain.Question;

import java.io.PrintStream;
import java.util.List;

public interface OutputService {
    List<Question> printQuestions(PrintStream printStream);
}
