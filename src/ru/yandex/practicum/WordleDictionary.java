package ru.yandex.practicum;

import java.util.List;
import java.util.Random;


/*
этот класс содержит в себе список слов List<String>
    его методы похожи на методы списка, но учитывают особенности игры
    также этот класс может содержать рутинные функции по сравнению слов, букв и т.д.
 */
public class WordleDictionary {

    private List<String> words;

    private Random random = new Random();

    public WordleDictionary(List<String> words) {
        this.words = words;
    }

    public List<String> getWords() {
        return words;
    }

    public boolean contains(String word) {
        return words.contains(word);
    }

    public boolean wordsAnalize(String word1, String word2) {
        if (word1.length() != word2.length()) {
            return false;
        }
        return word1.equals(word2);
    }

    public String getRandomWord() {
        return words.get(random.nextInt(words.size()));
    }
}
