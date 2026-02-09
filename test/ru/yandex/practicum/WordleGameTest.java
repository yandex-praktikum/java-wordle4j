package ru.yandex.practicum;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WordleGameTest {

    private WordleGame game;
    private Logger logger;

    @BeforeEach
    void setup() {

        logger = new PrintWriterLogger(new PrintWriter(System.out));

        WordleDictionary dict = new WordleDictionary(logger);
        dict.addWord("фавор");
        dict.addWord("виток");
        dict.addWord("взвоз");
        dict.addWord("сырец");
        dict.addWord("хохол");
        dict.addWord("холод");
        dict.addWord("конек");

        game = new WordleGame(dict, logger);
        game.setAnswer("фавор");
    }


    @Test
    void shouldWinWhenCorrectWordEntered() throws Exception {
        String result = game.makeAttempt("фавор");

        assertEquals("+++++", result);
        assertTrue(game.gameWin());
    }

    @Test
    void shouldSaveRequiredPositions() throws Exception {
        game.makeAttempt("фавор");

        List<String> possible = game.getPossibleWords();

        assertTrue(possible.contains("фавор"));
    }

    @Test
    void shouldAddForbiddenLetters() throws Exception {
        game.makeAttempt("виток");

        List<String> possible = game.getPossibleWords();

        assertFalse(possible.contains("виток"));
    }

    @Test
    void shouldRespectForbiddenPositions() throws Exception {
        game.makeAttempt("виток");

        List<String> possible = game.getPossibleWords();

        assertNotNull(possible);
    }

    @Test
    void shouldThrowIfWordNotInDictionary() {
        assertThrows(
                WordNotInDictionaryException.class,
                () -> game.makeAttempt("ааааа")
        );
    }

    @Test
    void shouldThrowIfWordAlreadyUsed() throws Exception {
        game.makeAttempt("виток");

        assertThrows(
                WordAlreadyUsedException.class,
                () -> game.makeAttempt("виток")
        );
    }

    @Test
    void gameShouldEndAfterSixSteps() throws Exception {

        ArrayList<String> w = new ArrayList<>();
        w.add("конек");
        w.add("холод");
        w.add("хохол");
        w.add("виток");
        w.add("сырец");
        w.add("взвоз");

        try {
            for (String s : w) {
                game.makeAttempt(s);
            }
        } catch (Exception ignored) {
            System.out.println(ignored.getMessage());
        }
        assertTrue(game.gameOver());
    }
}
