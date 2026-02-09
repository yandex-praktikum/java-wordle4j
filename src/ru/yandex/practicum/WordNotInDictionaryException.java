package ru.yandex.practicum;

public class WordNotInDictionaryException extends GameException {
    public WordNotInDictionaryException(String message) {
        super(message);
    }
}
