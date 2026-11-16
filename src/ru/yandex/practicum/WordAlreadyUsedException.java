package ru.yandex.practicum;
// Исключение лежит в том же пакете, что и остальные сущности игры.

public class WordAlreadyUsedException extends InvalidWordException {
    // Класс обозначает случай, когда игрок повторно вводит уже использованное слово.
    public WordAlreadyUsedException(String message) {
        // Конструктор передаёт сообщение в базовый класс исключения.
        super(message);
        // Сохраняем текст ошибки в родительском классе.
    }
}
