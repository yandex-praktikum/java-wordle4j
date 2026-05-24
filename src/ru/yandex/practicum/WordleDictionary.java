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

    public WordleDictionary(List<String> words) {
        this.words = words;
    }

    public int size() {
        return words.size();
    }

    public String normalizeWord(String word) { // приведение слова к ок регистру и буквам е
        word = word.toLowerCase(); // регистр
        word = word.trim(); // обрезать
        word = word.replace('ё','е'); // буквы
        return word;
    }

    public boolean contains(String word) { // есть ли слово в словаре? + привожу к регистру
        word = normalizeWord(word);
        return words.contains(word);

    }

    public String getRandomWord() { // получение случайного слова из словаря
        Random random = new Random();
        int index = random.nextInt(words.size());
        return words.get(index);
    }


}


