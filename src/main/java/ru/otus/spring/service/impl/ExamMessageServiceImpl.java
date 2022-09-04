package ru.otus.spring.service.impl;

import ru.otus.spring.common.Constants;
import ru.otus.spring.domain.Question;
import org.springframework.stereotype.Service;
import ru.otus.spring.service.IOService;
import ru.otus.spring.service.ExamMessageService;

@Service
public class ExamMessageServiceImpl implements ExamMessageService {
    private final IOService ioService;

    public ExamMessageServiceImpl(IOService ioService) {
        this.ioService = ioService;
    }

    @Override
    public void printGreetings() {
        ioService.printString("Your exam attempt is starting now! Write 'start' to begin! Good luck.");
    }

    @Override
    public void printResult(String result, int rightAnswers) {
        switch (result) {
            case Constants.SUCCESS_RESULT:
                ioService.printString("Congratulations!");
                ioService.printString("You scored " + rightAnswers + " out of 5 and successfully passed the exam!");
                break;
            case Constants.FAILED_RESULT:
                ioService.printString("Sorry, it was the last attempt.");
                ioService.printString("You scored " + rightAnswers + " out of 5 and failed the exam!");
                break;
            case Constants.ONE_MORE_ATTEMPT_RESULT:
                ioService.printString("Sorry, your result is below the passing score!");
                ioService.printString("You scored " + rightAnswers + " out of 5, but you have one more attempt to pass exam!");
                break;
        }
    }

    @Override
    public void printQuestion(Question question) {
        ioService.printString(question.id + ". " + question.text);
        if (question.options != null) {
            for (int counter = 0; counter < question.options.size(); counter++) {
                ioService.printString("    " + (counter + 1) + ". " + question.options.get(counter));
            }
        }
    }

    @Override
    public void printFormatError(String questionType) {
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
