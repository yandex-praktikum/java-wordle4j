package ru.yandex.practicum;

public class WordNotFoundInDictionary extends RuntimeException {

    public WordNotFoundInDictionary(String message) {
        super(message);
    }
}