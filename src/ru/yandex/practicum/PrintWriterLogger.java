package ru.yandex.practicum;

import java.io.FileWriter;
import java.io.PrintWriter;

public class PrintWriterLogger implements Logger, AutoCloseable {

    private final PrintWriter writer;

    public PrintWriterLogger(FileWriter fileWriter, boolean autoFlush) {
        this.writer = new PrintWriter(fileWriter, autoFlush);
    }

    public PrintWriterLogger(PrintWriter printWriter) {
        this.writer = printWriter;
    }

    @Override
    public void info(String msg) {
        writer.println("INFO  " + msg);
    }

    @Override
    public void debug(String msg) {
        writer.println("DEBUG " + msg);
    }

    @Override
    public void error(String msg) {
        writer.println("ERROR " + msg);
    }

    @Override
    public void close() {
        writer.close();
    }
}