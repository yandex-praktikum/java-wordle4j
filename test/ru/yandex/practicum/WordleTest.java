package ru.yandex.practicum;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WordleTest {

    @Test
    public void shouldNormalizeWord() { // проверка на нормализаицю слова: без пробелов, одного регистра, ё в е

        WordleDictionary dictionary =
                new WordleDictionary(List.of("привет")); // создание объекта

        String result = dictionary.normalizeWord(" ЁлКа "); // вызов метода

        assertEquals("елка", result); // проверка
    }

    @Test
    public void shouldContain() { // что находится слово в словаре
                WordleDictionary dictionary = new WordleDictionary((List.of("гонец")));
        boolean result = dictionary.contains("гонец");

        assertTrue(result);
    }

    @Test
    public void shouldGetRandomWord() { // что какое-то слово все-таки возвращается
           WordleDictionary dictionary = new WordleDictionary((List.of("гараж")));
            String result = dictionary.getRandomWord();

            assertNotNull(result);

    }

    @Test
    public void shouldGameOverIf5() { // после 5 норм попыток игра завершается
        WordleDictionary dictionary = new WordleDictionary(List.of("сдача","автор","карма","вздор","лимон"));
        WordleGame game = new WordleGame(dictionary);
        game.makeMove("сдача");
        game.makeMove("автор");
        game.makeMove("карма");
        game.makeMove("вздор");
        game.makeMove("лимон");

        assertTrue(game.gameOver());

    }

    @Test
    public void shouldMakeMove() { // что игра делает ход и делает подсказку

        WordleDictionary dictionary =
                new WordleDictionary(List.of("автор"));

        WordleGame game = new WordleGame(dictionary);

        String result = game.makeMove("автор");

        assertNotNull(result);
    }

    @Test
    public void shouldGenerateHint() { // возврщает подсказку, а не нулевое значение
        WordleDictionary dictionary = new WordleDictionary(List.of("солнце"));

        WordleGame game = new WordleGame(dictionary);
        String hint = game.generateHint("солнце");

        assertNotNull(hint);
    }

}
