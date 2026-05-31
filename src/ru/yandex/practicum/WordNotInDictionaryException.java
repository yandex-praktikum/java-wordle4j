package ru.yandex.practicum;

public class WordNotInDictionaryException extends Exception {
    public WordNotInDictionaryException(String message) {
        super(message);
    }
}