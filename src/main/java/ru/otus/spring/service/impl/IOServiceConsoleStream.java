package ru.otus.spring.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.spring.service.IOService;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

@Service
public class IOServiceConsoleStream implements IOService {
    private final PrintStream printStream;
    private final InputStream inputStream;

    public IOServiceConsoleStream() {
        this.inputStream = System.in;
        this.printStream = System.out;
    }

    @Override
    public String readString() {
        Scanner scanner = new Scanner(inputStream);
        return scanner.nextLine();
    }

    @Override
    public void printString(String string) {
        printStream.println(string);
    }
}
