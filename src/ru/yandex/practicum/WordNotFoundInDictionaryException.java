package ru.yandex.practicum;

public class WordNotFoundInDictionaryException extends InvalidWordException {
    public WordNotFoundInDictionaryException(String message) {
        super(message);
    }
}
