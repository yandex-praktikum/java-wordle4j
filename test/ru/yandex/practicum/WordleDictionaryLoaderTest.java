package ru.yandex.practicum;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.*;

class WordleDictionaryLoaderTest {

    @Test
    void loadDictionaryWhenPathIsCorrect() throws IOException {
        WordleDictionaryLoader loader = new WordleDictionaryLoader(new PrintWriter(new StringWriter()));

        WordleDictionary dictionary = loader.loadDictionary("words_ru.txt");

        assertNotNull(dictionary);
        assertNotEquals(0 , dictionary.getWords().size());
    }

    @Test
    void loadDictionaryWhenPathIsWrong() throws IOException {
        WordleDictionaryLoader loader = new WordleDictionaryLoader(new PrintWriter(new StringWriter()));

        assertThrows(RuntimeException.class, () -> loader.loadDictionary(""));
    }

}