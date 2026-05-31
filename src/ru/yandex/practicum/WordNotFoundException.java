package ru.yandex.practicum;

public class WordNotFoundException extends Exception {
    public WordNotFoundException(String message) {
        super(message);
    }
}