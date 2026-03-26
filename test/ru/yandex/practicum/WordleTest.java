package ru.yandex.practicum;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.exception.EmptyDictionaryException;
import ru.yandex.practicum.exception.InvalidInputException;
import ru.yandex.practicum.exception.NoHintException;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;


class WordleTest {

    @Test
    void shouldStartGameAndExit() {
        String input = "2\n";

        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        assertDoesNotThrow(() -> Wordle.main(new String[]{}));

        String output = out.toString();

        assertTrue(output.contains("Игра началась!"));
    }

    @Test
    void shouldHandleInvalidCommandInput() throws InvalidInputException, EmptyDictionaryException, NoHintException {
        String input = "abc\n2\n"; // ошибка → потом выход

        System.setIn(new ByteArrayInputStream(input.getBytes()));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Wordle.main(new String[]{});

        String output = out.toString();

        assertTrue(output.contains("Ошибка: введите число команды"));
    }

    @Test
    void shouldShowHintOnEmptyInput() throws InvalidInputException, EmptyDictionaryException, NoHintException {
        String input = "1\n\n2\n";

        System.setIn(new ByteArrayInputStream(input.getBytes()));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Wordle.main(new String[]{});

        String output = out.toString();

        assertTrue(output.contains("Подсказка"));
    }

    @Test
    void shouldHandleInvalidWordLength() throws InvalidInputException, EmptyDictionaryException, NoHintException {
        String input = "1\nabcdef\n2\n"; // длинное слово

        System.setIn(new ByteArrayInputStream(input.getBytes()));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Wordle.main(new String[]{});

        String output = out.toString();

        assertFalse(output.contains("Критическая ошибка"));
    }

    @Test
    void shouldProcessValidWord() throws InvalidInputException, EmptyDictionaryException, NoHintException {
        String input = "1\nмирок\n2\n";

        System.setIn(new ByteArrayInputStream(input.getBytes()));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Wordle.main(new String[]{});

        String output = out.toString();

        assertTrue(output.contains("Результат вашей попытки"));
    }
}