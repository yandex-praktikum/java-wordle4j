package ru.yandex.practicum;

import static org.junit.jupiter.api.Assertions.*;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

class WordleTest {
    private PrintWriter createLog() {
        return new PrintWriter(System.out, true);
    }

    @Test
    void loaderNormalizesAndFiltersWords() throws Exception {
        Path file = Files.createTempFile("wordle-dictionary", ".txt");
        try {
            Files.write(file, Arrays.asList("Алмаз", "сосна", "поиск", "мир", "поиск", "СЁДЛА", "abcde"),
                    StandardCharsets.UTF_8);

            WordleDictionary dictionary = new WordleDictionaryLoader(createLog()).loadFromFile(file.toString());

            assertTrue(dictionary.contains("алмаз"));
            assertTrue(dictionary.contains("сосна"));
            assertTrue(dictionary.contains("седла"));
            assertFalse(dictionary.contains("мир"));
            assertFalse(dictionary.contains("abcde"));
            assertEquals(4, dictionary.size());
        } finally {
            Files.deleteIfExists(file);
        }
    }

    @Test
    void correctGuessWinsAndConsumesOneAttempt() throws Exception {
        WordleDictionary dictionary = new WordleDictionary(
                Arrays.asList("слово", "книга", "место", "песок"), createLog());
        WordleGame game = new WordleGame(dictionary, "слово", createLog());

        assertEquals("+++++", game.makeMoveAndGetHint("слово"));
        assertTrue(game.isSolved());
        assertTrue(game.isGameOver());
        assertEquals(5, game.getAttemptsLeft());
    }

    @Test
    void invalidGuessesDoNotConsumeAttempts() {
        WordleDictionary dictionary = new WordleDictionary(Arrays.asList("слово", "книга"), createLog());
        WordleGame game = new WordleGame(dictionary, "слово", createLog());

        assertThrows(InvalidWordException.class, () -> game.makeMoveAndGetHint("плут"));
        assertThrows(WordNotFoundInDictionaryException.class, () -> game.makeMoveAndGetHint("арбуз"));
        assertEquals(WordleGame.MAX_STEPS, game.getAttemptsLeft());
    }

    @Test
    void repeatedGuessIsRejectedWithoutConsumingAttempt() throws Exception {
        WordleDictionary dictionary = new WordleDictionary(Arrays.asList("слово", "книга"), createLog());
        WordleGame game = new WordleGame(dictionary, "слово", createLog());
        game.makeMoveAndGetHint("книга");

        assertThrows(WordAlreadyUsedException.class, () -> game.makeMoveAndGetHint("книга"));
        assertEquals(WordleGame.MAX_STEPS - 1, game.getAttemptsLeft());
    }

    @Test
    void hintCountsRepeatedLettersCorrectly() {
        assertEquals("+++++", WordleGame.buildHint("сосна", "сосна"));
        assertEquals("+-+--", WordleGame.buildHint("ссссс", "сосна"));
        assertEquals("+^-^-", WordleGame.buildHint("гонец", "герой"));
    }

    @Test
    void suggestionMatchesPreviousHintsAndIsNotRepeated() throws Exception {
        WordleDictionary dictionary = new WordleDictionary(
                Arrays.asList("слово", "книга", "место", "песок", "сосна"), createLog());
        WordleGame game = new WordleGame(dictionary, "слово", createLog());
        String guess = "книга";
        String hint = game.makeMoveAndGetHint(guess);

        String suggestion = game.suggestWord();
        String nextSuggestion = game.suggestWord();

        assertNotEquals(guess, suggestion);
        assertEquals(hint, WordleGame.buildHint(guess, suggestion));
        assertNotEquals(suggestion, nextSuggestion);
        assertEquals(hint, WordleGame.buildHint(guess, nextSuggestion));
    }

    @Test
    void gameEndsAfterSixValidGuesses() throws Exception {
        WordleDictionary dictionary = new WordleDictionary(
                Arrays.asList("слово", "книга", "песок", "место", "сосна", "алмаз", "поиск"), createLog());
        WordleGame game = new WordleGame(dictionary, "слово", createLog());

        for (String guess : Arrays.asList("книга", "песок", "место", "сосна", "алмаз", "поиск")) {
            game.makeMoveAndGetHint(guess);
        }

        assertTrue(game.isGameOver());
        assertEquals(0, game.getAttemptsLeft());
        assertThrows(IllegalStateException.class, () -> game.makeMoveAndGetHint("слово"));
    }
}
