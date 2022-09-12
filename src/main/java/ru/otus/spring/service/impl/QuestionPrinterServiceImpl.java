package ru.otus.spring.service.impl;

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
        if (question.getOptions() != null) {
            for (int counter = 0; counter < question.getOptions().size(); counter++) {
                ioService.printString("    " + (counter + 1) + ". " + question.getOptions().get(counter));
            }
        }
    }
}
