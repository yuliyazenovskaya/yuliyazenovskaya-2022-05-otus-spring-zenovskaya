package ru.otus.spring.service;

import ru.otus.spring.domain.Question;

public interface AnswerService {
    boolean isOptionRight(Question question, String answer);

    boolean isAnswerFormatValid(String questionType, String answer);

    void printAnswerFormatError(String questionType);
}
