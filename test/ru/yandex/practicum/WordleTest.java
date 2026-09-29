package ru.yandex.practicum;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

class WordleTest {
    @Test
    void testCheckAnswer () throws WordNotFoundInDictionary {

        WordleDictionary dictionary = new WordleDictionary(List.of("акула" , "арбуз"));
        WordleGame game = new WordleGame(dictionary, new PrintWriter(System.out));
        assertTrue(game.checkAnswer(game.getAnswer()));

        String wrongWord;

        if (dictionary.getWord(0).equals(game.getAnswer())) {
            wrongWord = dictionary.getWord(1);
        } else {
            wrongWord = dictionary.getWord(0);
        }
        assertFalse(game.checkAnswer(wrongWord));
    }

    @Test
    void WordNotFoundInDictionary() {
        WordleDictionary dictionary = new WordleDictionary(List.of("акула" , "арбуз"));
        WordleGame game = new WordleGame(dictionary, new PrintWriter(System.out));
        assertThrows(WordNotFoundInDictionary.class, () -> game.checkAnswer("лимон"));
    }

    @Test
    void WordCheckCorrectLetters()  {
        WordleDictionary dictionary = new WordleDictionary(List.of("арбуз"));
        WordleGame game = new WordleGame(dictionary, new PrintWriter(System.out));
        assertEquals("+++++", game.checkLetters("арбуз"));
    }

    @Test
    void WordCheckWrongPosition() {
        WordleDictionary dictionary = new WordleDictionary(List.of("актер"));
        WordleGame game = new WordleGame(dictionary, new PrintWriter(System.out));
        assertEquals("^^+++", game.checkLetters("катер"));
    }

    @Test
    void WordCheckAbsentLetter() {
        WordleDictionary dictionary = new WordleDictionary(List.of("актер"));
        WordleGame game = new WordleGame(dictionary, new PrintWriter(System.out));
        assertEquals("^---+", game.checkLetters("топор"));
    }

    @Test
    void TestHintWord() {
        WordleDictionary dictionary = new WordleDictionary(List.of("актер"));
        WordleGame game = new WordleGame(dictionary,  new PrintWriter(System.out));
        String hint = game.hintWord();
        assertFalse(hint.isEmpty());
        assertTrue(dictionary.getWords().contains(hint));
        assertEquals(5, hint.length());
    }

    @Test
    void TestCheckHintAfterAttempt() throws WordNotFoundInDictionary {
        WordleDictionary dictionary = new WordleDictionary(List.of("актер", "акула"));
        WordleGame game = new WordleGame(
                dictionary,
                "актер",
                new PrintWriter(System.out)
        );

        game.checkAnswer("акула");
        String hint = game.hintWord();

        assertEquals('а', hint.charAt(0));
        assertFalse(hint.contains("с"));
    }

    @Test
    void TestCheckHintWrongPosition() throws WordNotFoundInDictionary {
        WordleDictionary dictionary = new WordleDictionary(List.of("актер","катер"));
        WordleGame game = new WordleGame(dictionary, "актер", new PrintWriter(System.out));

        game.checkAnswer("катер");
        String hint = game.hintWord();

        assertTrue(hint.contains("к"));
        assertNotEquals('к', hint.charAt(0));
    }

    @Test
    void testContainsWord() {
        WordleDictionary dictionary =
                new WordleDictionary(List.of("актер", "арбуз"));

        assertTrue(dictionary.containsWord("актер"));
        assertFalse(dictionary.containsWord("лимон"));
    }

    @Test
    void TestSize() {
        WordleDictionary dictionary = new WordleDictionary(List.of("актер"));
        WordleGame game = new WordleGame(dictionary,  new PrintWriter(System.out));

        assertEquals(1, dictionary.size());
    }

    @Test
    void TestGetWords() {
        WordleDictionary dictionary = new WordleDictionary(List.of("актер"));
        List<String> words = new ArrayList<>();
        words.add("актер");
        assertEquals(words, dictionary.getWords());
    }

    @Test
    void WordleDictionaryLoader() throws IOException {
        WordleDictionaryLoader loader = new WordleDictionaryLoader(new PrintWriter(System.out));
        Path path = Files.createTempFile("dictionary", ".txt");
        Files.write(path, List.of("актер", "ЁЛКА"));
        path.toString();
        WordleDictionary dictionary = loader.loadWords(path.toString());
        assertTrue(dictionary.containsWord("актер"));
        assertTrue(dictionary.containsWord("елка"));
    }

    @Test
    void TestIOException () {
        WordleDictionaryLoader loader = new WordleDictionaryLoader(new PrintWriter(System.out));
        assertThrows(IOException.class, () -> loader.loadWords("несуществующи_файл.txt"));
    }

    @Test
    void WordleDictionarySize() {
        WordleDictionary dictionary =
                new WordleDictionary(List.of("актер", "арбуз"));
        assertEquals(2 , dictionary.size());
    }

    @Test
    void InvalidWordDoesNotIncreaseSteps() {
        WordleDictionary dictionary = new WordleDictionary(List.of("актер"));
        WordleGame game = new WordleGame(dictionary, new PrintWriter(System.out));
        assertThrows(WordNotFoundInDictionary.class, () -> game.checkAnswer("катер"));
        assertEquals(0, game.getSteps());
    }

    @Test
    void ValidWordIncreasesSteps() throws WordNotFoundInDictionary {
        WordleDictionary dictionary = new WordleDictionary(List.of("актер"));
        WordleGame game = new WordleGame(dictionary, new PrintWriter(System.out));
        game.checkAnswer("актер");
        assertEquals(1, game.getSteps());
    }

    @Test
    void HintNotIncreaseNumberOfAttempts() {
        WordleDictionary dictionary = new WordleDictionary(List.of("актер"));
        WordleGame game = new WordleGame(dictionary, new PrintWriter(System.out));
        game.hintWord();
        assertEquals(0, game.getSteps());

    }
}
