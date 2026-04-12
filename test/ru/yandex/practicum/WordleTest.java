package ru.yandex.practicum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class WordleTest {
    private WordleDictionary dictionary;
    private WordleGame logicOfGame;
    

    @BeforeEach
    void setUp() {
       
        File commonLog = new File("error.log");
        File file = new File("words_ru.txt");

        WordleDictionaryLoader loader = new WordleDictionaryLoader(commonLog);
        this.dictionary = loader.load(file);
        this.logicOfGame = new WordleGame(dictionary);
    }

    // 1. Проверка подсказок и словаря
    @Test
    void testGetHint() {
        Set<Character> usedChars = new HashSet<>();
        String targetWord = "балон";
        String hintWord = dictionary.getRandomWordIfEnter(usedChars, targetWord);
        
        assertNotNull(hintWord, "Подсказка не должна быть null");
        assertEquals(5, hintWord.length(), "Подсказка должна быть 5 букв");
    }

    @Test
    void testDictionarySpecifics() {
        // Проверка наличия слова и регистра
        assertTrue(dictionary.containsToDictionary("понос"), "Слово 'понос' должно быть в словаре");
        assertTrue(dictionary.containsToDictionary("ПОНОС".toLowerCase()), "Должно работать с любым регистром");
    }

    // 2. Проверка логики игры и ввода (Enter)
    @Test
    void testGenerateNewRandomWord() { 
        String input = "\nВыход\n"; 
        Scanner mockScanner = new Scanner(input);
        
        logicOfGame.runWordleGame(mockScanner); 
        String result = logicOfGame.getEnterWord(); 
        
        assertNotNull(result, "Нажатие Enter должно генерировать слово");
        assertEquals(5, result.length(), "Сгенерированное слово должно быть из 5 букв");
    }

    // 3. Проверка "Кнопок" и текстов из класса Wordle
    @Test
    void testAboutWordleGameText() {
        String rules = Wordle.aboutWordleGame();
        assertNotNull(rules);
        assertTrue(rules.contains("6 попыток"), "В правилах должна быть инфа про попытки");
    }

    @Test
    void testExitMessage() {
        assertEquals("Спасибо за игру <3", Wordle.exiteWordleGame());
    }

    @Test
    void testStartGameDoesNotCrash() {
        // Проверяем, что метод запуска игры не "падает"
        Scanner mockScanner = new Scanner("выход\n");
        assertDoesNotThrow(() -> Wordle.startWordlGame(logicOfGame, mockScanner));
    }
}