package ru.yandex.practicum.exception;

public class DictionaryFileException extends RuntimeException {
    public DictionaryFileException(String message, Throwable cause) {
      super(message, cause);
    }
}
