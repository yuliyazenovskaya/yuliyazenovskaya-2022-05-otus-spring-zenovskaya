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

        Assert.assertEquals(4, questions.get(0).getOptions().size());
        Assert.assertEquals(6, questions.get(1).getOptions().size());
        Assert.assertNull(questions.get(2).getOptions());
        Assert.assertEquals(4, questions.get(3).getOptions().size());
        Assert.assertEquals(6, questions.get(4).getOptions().size());
    }

}
