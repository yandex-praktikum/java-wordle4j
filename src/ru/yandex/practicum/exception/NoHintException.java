package ru.yandex.practicum.exception;

public class NoHintException extends Exception {
    public NoHintException() {
        super();
    }

    public NoHintException(String message) {
        super("Нет доступных подсказок: " + message);
    }
}
