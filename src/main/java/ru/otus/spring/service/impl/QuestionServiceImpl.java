package ru.otus.spring.service.impl;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import ru.otus.spring.common.Constants;
import ru.otus.spring.domain.Question;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.spring.exceptions.ParseValuesException;
import ru.otus.spring.exceptions.ReadQuestionException;
import ru.otus.spring.service.QuestionService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
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

    private void readQuestionsFromFile(List<Question> questions) {
        InputStream inputStream = this.getClass().getResourceAsStream(this.questionsCsv);
        try (
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                CSVReader csvReader = new CSVReaderBuilder(bufferedReader).withSkipLines(1).build()
                ) {
            String[] fileString;
            while ((fileString = csvReader.readNext()) != null) {
                readQuestionFromFile(fileString, questions);
            }
        } catch (IOException e) {
            throw new ReadQuestionException("Error in reading questions from file", e);
        }

    }

    private void readQuestionFromFile(String[] fileString, List<Question> readQuestions) {
        try {
            Question question = new Question(
                    Long.parseLong(fileString[0]),
                    fileString[1],
                    fileString[2]
            );
            parseOptions(fileString, question);
            parseRightAnswer(fileString, question);
            readQuestions.add(question);
        } catch (NumberFormatException e) {
            throw new ParseValuesException("QuestionId cannot be parsed into Long", e);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ReadQuestionException(Constants.NOT_ENOUGH_DATA_IN_FILE_EXCEPTION, e);
        }
    }

    private void parseOptions(String[] str, Question question) {
        try {
            List<String> options = new ArrayList<>();
            for (int i = 3; i < 9; i++) {
                if (str[i].equals("")) break;
                options.add(str[i]);
            }
            if (options.size() > 0) question.setOptions(options);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ReadQuestionException(Constants.NOT_ENOUGH_DATA_IN_FILE_EXCEPTION, e);
        }


    }

    private void parseRightAnswer(String[] str, Question question) {
        try {
            question.setRightAnswer(str[9]);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ReadQuestionException(Constants.NOT_ENOUGH_DATA_IN_FILE_EXCEPTION, e);
        }
    }
}
