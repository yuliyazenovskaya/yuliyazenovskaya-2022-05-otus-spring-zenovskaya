package ru.otus.spring.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.spring.common.Constants;
import ru.otus.spring.domain.ChoiceQuestion;
import ru.otus.spring.domain.FreeFormatQuestion;
import ru.otus.spring.domain.Question;
import ru.otus.spring.service.AnswerService;
import ru.otus.spring.service.IOService;
import ru.otus.spring.service.OptionCheckService;

import java.util.Arrays;

@Service
public class AnswerServiceImpl implements AnswerService {
    private final IOService ioService;
    private final OptionCheckService optionCheckService;

    public AnswerServiceImpl(IOService ioService, OptionCheckService optionCheckService) {
        this.ioService = ioService;
        this.optionCheckService = optionCheckService;
    }

    @Override
    public boolean isAnswerRight(Question question, String answer) {
        switch (question.getType()) {
            case Constants.MULTI_SELECT_QUESTION_TYPE:
                return optionCheckService.isAnswerRightForMultiSelectQuestion((ChoiceQuestion) question, answer);
            case Constants.SINGLE_SELECT_QUESTION_TYPE:
                return optionCheckService.isAnswerRightForSingleSelectQuestion((ChoiceQuestion) question, answer);
            default:
                return ((FreeFormatQuestion) question).getRightAnswer().equals(answer);
        }

    }

    @Override
    public boolean isAnswerFormatValid(String questionType, String answer) {
        switch (questionType) {
            case Constants.SINGLE_SELECT_QUESTION_TYPE:
            case Constants.NUMBER_QUESTION_TYPE:
                try {
                    Integer.parseInt(answer);
                    return true;
                } catch (NumberFormatException e) {
                    return false;
                }
            case Constants.MULTI_SELECT_QUESTION_TYPE:
                try {
                    Arrays.stream(answer.split(";")).forEach(Integer::parseInt);
                    return true;
                } catch (NumberFormatException e) {
                    return false;
                }

        }
        return true;
    }

    @Override
    public void printAnswerFormatError(String questionType) {
        switch (questionType) {
            case Constants.SINGLE_SELECT_QUESTION_TYPE:
            case Constants.NUMBER_QUESTION_TYPE:
                ioService.printString("Wrong answer format! You should type a number");
                break;
            case Constants.MULTI_SELECT_QUESTION_TYPE:
                ioService.printString("Wrong answer format! You should type numbers split by ';'");
        }
    }
}
