package ru.yandex.practicum;

public class ProgramException extends Exception {
    public ProgramException(String message) {
        super(message);
    }

    public ProgramException(String message, Throwable cause) {
        super(message, cause);
    }
}
