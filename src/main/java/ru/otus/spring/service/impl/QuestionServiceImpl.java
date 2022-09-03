package ru.otus.spring.service.impl;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import ru.otus.spring.domain.Question;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import ru.otus.spring.service.QuestionService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
@PropertySource("classpath:examine.properties")
public class QuestionServiceImpl implements QuestionService {
    private final String questionsCsv;

    public QuestionServiceImpl(@Value("${question.csv.path}") String questionsCsv) {
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
                parseOptions(str, question);
                parseAnswers(str, question);
                questions.add(question);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return questions;
    }

    private void parseOptions(String[] str, Question question) {
        List<String> options = new ArrayList<>();
        for (int i = 3; i < 9; i++) {
            if (str[i].equals("")) break;
            options.add(str[i]);
        }
        if (options.size() > 0) question.options = options;
    }

    private void parseAnswers(String[] str, Question question) {
        question.answers = str[9];
    }
}
