import domain.Question;
import org.junit.Assert;
import org.junit.Test;
import service.QuestionService;
import service.impl.QuestionServiceImpl;

import java.util.List;

public class QuestionTest {

    @Test
    public void parseQuestions() {
        QuestionService questionService = new QuestionServiceImpl("/questions.csv");
        List<Question> questions = questionService.getQuestions();

        Assert.assertEquals(5, questions.size());

        Assert.assertEquals(4, questions.get(0).options.size());
        Assert.assertEquals(6, questions.get(1).options.size());
        Assert.assertNull(questions.get(2).options);
        Assert.assertEquals(4, questions.get(3).options.size());
        Assert.assertEquals(6, questions.get(4).options.size());
    }

}
