package ru.yandex.practicum;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class WordleDictionaryTest {

    @Test
    void testContains() {
        WordleDictionary dict = new WordleDictionary(List.of("герой", "кодек"));
        assertTrue(dict.contains("герой"));
        assertFalse(dict.contains("ложка"));
    }

    @Test
    void testGetRandomWord() {
        WordleDictionary dict = new WordleDictionary(List.of("герой"));
        assertEquals("герой", dict.getRandomWord());
    }
}