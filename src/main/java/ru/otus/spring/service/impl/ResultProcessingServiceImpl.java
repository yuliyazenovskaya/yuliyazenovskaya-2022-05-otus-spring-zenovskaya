package ru.otus.spring.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.spring.common.Constants;
import ru.otus.spring.service.IOService;
import ru.otus.spring.service.ResultProcessingService;

@Service
public class ResultProcessingServiceImpl implements ResultProcessingService {
    private final int passingScore;
    private final int passingAttempts;

    private final IOService ioService;

    public ResultProcessingServiceImpl(
            @Value("${examine.passing.score}") int passingScore,
            @Value("${examine.passing.attempts}") int passingAttempts,
            IOService ioService
    ) {
        this.passingScore = passingScore;
        this.passingAttempts = passingAttempts;
        this.ioService = ioService;
    }

    @Override
    public String processResult(int resultScore, int attempt) {
        if (resultScore >= passingScore) {
            printResult(Constants.SUCCESS_RESULT, resultScore);
            return Constants.SUCCESS_RESULT;
        }

        if (attempt == passingAttempts) {
            printResult(Constants.FAILED_RESULT, resultScore);
            return Constants.FAILED_RESULT;
        } else {
            printResult(Constants.ONE_MORE_ATTEMPT_RESULT, resultScore);
            return Constants.ONE_MORE_ATTEMPT_RESULT;
        }
    }

    private void printResult(String result, int rightAnswers) {
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
}
