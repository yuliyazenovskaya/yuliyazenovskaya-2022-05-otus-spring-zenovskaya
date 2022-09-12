package ru.otus.spring.service;

import ru.otus.spring.domain.Question;

import java.io.PrintStream;

public interface QuestionPrinterService {
    void printQuestion(Question question);
}
