package ru.yandex.practicum;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import ru.yandex.practicum.exception.EmptyDictionaryException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class WordleDictionaryLoaderTest {

    @TempDir
    Path tempDir;

    @Test
    void testLoadWithValidFile() throws IOException, EmptyDictionaryException {
        // Создаем временный файл с тестовыми данными:
        File tempFile = tempDir.resolve("test_dict.txt").toFile();
        java.nio.file.Files.write(tempFile.toPath(), List.of(
                "Apple",
                "бАНАН",
                "  Малина  ",
                "абвгд",
                "ёлка",
                "тест"
        ));

        WordleDictionaryLoader loader = new WordleDictionaryLoader();
        WordleDictionary dictionary = loader.load(tempFile.getAbsolutePath());

        assertNotNull(dictionary);
        List<String> words = dictionary.getWords();
        assertEquals(3, words.size());
        assertTrue(words.contains("apple"));
        assertTrue(words.contains("банан"));
        assertFalse(words.contains("елка"));
        assertFalse(words.contains("абвгде"));
        assertFalse(words.contains("тест"));
    }

    @Test
    void testLoadWithNonExistentFile() {
        WordleDictionaryLoader loader = new WordleDictionaryLoader();
        assertThrows(IOException.class, () -> {
            loader.load("/non/existent/file.txt");
        });
    }

    @Test
    void testNormalize() {
        assertEquals("яблоко", WordleDictionaryLoader.normalize("  ЯбЛОкО  "));
        assertEquals("елка", WordleDictionaryLoader.normalize("ёлка"));
        assertEquals("тесты", WordleDictionaryLoader.normalize("тесты"));
    }

    @Test
    void testCheckLength() {
        WordleDictionaryLoader loader = new WordleDictionaryLoader();
        assertTrue(loader.checkLength("apple"));
        assertTrue(loader.checkLength("тесты"));
        assertFalse(loader.checkLength("hi"));
        assertFalse(loader.checkLength("электричество"));
    }
}
