package ru.otus.spring.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.spring.service.IOService;
import ru.otus.spring.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {
    private final IOService ioService;

    public StudentServiceImpl(IOService ioService) {
        this.ioService = ioService;
    }

    @Override
    public String getStudentName() {
        ioService.printString("Welcome to the exam! Please, enter first and last name.");
        return ioService.readString();
    }
}
