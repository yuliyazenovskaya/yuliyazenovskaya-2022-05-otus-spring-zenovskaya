package ru.otus.spring.service.impl;

import ru.otus.spring.common.Constants;
import ru.otus.spring.domain.Question;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import ru.otus.spring.service.*;

@Service
@PropertySource("classpath:examine.properties")
public class ExamineServiceImpl implements ExamineService {
    private final QuestionService questionService;
    private final ExamMessageService examMessageService;
    private final IOService ioService;
    private final AnswerService answerService;

    private final int passingScore;
    private final int passingAttempts;

    public ExamineServiceImpl(
            QuestionService questionService,
            ExamMessageService examMessageService,
            IOService ioService,
            AnswerService answerService,
            @Value("${examine.passing.score}") int passingScore,
            @Value("${examine.passing.attempts}") int passingAttempts
    ) {
        this.questionService = questionService;
        this.examMessageService = examMessageService;
        this.ioService = ioService;
        this.answerService = answerService;
        this.passingScore = passingScore;
        this.passingAttempts = passingAttempts;
    }

    @Override
    public void conductExam() {
        for (int attempt = 1; attempt < this.passingAttempts + 1; attempt++) {
            int result = conductExamAttempt();

            if (result >= passingScore) {
                examMessageService.printResult(Constants.SUCCESS_RESULT, result);
                break;
            }

            if (attempt == passingAttempts) {
                examMessageService.printResult(Constants.FAILED_RESULT, result);
            } else {
                examMessageService.printResult(Constants.ONE_MORE_ATTEMPT_RESULT, result);
            }
        }
    }

    private int conductExamAttempt() {
        examMessageService.printGreetings();

        if (ioService.readString().equals(Constants.START_COMMAND)) {
            int rightAnswers = 0;
            for (Question question : questionService.getQuestions()) {
                examMessageService.printQuestion(question);

                if (answerService.isAnswerRight(question, processAnswer(question.type))) {
                    rightAnswers++;
                }
            }

            return rightAnswers;
        } else {
            return 0;
        }
    }

    private String processAnswer(String questionType) {
        String answer = ioService.readString();
        if (answerService.isAnswerFormatValid(questionType, answer)) {
            return answer;
        } else {
            examMessageService.printFormatError(questionType);
            return processAnswer(questionType);
        }
    }
}
