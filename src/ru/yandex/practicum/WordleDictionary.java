package ru.yandex.practicum;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;

public class WordleDictionary {
    private static final int WORD_LENGTH = 5;

    private final Set<String> words = new LinkedHashSet<>();
    public WordleDictionary(List<String> sourceWords, PrintWriter log) {
        if (sourceWords == null || log == null) {
            throw new IllegalArgumentException("Слова и журнал обязательны");
        }
        for (String word : sourceWords) {
            addWord(word);
        }
        log.println("Загружено слов: " + words.size());
    }

    public static String normalizeWord(String word) {
        if (word == null) {
            return "";
        }

        String normalized = word.trim().toLowerCase(Locale.ROOT).replace('ё', 'е');
        for (int i = 0; i < normalized.length(); i++) {
            if (normalized.charAt(i) < 'а' || normalized.charAt(i) > 'я') {
                return "";
            }
        }
        return normalized;
    }

    public void addWord(String word) {
        String normalized = normalizeWord(word);
        if (normalized.length() == WORD_LENGTH) {
            words.add(normalized);
        }
    }

    public boolean contains(String word) {
        return words.contains(normalizeWord(word));
    }

    public List<String> getWords() {
        return Collections.unmodifiableList(new ArrayList<>(words));
    }

    public int size() {
        return words.size();
    }

    public String getRandomWord() {
        if (words.isEmpty()) {
            throw new IllegalStateException("Словарь пуст");
        }
        List<String> wordList = new ArrayList<>(words);
        return wordList.get(new Random().nextInt(wordList.size()));
    }
}
