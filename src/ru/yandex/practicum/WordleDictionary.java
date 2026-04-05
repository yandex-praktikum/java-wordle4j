package ru.yandex.practicum;

import java.util.List;

/*
этот класс содержит в себе список слов List<String>
    его методы похожи на методы списка, но учитывают особенности игры
    также этот класс может содержать рутинные функции по сравнению слов, букв и т.д.
 */
public class WordleDictionary {

    private Set<String> words;

    public Set<String> getWords() {
        return words;
    }

    public WordleDictionary (Set<String> words) {
        this.words = words;
    }

    public boolean containsToDictionary(String word) {
        if (word == null || words == null) {
            return false;
        }
        // Используем toLowerCase(), чтобы проверка не зависела от регистра
        return words.contains(word.toLowerCase());
    }


}
