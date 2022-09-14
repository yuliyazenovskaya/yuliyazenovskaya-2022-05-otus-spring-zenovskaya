package ru.otus.spring.service;

import ru.otus.spring.domain.ChoiceQuestion;

public interface OptionCheckService {
    boolean isAnswerRightForSingleSelectQuestion(ChoiceQuestion question, String answer);

    boolean isAnswerRightForMultiSelectQuestion(ChoiceQuestion question, String answer);
}
