package service.impl;

import service.OutputService;

import java.io.PrintStream;

public class ConsoleOutputServiceImpl implements OutputService {
    private final PrintStream out;

    ConsoleOutputServiceImpl() {
        this(System.out);
    }

    ConsoleOutputServiceImpl(PrintStream out) {
        this.out = out;
    }

    @Override
    public void printString(String string) {
        out.println(string);
    }
}
