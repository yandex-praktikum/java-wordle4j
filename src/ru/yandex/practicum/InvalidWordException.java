package ru.yandex.practicum;
// Класс объявлен в пакете игры Wordle.

public class InvalidWordException extends Exception {
    // Это базовый класс для всех ошибок, связанных с неправильным словом.
    public InvalidWordException(String message) {
        // Конструктор принимает текст ошибки, который будет передан родителю.
        super(message);
        // Вызываем конструктор Exception, чтобы сохранить текст сообщения.
    }
}
