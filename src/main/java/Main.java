import org.springframework.context.support.ClassPathXmlApplicationContext;
import service.ParseQuestionService;

public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        ParseQuestionService service = context.getBean(ParseQuestionService.class);
        service.parseQuestions();

        context.close();
    }
}
