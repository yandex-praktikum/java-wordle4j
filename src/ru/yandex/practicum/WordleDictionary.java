package ru.yandex.practicum;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WordleDictionary {

    private final List<String> words;

    public WordleDictionary(List<String> words) {
        this.words = new ArrayList<>(words);
    }

    public String getRandomWord() {
        Random random = new Random();
        return words.get(random.nextInt(words.size()));
    }

    public boolean contains(String word) {
        return words.contains(word);
    }

    public List<String> getAllWords() {
        return new ArrayList<>(words);
    }

    public int size() {
        return words.size();
    }
}