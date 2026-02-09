package ru.yandex.practicum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.*;

class WordleTest {

    private Wordle wordle;
    private Logger logger;

    @BeforeEach
    void setup() {
        logger = new PrintWriterLogger(
                new PrintWriter(System.out));
        wordle = new Wordle();
    }

    @Test
    void shouldRejectLatinLetters() {
        Wordle wordle = new Wordle();
        assertFalse(wordle.notContainForbiddenCharacters("slovo1"));
    }

    @Test
    void shouldAcceptRussianLetters() {
        Wordle wordle = new Wordle();
        assertTrue(wordle.notContainForbiddenCharacters("хлеб"));
    }

    @Test
    void loadDictionaryShouldReturnDictionary() throws DictionaryLoadException {
        WordleDictionary dict = wordle.loadDictionary(logger);

        assertNotNull(dict);
    }
}
