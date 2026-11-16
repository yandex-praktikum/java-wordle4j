package ru.yandex.practicum;
// Класс находится в пакете игры Wordle, чтобы его можно было легко использовать.

public class WordNotFoundInDictionaryException extends InvalidWordException {
    // Это исключение означает, что игрок ввёл слово, которого нет в словаре.
    public WordNotFoundInDictionaryException(String message) {
        // Конструктор сохраняет сообщение об ошибке в базовом классе.
        super(message);
        // Передаём текст ошибки в родительский класс Exception.
    }
}
