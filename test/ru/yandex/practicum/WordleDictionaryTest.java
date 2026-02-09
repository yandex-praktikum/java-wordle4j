package ru.yandex.practicum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.*;

class WordleDictionaryTest {

    private WordleDictionary dict;

    @BeforeEach
    void setup() {
        PrintWriterLogger logger =
                new PrintWriterLogger(new PrintWriter(System.out));

        dict = new WordleDictionary(logger);
    }

    @Test
    void shouldAddWord() {
        dict.addWord("тест");

        assertTrue(dict.containsWord("тест"));
    }

    @Test
    void shouldThrowIfWordNull() {
        assertThrows(
                IllegalArgumentException.class,
                () -> dict.addWord(null)
        );
    }

    @Test
    void shouldThrowIfWordBlank() {
        assertThrows(
                IllegalArgumentException.class,
                () -> dict.addWord("   ")
        );
    }

    @Test
    void shouldNormalizeWords() {
        dict.addWord("ЁЛКА");

        WordleDictionary normalized = dict.normalized();

        assertTrue(normalized.containsWord("елка"));
    }

    @Test
    void normalizedShouldNotModifyOriginal() {
        dict.addWord("ЁЛКА");

        WordleDictionary normalized = dict.normalized();

        assertTrue(dict.containsWord("ЁЛКА"));
        assertFalse(dict.containsWord("елка"));
    }

    @Test
    void shouldFilterByLength() {
        dict.addWord("кот");
        dict.addWord("собака");

        WordleDictionary filtered = dict.filteredByLength(3);

        assertTrue(filtered.containsWord("кот"));
        assertFalse(filtered.containsWord("собака"));
    }

    @Test
    void shouldThrowIfDictionaryEmpty() {
        assertThrows(
                IllegalStateException.class,
                () -> dict.getRandomWord()
        );
    }
}