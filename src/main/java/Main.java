import org.springframework.context.support.ClassPathXmlApplicationContext;
import service.OutputService;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        OutputService service = context.getBean(OutputService.class);
        service.printQuestions();

        context.close();
    }
}
