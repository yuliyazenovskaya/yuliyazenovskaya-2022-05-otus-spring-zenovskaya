package service.impl;

import domain.Question;
import service.OutputService;
import service.QuestionService;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class OutputServiceImpl implements OutputService {
    private final QuestionService questionService;

    public OutputServiceImpl(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Override
    public List<Question> printQuestions(PrintStream printStream) {
        List<Question> questions = new ArrayList<>();
        questionService.getQuestions().forEach(question -> {
            printStream.println(question.id + ". " + question.text);
            AtomicInteger counter = new AtomicInteger(1);
            if (question.options != null) {
                question.options.forEach(option ->
                        printStream.println("    " + counter.getAndIncrement() + ". " + option));
            }

            questions.add(question);
        });

        return questions;
    }
}
