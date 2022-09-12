package ru.otus.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.spring.service.ExamineService;

@Configuration
@ComponentScan
@PropertySource("classpath:examine.properties")
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);

        ExamineService examineService = context.getBean(ExamineService.class);
        examineService.conductExam();

        context.close();
    }
}
