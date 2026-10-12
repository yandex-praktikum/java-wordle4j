package ru.yandex.practicum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class WordleTest {

    @Test
    void normalizeShouldReplaceYoAndLowerCase() {
        assertEquals("ежик", WordleDictionary.normalize(" Ёжик "));
    }

    @Test
    void analyzeGuessShouldReturnCorrectHint() {
        assertEquals("+^-^-", WordleDictionary.analyzeGuess("гонец", "герой"));
    }

    @Test
    void analyzeGuessShouldHandleDuplicateLetters() {
        assertEquals("^^+-^", WordleDictionary.analyzeGuess("бабка", "аббат"));
    }

    private WordleGame game;

    @BeforeEach
    void setUp() throws Exception {
        WordleDictionary dictionary = new WordleDictionary(List.of("герой", "гонец", "горец", "город"));
        game = new WordleGame(dictionary);
    }

    @Test
    void gameShouldHaveSixAttemptsInitially() {
        assertEquals(6, game.getAttemptsLeft());
    }

    @Test
    void invalidWordShouldNotSpendAttempt() throws Exception {
        int before = game.getAttemptsLeft();
        assertThrows(WordNotFoundInDictionary.class, () -> game.makeGuess("абвгд"));
        assertEquals(before, game.getAttemptsLeft());
    }

}
