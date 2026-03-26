package ru.yandex.practicum.exception;

public class WordNotFoundException extends Exception {
    public WordNotFoundException(String word) {
        super("Слово " + word + " не найдено в словаре.");
    }

}
