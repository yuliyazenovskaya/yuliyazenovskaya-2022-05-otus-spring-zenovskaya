package service.impl;

import service.OutputService;
import service.ParseQuestionService;
import service.QuestionService;

public class ParseQuestionServiceImpl implements ParseQuestionService {
    private final QuestionService questionService;
    private final OutputService outputService;

    public ParseQuestionServiceImpl(QuestionService questionService, OutputService outputService) {
        this.questionService = questionService;
        this.outputService = outputService;
    }

    @Override
    public void parseQuestions() {
        questionService.getQuestions().forEach(question -> {
            outputService.printString(question.id + ". " + question.text);

            if (question.options != null) {
                for (int counter = 0; counter < question.options.size(); counter++) {
                    outputService.printString("    " + (counter + 1) + ". " + question.options.get(counter));
                }
            }
        });
    }
}
