package service.impl;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import domain.Question;
import service.QuestionService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class QuestionServiceImpl implements QuestionService {
    private final String questionsCsv;

    public QuestionServiceImpl(String questionsCsv) {
        this.questionsCsv = questionsCsv;
    }

    @Override
    public List<Question> getQuestions() {
        List<Question> questions = new ArrayList<>();

        readQuestionsFromFile(questions);

        return questions;
    }

    private List<Question> readQuestionsFromFile(List<Question> questions) {
        InputStream inputStream = this.getClass().getResourceAsStream(this.questionsCsv);
        try (
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                CSVReader csvReader = new CSVReaderBuilder(bufferedReader).withSkipLines(1).build()
                ) {
            String[] str;
            while ((str = csvReader.readNext()) != null) {
                Question question = new Question(
                        Long.parseLong(str[0]),
                        str[1],
                        str[2]
                );
                List<String> options = new ArrayList<>();
                for (int i = 3; i < 9; i++) {
                    if (str[i].equals("")) break;
                    options.add(str[i]);
                }
                if (options.size() > 0) question.options = options;

                questions.add(question);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return questions;
    }
}
