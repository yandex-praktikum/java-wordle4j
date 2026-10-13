package ru.yandex.practicum;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WordleHinterTest {

    private PrintWriter printWriter = new PrintWriter(new StringWriter());

    @Test
    @DisplayName("При множестве подсказок возвращается только одна")
    void generateSuggestionWhenDictionaryIsFullOfWords() {
        String answer = "камус";
        WordleDictionary dictionary = new WordleDictionary(List.of("камас", "камса", "каска", "скука", "камус"));

        WordleHinter hinter = new WordleHinter(dictionary, new HashSet<>(), new HashSet<>(), printWriter);
        String suggestion = hinter.generateSuggestion(answer);

        assertEquals(5, suggestion.length());
    }

    @Test
    @DisplayName("При наличии одной подсказки возвращается только одна")
    void generateSuggestionWhenDictionaryContainsOneWord() {
        String answer = "камус";
        WordleDictionary dictionary = new WordleDictionary(List.of("камас"));
        WordleHinter hinter = new WordleHinter(dictionary, new HashSet<>(), new HashSet<>(), printWriter);
        String suggestion = hinter.generateSuggestion(answer);

        assertEquals(5, suggestion.length());
        assertEquals("камас", suggestion);
    }

    @Test
    @DisplayName("При отсутствии подсказок выводится предупреждение")
    void generateSuggestionWhenDictionaryHasNoWords() {
        String answer = "камус";
        WordleDictionary dictionary = new WordleDictionary(List.of());
        WordleHinter hinter = new WordleHinter(dictionary, new HashSet<>(), new HashSet<>(), printWriter);
        String suggestion = hinter.generateSuggestion(answer);

        assertEquals("Вы использовали все доступные подсказки", suggestion);
    }

    @Test
    @DisplayName("Если все слова уже подсказаны, то выводится предупреждение")
    void generateSuggestionWhenAllWordsInAlreadySuggested() {
        String answer = "камус";
        WordleDictionary dictionary = new WordleDictionary(List.of("камас", "камса", "каска", "скука", "камус"));
        WordleHinter hinter = new WordleHinter(dictionary,
                new HashSet<>(List.of("камас", "камса", "каска", "скука", "камус")), new HashSet<>(), printWriter);
        String suggestion = hinter.generateSuggestion(answer);

        assertEquals("Вы использовали все доступные подсказки", suggestion);
    }

    @Test
    @DisplayName("Если все слова пользователь уже ввел, то выводится предупреждение")
    void generateSuggestionWhenAllWordsInUserInput() {
        String answer = "камус";
        WordleDictionary dictionary = new WordleDictionary(List.of("камас", "камса", "каска", "скука"));
        WordleHinter hinter = new WordleHinter(dictionary,
                new HashSet<>(), new HashSet<>(List.of("камас", "камса", "каска", "скука")), printWriter);
        String suggestion = hinter.generateSuggestion(answer);

        assertEquals("Вы использовали все доступные подсказки", suggestion);
    }

    @Test
    @DisplayName("Когда слово содержит буквы, которые подходят частично")
    void generateSymbolicHintWhenWordContainDifferentCharacters() {
        String answer = "герой";
        String userInput = "гонец";
        WordleDictionary dictionary = new WordleDictionary(List.of());
        WordleHinter hinter = new WordleHinter(dictionary, new HashSet<>(), new HashSet<>(), printWriter);
        String suggestion = hinter.generateSymbolicHint(userInput, answer);
        assertEquals("+^-^-", suggestion);
    }

    @Test
    @DisplayName("Когда слово содержит неподходящие буквы")
    void generateSymbolicHintWhenWordContainNoSimilarCharacters() {
        String answer = "герой";
        String userInput = "аллах";
        WordleDictionary dictionary = new WordleDictionary(List.of());
        WordleHinter hinter = new WordleHinter(dictionary, new HashSet<>(), new HashSet<>(), printWriter);
        String suggestion = hinter.generateSymbolicHint(userInput, answer);
        assertEquals("-----", suggestion);
    }

}