package ru.otus.spring.service;

import java.io.InputStream;
import java.io.PrintStream;

public interface ExamineService {
    void conductExam(InputStream in, PrintStream out);
}
