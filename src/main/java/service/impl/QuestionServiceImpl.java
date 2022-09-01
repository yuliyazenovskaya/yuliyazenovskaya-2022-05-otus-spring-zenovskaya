package service.impl;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import common.Constants;
import domain.Question;
import service.OptionService;
import service.QuestionService;
import sun.applet.Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class QuestionServiceImpl implements QuestionService {
    private final OptionService optionService;
    private final String questionsCsv;
    private final List<Question> questions = new ArrayList<>();

    public QuestionServiceImpl(OptionService optionService, String questionsCsv) {
        this.optionService = optionService;
        this.questionsCsv = questionsCsv;
    }

    @Override
    public List<Question> getQuestions() throws IOException {
        readOptionsFromFile();

        questions.forEach(question -> {
            if (question.type.equals(Constants.SINGLE_SELECT_QUESTION_TYPE)
                    || question.type.equals(Constants.MULTI_SELECT_QUESTION_TYPE)) {
                try {
                    question.options = optionService.getQuestionOptions(question.id);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        return questions;
    }

    private void readOptionsFromFile() throws IOException {
        InputStream inputStream = Main.class.getResourceAsStream(this.questionsCsv);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        CSVReader csvReader = new CSVReaderBuilder(bufferedReader).withSkipLines(1).build();
        String[] str;
        while ((str = csvReader.readNext()) != null) {
            questions.add(new Question(
                    Long.parseLong(str[0]),
                    str[1],
                    str[2]
            ));
        }
        csvReader.close();
        bufferedReader.close();
    }
}
