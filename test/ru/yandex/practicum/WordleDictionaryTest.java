package ru.yandex.practicum;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;


class WordleDictionaryTest {

    private WordleDictionary dictionary;
    private List<String> testWords;

    @BeforeEach
    void setUp() {
        testWords = Arrays.asList("пожар", "глыба", "тест");
        dictionary = new WordleDictionary(testWords);
    }

    @Test
    void getWordsShouldReturnCorrectList() {
        assertEquals(testWords, dictionary.getWords());
    }

    @Test
    void containsWordShouldReturnTrueForExistingWord() {
        assertTrue(dictionary.contains("пожар"));
        assertTrue(dictionary.contains("глыба"));
    }

    @Test
    void containsWordShouldReturnFalseForNonExistingWord() {
        assertFalse(dictionary.contains("бурда"));
        assertFalse(dictionary.contains("пожары"));
        assertFalse(dictionary.contains("тесты"));
    }

    @Test
    void wordsAnalizeShouldReturnTrueForIdenticalWords() {
        assertTrue(dictionary.wordsAnalize("пожар", "пожар"));
    }

    @Test
    void wordsAnalizeShouldReturnFalseForDifferentWords() {
        assertFalse(dictionary.wordsAnalize("пожар", "глыба"));
        assertFalse(dictionary.wordsAnalize("пожар", "тест"));
    }

    @Test
    void getRandomWordShouldReturnRandomWord() {
        assertTrue(testWords.contains(dictionary.getRandomWord()));
    }

}
