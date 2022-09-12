package ru.otus.spring.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring.common.Constants;
import ru.otus.spring.domain.Question;
import ru.otus.spring.exceptions.ParseValuesException;
import ru.otus.spring.service.AnswerService;
import ru.otus.spring.service.IOService;

import java.util.Arrays;

@Service
public class AnswerServiceImpl implements AnswerService {
    private final IOService ioService;

    public AnswerServiceImpl(IOService ioService) {
        this.ioService = ioService;
    }

    @Override
    public boolean isOptionRight(Question question, String answer) {
        if (question.getType().equals(Constants.MULTI_SELECT_QUESTION_TYPE)) {
            try{
                return Arrays.equals(
                        Arrays.stream(question.getRightAnswer().split(";")).map(Integer::parseInt).sorted().toArray(),
                        Arrays.stream(answer.split(";")).map(Integer::parseInt).sorted().toArray());
            } catch (NumberFormatException e) {
                throw new ParseValuesException("Wrong answer format!", e);
            }
        } else {
            return question.getRightAnswer().equals(answer);
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
