package ru.yandex.practicum.exception;

public class EmptyDictionaryException extends Exception {
    public EmptyDictionaryException() {
        super("Словарь пуст");
    }
}
