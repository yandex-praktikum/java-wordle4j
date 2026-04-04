package ru.yandex.practicum;

import java.util.List;

/*
этот класс содержит в себе список слов List<String>
    его методы похожи на методы списка, но учитывают особенности игры
    также этот класс может содержать рутинные функции по сравнению слов, букв и т.д.
 */
public class WordleDictionary {

    private List<String> words;

    public List<String> getWords() {
        return words;
    }

    public WordleDictionary setWords(List<String> words) {
        this.words = words;
        return this;
    }


}
