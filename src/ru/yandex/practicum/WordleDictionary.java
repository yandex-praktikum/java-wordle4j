package ru.yandex.practicum;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
этот класс содержит в себе список слов List<String>
    его методы похожи на методы списка, но учитывают особенности игры
    также этот класс может содержать рутинные функции по сравнению слов, букв и т.д.
 */
public class WordleDictionary {

    private List<String> words;

    private Set<String> wordsForCheckingUserInput;

    public WordleDictionary(List<String> words) {
        this.words = words;
        this.wordsForCheckingUserInput = new HashSet<>(words);
    }

    public List<String> getWords() {
        return words;
    }

    public Set<String> getWordsForCheckingUserInput() {
        return wordsForCheckingUserInput;
    }
}
