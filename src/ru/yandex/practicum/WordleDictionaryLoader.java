package ru.yandex.practicum;

import ru.yandex.practicum.exception.EmptyDictionaryException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


public class WordleDictionaryLoader {

    public WordleDictionary load(String path) throws IOException, EmptyDictionaryException {
        List<String> lines = Files.readAllLines(Path.of(path));

        List<String> result = new ArrayList<>();

        for (String line : lines) {
            String normalized = normalize(line);

            if (checkLength(normalized)) {
                result.add(normalized);
            }
        }

        if (result.isEmpty()) {
            throw new EmptyDictionaryException();
        }

        return new WordleDictionary(result);
    }

    public static String normalize(String word) {
        return word
                .trim()
                .toLowerCase()
                .replace('ё', 'е');
    }

    public boolean checkLength(String word) {
        return word.length() == 5;
    }
}