package ru.yandex.practicum;

import ru.yandex.practicum.WordNotFoundException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class WordleDictionaryLoader {

    public WordleDictionary loadDictionary(String fileName) throws WordNotFoundException {
        List<String> words = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(
                new FileReader(fileName, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String normalized = normalizeWord(line);
                if (isValidWord(normalized)) {
                    words.add(normalized);
                }
            }
        } catch (IOException e) {
            throw new WordNotFoundException("Не удалось загрузить словарь из файла: " + fileName);
        }

        if (words.isEmpty()) {
            throw new WordNotFoundException("Словарь пуст или не содержит подходящих слов (5 букв)");
        }

        return new WordleDictionary(words);
    }

    private String normalizeWord(String word) {
        return word.trim()
                .toLowerCase()
                .replace('ё', 'е');
    }

    private boolean isValidWord(String word) {
        return word.length() == 5 && word.matches("[а-я]+");
    }
}