package ru.yandex.practicum;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class WordleDictionaryLoader {
    public WordleDictionary load(String fileName) throws DictionaryLoadException, EmptyDictionaryException {
        List<String> words = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String normalized = WordleDictionary.normalize(line);
                if (WordleDictionary.isValidWordFormat(normalized)) {
                    words.add(normalized);
                }
            }
        } catch (IOException e) {
            throw new DictionaryLoadException("Не удалось загрузить словарь из файла: " + fileName, e);
        }

        if (words.isEmpty()) {
            throw new EmptyDictionaryException("Словарь не содержит подходящих слов");
        }

        return new WordleDictionary(words);
    }
}
