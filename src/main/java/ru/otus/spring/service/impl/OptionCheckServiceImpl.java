package ru.otus.spring.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.spring.domain.ChoiceQuestion;
import ru.otus.spring.domain.Option;
import ru.otus.spring.exceptions.ParseValuesException;
import ru.otus.spring.service.OptionCheckService;

import java.util.Arrays;

@Service
public class OptionCheckServiceImpl implements OptionCheckService {
    @Override
    public boolean isAnswerRightForSingleSelectQuestion(ChoiceQuestion question, String answer) {
        try {
            return question.getOptions()
                    .stream().anyMatch(option -> option.getNumber() == Integer.parseInt(answer) && option.isRight());
        } catch (NumberFormatException e) {
            throw new ParseValuesException("Wrong answer format!", e);
        }
    }

    @Override
    public boolean isAnswerRightForMultiSelectQuestion(ChoiceQuestion question, String answer) {
        try{
            return Arrays.equals(
                    question.getOptions()
                            .stream().filter(Option::isRight).map(Option::getNumber).sorted().toArray(),
                    Arrays.stream(answer.split(";")).map(Integer::parseInt).sorted().toArray());
        } catch (NumberFormatException e) {
            throw new ParseValuesException("Wrong answer format!", e);
        }
    }
}
