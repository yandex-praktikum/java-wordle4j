package ru.yandex.practicum.exception;

public class DictionaryLoadException extends Exception {
    public DictionaryLoadException(String message, Throwable cause) {
        super(message, cause);
    }
}
