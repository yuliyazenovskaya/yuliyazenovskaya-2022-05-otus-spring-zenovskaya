package ru.otus.spring.service;

import ru.otus.spring.domain.Question;

import java.io.PrintStream;

public interface PrintService {
    void printGreetings(PrintStream printStream);

    void printResult(String result, int rightAnswers, PrintStream printStream);

    void printQuestion(PrintStream printStream, Question question);
}
