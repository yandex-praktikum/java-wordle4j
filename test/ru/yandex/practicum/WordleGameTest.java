package ru.yandex.practicum;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import ru.yandex.practicum.exception.NoHintException;

import java.util.*;


class WordleGameTest {

    private WordleDictionary dictionary;
    private WordleGame game;

    @BeforeEach
    void setUp() {
        List<String> testWords = Arrays.asList("олень", "банан", "забор");
        dictionary = new WordleDictionary(testWords);
        game = new WordleGame("олень", 6, dictionary);
    }

    @Test
    void testMakeStep_WinCondition() throws Exception {
        game.makeStep("олень");
        assertTrue(game.isGameWin());
        assertEquals(5, game.getSteps());
    }

    @Test
    void testMakeStep() throws Exception {
        game.makeStep("банан");
        assertEquals(5, game.getSteps());
    }

    @Test
    void testCheckAttempt_AllWrong() {
        String result = game.checkAttempt("забор", "олень");
        assertEquals("---^-", result);
    }

    @Test
    void testCheckAttempt_AllCorrect() {
        String result = game.checkAttempt("олень", "олень");
        assertEquals("+++++", result);
    }

    @Test
    void testCheckAttempt_AlmostCorrect() {
        String result = game.checkAttempt("олени", "олень");
        assertEquals("++++-", result);
    }

    @Test
    void testGetHint_NoMatchingWords() throws Exception {

        List<String> emptyWords = Arrays.asList("пожар", "слово");
        WordleDictionary emptyDictionary = new WordleDictionary(emptyWords);
        WordleGame gameWithEmptyDict = new WordleGame("забор", 6, emptyDictionary);

        gameWithEmptyDict.makeStep("носок");

        assertThrows(NoHintException.class, () -> {
            gameWithEmptyDict.getHint();
        });
    }

    @Test
    public void testGetHint_ReturnsBestWord() throws Exception {
        game.makeStep("забег"); // Устанавливаем ограничения

        String hint = game.getHint();
        assertNotNull(hint);
        assertTrue(dictionary.getWords().contains(hint));
    }

    @Test
    void testMatches_ExcludedLetters() throws Exception {
        game.makeStep("ггггг");

        java.lang.reflect.Method method = WordleGame.class.getDeclaredMethod("matches", String.class);
        method.setAccessible(true);

        assertFalse((Boolean) method.invoke(game, "ггггг"));
        assertTrue((Boolean) method.invoke(game, "забор"));
    }


    @Test
    public void testMatches_WrongPositions() throws Exception {
        game.makeStep("ORANG");

        java.lang.reflect.Method method = WordleGame.class.getDeclaredMethod("matches", String.class);
        method.setAccessible(true);

        assertTrue((Boolean) method.invoke(game, "APPLE"));
    }

}
