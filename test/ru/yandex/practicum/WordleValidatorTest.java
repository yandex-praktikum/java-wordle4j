package ru.yandex.practicum;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.exceptions.InputContainsSpacesException;
import ru.yandex.practicum.exceptions.InputOutOfLanguageException;
import ru.yandex.practicum.exceptions.InputOutOfLengthException;
import ru.yandex.practicum.exceptions.WordNotFoundInDictionary;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WordleValidatorTest {

    PrintWriter writer = new PrintWriter(new StringWriter());

    @Test
    @DisplayName("Ввод не подходит по длине")
    void validateUserInputWhenWordOutOfLength() {
        WordleDictionary dictionary = new WordleDictionary(List.of("аллах, ангар"));
        WordleValidator wordleValidator = new WordleValidator(dictionary, writer);

        assertThrows(InputOutOfLengthException.class, () -> wordleValidator.validateUserInput("пять"));
    }

    @Test
    @DisplayName("Ввод содержит пробелы")
    void validateUserInputWhenWordContainsSpaces() {
        WordleDictionary dictionary = new WordleDictionary(List.of("аллах, ангар"));
        WordleValidator wordleValidator = new WordleValidator(dictionary, writer);

        assertThrows(InputContainsSpacesException.class, () -> wordleValidator.validateUserInput("пя ть"));
    }

    @Test
    @DisplayName("Ввод содержит символы не из русского алфавита")
    void validateUserInputWhenWordContainsOtherCharactersThenRussian() {
        WordleDictionary dictionary = new WordleDictionary(List.of("аллах, ангар"));
        WordleValidator wordleValidator = new WordleValidator(dictionary, writer);

        assertThrows(InputOutOfLanguageException.class, () -> wordleValidator.validateUserInput("qwert"));
        assertThrows(InputOutOfLanguageException.class, () -> wordleValidator.validateUserInput("隸書隸書隸"));
    }

    @Test
    @DisplayName("Ввод содержит слово, которого нет в словаре")
    void validateUserInputWhenWordNotFoundInDictionary() {
        WordleDictionary dictionary = new WordleDictionary(List.of("аллах, ангар"));
        WordleValidator wordleValidator = new WordleValidator(dictionary, writer);

        assertThrows(WordNotFoundInDictionary.class, () -> wordleValidator.validateUserInput("абвгд"));
    }

}