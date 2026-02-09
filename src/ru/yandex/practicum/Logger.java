package ru.yandex.practicum;

public interface Logger {
    void info(String msg);

    void debug(String msg);

    void error(String msg);
}