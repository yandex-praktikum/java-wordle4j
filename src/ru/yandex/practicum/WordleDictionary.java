package ru.yandex.practicum;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WordleDictionary {
    private List<String> words;
    private Random random;
    private Logger logger;

    public WordleDictionary(Logger log) {
        this.random = new Random();
        words = new ArrayList<>();
        this.logger = log;
    }

    public WordleDictionary normalized() {
        logger.debug("Началась нормализация словаря. Слов: " + words.size());
        WordleDictionary wordleDictionary = new WordleDictionary(logger);
        for (String word : words) {
            word = word.toLowerCase().replace("ё", "е");
            wordleDictionary.addWord(word);
        }
        logger.debug("Нормализация завершена. Слов: " + wordleDictionary.getWords().size());
        return wordleDictionary;
    }

    public WordleDictionary filteredByLength(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Длина должна быть > 0");
        }

        WordleDictionary wordleDictionary = new WordleDictionary(logger);
        for (String word : words) {
            if (word.length() == length) {
                wordleDictionary.addWord(word);
            }
        }
        return wordleDictionary;
    }

    public List<String> getWords() {
        return new ArrayList<>(words);
    }

    public void addWord(String word) {
        if (word == null || word.isBlank()) {
            throw new IllegalArgumentException("Введена пустая строка и она не считалась как запрос подсказки");
        }
        words.add(word);
    }

    public boolean containsWord(String word) {
        return words.contains(word);
    }

    public String getRandomWord() {
        if (words.isEmpty()) {
            throw new IllegalStateException("Словарь пуст");
        }

        return words.get(random.nextInt(words.size()));
    }
}
