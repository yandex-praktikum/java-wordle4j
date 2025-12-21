package ru.yandex.practicum;

import java.util.*;

public class WordleDictionary {

    private final List<String> words;
    private final Set<String> wordSet;

    public WordleDictionary(List<String> words) {
        this.words = List.copyOf(words);
        this.wordSet = new HashSet<>(this.words);
    }

    public boolean contains(String word) {
        return wordSet.contains(word);
    }

    public String getRandomWord(Random rnd) {
        return words.get(rnd.nextInt(words.size()));
    }

    public List<String> allWords() {
        return words;
    }

    public int size() {
        return words.size();
    }
}
