import domain.Option;
import domain.Question;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import service.OptionService;
import service.OutputService;
import service.QuestionService;
import service.impl.OutputServiceImpl;

import java.io.IOException;
import java.util.Collections;

@SpringBootTest
@RunWith(SpringRunner.class)
public class QuestionTest {
    public static Question question = new Question(1, "select", "tratata");
    public static Option option = new Option(2, 1, "this");

    @MockBean
    private QuestionService questionService;

    @MockBean
    private OptionService optionService;

    @Test
    public void printQuestions() throws IOException {
        OutputService outputService = new OutputServiceImpl(questionService);

        Mockito.when(questionService.getQuestions()).thenReturn(Collections.singletonList(question));
        Mockito.when(optionService.getQuestionOptions(question.id)).thenReturn(Collections.singletonList(option));

        Question printedQuestion = outputService.printQuestions().get(0);

        Assert.assertEquals(question.id, printedQuestion.id);
        Assert.assertEquals(question.type, printedQuestion.type);
        Assert.assertEquals(question.text, printedQuestion.text);
        Assert.assertEquals(question.id, printedQuestion.options.get(0).questionId);
        Assert.assertEquals(option.text, printedQuestion.options.get(0).text);
    }

}
