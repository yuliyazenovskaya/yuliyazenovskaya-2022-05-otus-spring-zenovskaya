package ru.otus.spring.service;

import ru.otus.spring.domain.Question;

import java.io.PrintStream;

public interface ExamMessageService {
    void printGreetings();

    void printResult(String result, int rightAnswers);

    void printQuestion(Question question);

    void printFormatError(String questionType);
}
