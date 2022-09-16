package ru.otus.spring.service.impl;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import ru.otus.spring.common.Constants;
import ru.otus.spring.domain.ChoiceQuestion;
import ru.otus.spring.domain.FreeFormatQuestion;
import ru.otus.spring.domain.Option;
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
        return readQuestionsFromFile();
    }

    private List<Question> readQuestionsFromFile() {
        List<Question> questions = new ArrayList<>();

        InputStream inputStream = this.getClass().getResourceAsStream(this.questionsCsv);
        try (
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                CSVReader csvReader = new CSVReaderBuilder(bufferedReader).withSkipLines(1).build()
                ) {
            String[] fileString;
            while ((fileString = csvReader.readNext()) != null) {
                questions.add(readQuestionFromFile(fileString));
            }
        } catch (IOException e) {
            throw new ReadQuestionException("Error in reading questions from file", e);
        }

        return questions;
    }

    private Question readQuestionFromFile(String[] fileString) {
        Question question;
        try {
            String questionType = fileString[1];

            if (questionType.equals(Constants.SINGLE_SELECT_QUESTION_TYPE)
                    || questionType.equals(Constants.MULTI_SELECT_QUESTION_TYPE)) {
                question = readChoiceQuestion(fileString);
            } else {
                question = readFreeFormatQuestion(fileString);
            }

            return question;
        } catch (NumberFormatException e) {
            throw new ParseValuesException("QuestionId cannot be parsed into Long", e);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ReadQuestionException(Constants.NOT_ENOUGH_DATA_IN_FILE_EXCEPTION, e);
        }
    }

    private Question readChoiceQuestion(String[] fileString) {
        ChoiceQuestion question = new ChoiceQuestion(
                Long.parseLong(fileString[0]),
                fileString[1],
                fileString[2]
        );
        question.setOptions(parseOptions(fileString));

        return question;
    }

    private Question readFreeFormatQuestion(String[] fileString) {
        FreeFormatQuestion question = new FreeFormatQuestion(
                Long.parseLong(fileString[0]),
                fileString[1],
                fileString[2]
        );
        question.setRightAnswer(parseRightAnswer(fileString));

        return question;
    }

    private List<Option> parseOptions(String[] str) {
        try {
            List<Option> options = new ArrayList<>();
            for (int i = 3; i < 9; i++) {
                if (str[i].equals("")) break;
                Option option = new Option(i - 2, str[i]);
                option.setRight(str[9].contains(String.valueOf((i-2))));

                options.add(option);
            }
            return options;
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ReadQuestionException(Constants.NOT_ENOUGH_DATA_IN_FILE_EXCEPTION, e);
        }
    }

    private String parseRightAnswer(String[] str) {
        try {
            return str[9];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ReadQuestionException(Constants.NOT_ENOUGH_DATA_IN_FILE_EXCEPTION, e);
        }
    }
}
