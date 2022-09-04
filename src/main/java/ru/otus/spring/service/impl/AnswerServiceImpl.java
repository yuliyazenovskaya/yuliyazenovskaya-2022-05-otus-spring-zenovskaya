package ru.otus.spring.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.spring.common.Constants;
import ru.otus.spring.domain.Question;
import ru.otus.spring.exceptions.ParseLongValueFromSourceException;
import ru.otus.spring.service.AnswerService;

import java.util.Arrays;

@Service
public class AnswerServiceImpl implements AnswerService {
    @Override
    public boolean isAnswerRight(Question question, String answer) {
        if (question.type.equals(Constants.MULTI_SELECT_QUESTION_TYPE)) {
            try{
                return Arrays.equals(
                        Arrays.stream(question.answers.split(";")).map(Integer::parseInt).sorted().toArray(),
                        Arrays.stream(answer.split(";")).map(Integer::parseInt).sorted().toArray());
            } catch (NumberFormatException e) {
                throw new ParseLongValueFromSourceException("Wrong answer format!");
            }
        } else {
            return question.answers.equals(answer);
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

}
