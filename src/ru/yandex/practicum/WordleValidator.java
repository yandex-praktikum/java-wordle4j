package ru.yandex.practicum;

import ru.yandex.practicum.exceptions.InputContainsSpacesException;
import ru.yandex.practicum.exceptions.InputOutOfLanguageException;
import ru.yandex.practicum.exceptions.InputOutOfLengthException;
import ru.yandex.practicum.exceptions.WordNotFoundInDictionary;

import java.io.PrintWriter;

public class WordleValidator {

    private WordleDictionary dictionary;
    private PrintWriter writer;

    public WordleValidator(WordleDictionary dictionary, PrintWriter writer) {
        this.dictionary = dictionary;
        this.writer = writer;
    }

    public String validateUserInput(String userWordInput) {
        userWordInput = userWordInput.toLowerCase().trim();
        if (userWordInput.length() != 5) {
            writer.println("Введенное пользователем слово выходит за пределы предусмотренной длины");
            throw new InputOutOfLengthException();
        } else if (userWordInput.contains(" ")) {
            writer.println("Введенное пользователем слово содержит пробелы");
            throw new InputContainsSpacesException();
        } else if (!userWordInput.matches("[А-Яа-яЁё]+")) {
            writer.println("Введенное пользователем слово содержит символы, кроме русских");
            throw new InputOutOfLanguageException();
        } else if (!dictionary.getWordsForCheckingUserInput().contains(userWordInput)) {
            writer.println("Введенное пользователем слово отсутствует в словаре");
            throw new WordNotFoundInDictionary();
        }
        return userWordInput;
    }

}
