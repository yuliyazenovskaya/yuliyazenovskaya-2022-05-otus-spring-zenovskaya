package ru.otus.spring.service.impl;

import ru.otus.spring.common.Constants;
import ru.otus.spring.domain.Question;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import ru.otus.spring.service.ExamineService;
import ru.otus.spring.service.PrintService;
import ru.otus.spring.service.QuestionService;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@PropertySource("classpath:examine.properties")
public class ExamineServiceImpl implements ExamineService {
    private final QuestionService questionService;
    private final PrintService printService;

    private final int passingScore;
    private final int passingAttempts;

    public ExamineServiceImpl(
            QuestionService questionService,
            PrintService printService,
            @Value("${examine.passing.score}") int passingScore,
            @Value("${examine.passing.attempts}") int passingAttempts
    ) {
        this.questionService = questionService;
        this.printService = printService;
        this.passingScore = passingScore;
        this.passingAttempts = passingAttempts;
    }

    @Override
    public void conductExam(InputStream in, PrintStream out) {
        for (int attempt = 1; attempt < this.passingAttempts + 1; attempt++) {
            int result = conductExamAttempt(in, out);
            if (result >= passingScore) {
                printService.printResult(Constants.SUCCESS_RESULT, result, out);
                break;
            }
            if (attempt == passingAttempts) {
                printService.printResult(Constants.FAILED_RESULT, result, out);
            } else {
                printService.printResult(Constants.ONE_MORE_ATTEMPT_RESULT, result, out);
            }
        }
    }

    private int conductExamAttempt(InputStream in, PrintStream out) {
        printService.printGreetings(out);

        Scanner scanner = new Scanner(in);
        if (scanner.nextLine().equals(Constants.START_COMMAND)) {
            AtomicInteger rightAnswers = new AtomicInteger();
            questionService.getQuestions().forEach(question -> {
                printService.printQuestion(out, question);

                String answer = scanner.nextLine();

                if (isAnswerRight(question, answer)) {
                    rightAnswers.getAndIncrement();
                }
            });

            return rightAnswers.get();
        }

        return 0;
    }

    private boolean isAnswerRight(Question question, String answer) {
        if (question.type.equals(Constants.MULTI_SELECT_QUESTION_TYPE)) {

            return Arrays.equals(
                    Arrays.stream(question.answers.split(";")).map(Integer::parseInt).sorted().toArray(),
                    Arrays.stream(answer.split(";")).map(Integer::parseInt).sorted().toArray());
        } else {
            return question.answers.equals(answer);
        }
    }
}
