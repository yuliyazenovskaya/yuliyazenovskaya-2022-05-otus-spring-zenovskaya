package service.impl;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import domain.Question;
import exceptions.ParseLongValueFromSourceException;
import exceptions.ReadQuestionException;
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

    private void readQuestionsFromFile(List<Question> questions) {
        InputStream inputStream = this.getClass().getResourceAsStream(this.questionsCsv);
        try (
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                CSVReader csvReader = new CSVReaderBuilder(bufferedReader).withSkipLines(1).build()
                ) {
            String[] fileString;
            while ((fileString = csvReader.readNext()) != null) {
                readQuestion(fileString, questions);
            }
        } catch (IOException e) {
            throw new ReadQuestionException("Error in reading questions from file");
        }
    }

    private void readQuestion(String[] fileString, List<Question> readQuestions) {
        try {
            Question question = new Question(
                    Long.parseLong(fileString[0]),
                    fileString[1],
                    fileString[2]
            );
            List<String> options = new ArrayList<>();
            for (int i = 3; i < 9; i++) {
                if (fileString[i].equals("")) break;
                options.add(fileString[i]);
            }
            if (options.size() > 0) question.options = options;

            readQuestions.add(question);
        } catch (NumberFormatException e) {
            throw new ParseLongValueFromSourceException("QuestionId cannot be parsed into Long");
        }
    }
}
