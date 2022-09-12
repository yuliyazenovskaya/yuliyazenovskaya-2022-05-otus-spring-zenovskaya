package ru.otus.spring.service.impl;

import ru.otus.spring.common.Constants;
import ru.otus.spring.domain.Question;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.spring.service.*;

@Service
public class ExamineServiceImpl implements ExamineService {
    private final QuestionService questionService;
    private final QuestionPrinterService questionPrinterService;
    private final IOService ioService;
    private final AnswerService answerService;
    private final ResultProcessingService resultProcessingService;

    private final int passingAttempts;

    public ExamineServiceImpl(
            QuestionService questionService,
            QuestionPrinterService questionPrinterService,
            IOService ioService,
            AnswerService answerService,
            ResultProcessingService resultProcessingService,
            @Value("${examine.passing.attempts}") int passingAttempts
    ) {
        this.questionService = questionService;
        this.questionPrinterService = questionPrinterService;
        this.ioService = ioService;
        this.answerService = answerService;
        this.resultProcessingService = resultProcessingService;
        this.passingAttempts = passingAttempts;
    }

    @Override
    public void conductExam() {
        for (int attempt = 1; attempt < this.passingAttempts + 1; attempt++) {
            int result = conductExamAttempt();

            String processedResult = resultProcessingService.processResult(result, attempt);

            if (!processedResult.equals(Constants.ONE_MORE_ATTEMPT_RESULT)) break;
        }
    }

    private int conductExamAttempt() {
        ioService.printString("Your exam attempt is starting now! Write 'start' to begin! Good luck.");

        if (ioService.readString().equals(Constants.START_COMMAND)) {
            int rightAnswers = 0;
            for (Question question : questionService.getQuestions()) {
                questionPrinterService.printQuestion(question);

                if (answerService.isOptionRight(question, getValidAnswer(question.getType()))) {
                    rightAnswers++;
                }
            }
            return rightAnswers;
        } else {
            return 0;
        }
    }

    private String getValidAnswer(String questionType) {
        String answer = ioService.readString();
        if (answerService.isAnswerFormatValid(questionType, answer)) {
            return answer;
        } else {
            answerService.printAnswerFormatError(questionType);
            return getValidAnswer(questionType);
        }
    }
}
