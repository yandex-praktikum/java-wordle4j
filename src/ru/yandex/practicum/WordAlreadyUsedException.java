package ru.yandex.practicum;

public class WordAlreadyUsedException extends InvalidWordException {
    public WordAlreadyUsedException(String message) {
        super(message);
    }
}
