package ru.otus.spring.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.spring.service.IOService;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

@Service
public class IOServiceConsoleStream implements IOService {
    private final PrintStream printStream;
    private final InputStream inputStream;

    public IOServiceConsoleStream(
            @Value("#{ T(java.lang.System).in}") InputStream in,
            @Value("#{ T(java.lang.System).out}") PrintStream out) {
        this.inputStream = in;
        this.printStream = out;
    }

    @Override
    public String readString() {
        return new Scanner(inputStream).nextLine();
    }

    @Override
    public void printString(String string) {
        printStream.println(string);
    }
}
