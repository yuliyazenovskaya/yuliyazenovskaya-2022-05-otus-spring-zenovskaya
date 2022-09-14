import ru.otus.spring.domain.Question;
import org.junit.Assert;
import org.junit.Test;
import ru.otus.spring.service.QuestionService;
import ru.otus.spring.service.impl.QuestionServiceImpl;

import java.util.List;

public class QuestionTest {

    @Test
    public void parseQuestions() {
        QuestionService questionService = new QuestionServiceImpl("/questions.csv");
        List<Question> questions = questionService.getQuestions();

        Assert.assertEquals(5, questions.size());
    }

}
