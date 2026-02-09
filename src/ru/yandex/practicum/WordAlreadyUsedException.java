package ru.yandex.practicum;

public class WordAlreadyUsedException extends GameException {
    public WordAlreadyUsedException(String message) {
        super(message);
    }
}
