package ru.yandex.practicum;

import java.util.*;

/*
этот класс содержит в себе список слов List<String>
    его методы похожи на методы списка, но учитывают особенности игры
    также этот класс может содержать рутинные функции по сравнению слов, букв и т.д.
 */
public class WordleDictionary {

    private final Set<String> words;

    public WordleDictionary(Set<String> words) {
        this.words = words;
    }

    public boolean containsToDictionary(String word) {
        return words.contains(word);
    }

    public String getRandomWord() {
        List<String> wordsForRandom = new ArrayList<>(words);
        int randIndex = new Random().nextInt(wordsForRandom.size());
        return wordsForRandom.get(randIndex);
    }

    public String getRandomWordIfEnter(Set<Character> listOfChar, String targetWord) {

        List<String> matches = new ArrayList<>();

        String randomWord = getRandomWord();
        while (randomWord.equals(targetWord)) {
            randomWord = getRandomWord();
        }
        if (listOfChar == null) {
            return randomWord;
        }
        for (String word : words) {
            if (word.equals(targetWord)) continue;

            boolean allMatch = true;
            for (char c : listOfChar) {
                if (word.indexOf(c) == -1) {
                    allMatch = false;
                    break;
                }
            }

            if (allMatch) {
                matches.add(word); // Нашли? В корзину его
            }
        }

        // Если нашли подходящие слова — выбираем случайное из них
        if (!matches.isEmpty()) {
            return matches.get(new Random().nextInt(matches.size()));
        }

        return randomWord; // Если совсем ничего не подошло
    }
}
