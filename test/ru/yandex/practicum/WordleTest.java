package ru.yandex.practicum;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class WordleGameTest {

    @Test
    void testGenerateHintExactMatch() {
        WordleDictionary dict = new WordleDictionary(List.of("герой"));
        WordleGame game = new WordleGame(dict);
        try {
            java.lang.reflect.Field field = WordleGame.class.getDeclaredField("answer");
            field.setAccessible(true);
            field.set(game, "герой");
        } catch (Exception e) {
            fail("Не удалось подменить answer");
        }
        String hint = null;
        try {
            hint = game.makeGuess("герой");
        } catch (Exception e) {
            fail("Ошибка при вызове makeGuess");
        }
        assertEquals("+++++", hint);
    }

    @Test
    void testIsWordGuessed() {
        WordleDictionary dict = new WordleDictionary(List.of("герой"));
        WordleGame game = new WordleGame(dict);
        try {
            java.lang.reflect.Field field = WordleGame.class.getDeclaredField("answer");
            field.setAccessible(true);
            field.set(game, "герой");
        } catch (Exception e) {
            fail("Не удалось подменить answer");
        }
        assertFalse(game.isWordGuessed());
        try {
            game.makeGuess("герой");
        } catch (Exception e) {
            fail("Ошибка при вызове makeGuess");
        }
        assertTrue(game.isWordGuessed());
    }

    @Test
    void testGetHintWordNotRepeats() {
        WordleDictionary dict = new WordleDictionary(List.of("герой", "герои", "героя", "город"));
        WordleGame game = new WordleGame(dict);
        try {
            java.lang.reflect.Field field = WordleGame.class.getDeclaredField("answer");
            field.setAccessible(true);
            field.set(game, "герой");
        } catch (Exception e) {
            fail("Не удалось подменить answer");
        }
        String firstHint = game.getHintWord();
        String secondHint = game.getHintWord();
        if (firstHint != null && secondHint != null) {
            assertNotEquals(firstHint, secondHint, "Подсказки не должны повторяться");
        }
    }
}