package ru.yandex.practicum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
class WordleTest {
    private WordleDictionary dictionary;

    @BeforeEach
    void setUp() {
        // Создаем зависимости перед каждым тестом
        File commonLog = new File("error.log");
        File file = new File("words_ru.txt");

        WordleDictionaryLoader loader = new WordleDictionaryLoader(commonLog);
        WordleDictionary dictionary = loader.load(file);
        WordleGame logicOfGame = new WordleGame(dictionary);

    }

    @Test
    void testBlankInputProvidesHint() {
        String targetWord = "Яблоко";
        String input = "\n";
        Set<Character> listOfChar = new HashSet<>();
        Scanner sc = new Scanner(new ByteArrayInputStream(input.getBytes()));
        String result = dictionary.getRandomWordIfEnter(listOfChar, targetWord);

        assertTrue(result.isBlank(), "Метод должен вернуть пустую строку при нажатии Enter");

        // Проверяем, что если подставить логику обработки:
        if (result.isBlank()) {
            result = "apple"; // Имитируем получение слова из словаря
        }
        assertEquals("apple", result);
    }

   /* @Test
    void testWordValidationLowerCases() {
        String input = "APPLE\n";
        Scanner sc = new Scanner(new ByteArrayInputStream(input.getBytes()));

        //String result = game.runWordleGame(sc);

        assertEquals("apple", result, "Слово должно быть приведено к нижнему регистру");
    }*/
}
