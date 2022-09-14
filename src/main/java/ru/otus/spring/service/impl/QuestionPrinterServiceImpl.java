package ru.otus.spring.service.impl;

import ru.otus.spring.domain.ChoiceQuestion;
import ru.otus.spring.domain.Question;
import org.springframework.stereotype.Service;
import ru.otus.spring.service.IOService;
import ru.otus.spring.service.QuestionPrinterService;

@Service
public class QuestionPrinterServiceImpl implements QuestionPrinterService {
    private final IOService ioService;

    public QuestionPrinterServiceImpl(IOService ioService) {
        this.ioService = ioService;
    }

    @Override
    public void printQuestion(Question question) {
        ioService.printString(question.getId() + ". " + question.getText());
        if (question.getClass().getSimpleName().equals("ChoiceQuestion")) {
            ((ChoiceQuestion) question).getOptions().forEach(option -> {
                ioService.printString("    " + option.getNumber() + ". "
                        + option.getText());
            });
        }
    }
}
