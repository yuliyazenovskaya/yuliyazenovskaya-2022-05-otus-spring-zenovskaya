package ru.otus.spring.service.impl;

import ru.otus.spring.common.Constants;
import ru.otus.spring.domain.Question;
import org.springframework.stereotype.Service;
import ru.otus.spring.service.PrintService;

import java.io.PrintStream;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class PrintServiceImpl implements PrintService {

    @Override
    public void printGreetings(PrintStream printStream) {
        printStream.println("Your exam attempt is starting now! Write 'start' to begin! Good luck.");
    }

    @Override
    public void printResult(String result, int rightAnswers, PrintStream printStream) {
        switch (result) {
            case Constants.SUCCESS_RESULT:
                printStream.println("Congratulations!");
                printStream.println("You scored " + rightAnswers + " and successfully passed the exam!");
                break;
            case Constants.FAILED_RESULT:
                printStream.println("Sorry, it was the last attempt.");
                printStream.println("You scored " + rightAnswers + " and failed the exam!");
                break;
            case Constants.ONE_MORE_ATTEMPT_RESULT:
                printStream.println("Sorry, your result is below the passing score!");
                printStream.println("You scored " + rightAnswers + ", but you have one more attempt to pass exam!");
                break;
        }
    }

    @Override
    public void printQuestion(PrintStream printStream, Question question) {
        printStream.println(question.id + ". " + question.text);
        AtomicInteger counter = new AtomicInteger(1);
        if (question.options != null) {
            question.options.forEach(option ->
                    printStream.println("    " + counter.getAndIncrement() + ". " + option));
        }
    }
}
