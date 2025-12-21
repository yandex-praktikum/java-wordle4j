package ru.yandex.practicum;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class WordleDictionaryLoader {

    private final PrintWriter log;

    public WordleDictionaryLoader(PrintWriter log) {
        this.log = log;
    }

    public WordleDictionary load(String path) throws DictionaryLoadException, EmptyDictionaryException {
        LinkedHashSet<String> unique = new LinkedHashSet<>();

        try (BufferedReader br = Files.newBufferedReader(Path.of(path), StandardCharsets.UTF_8)) {
            String line;
            while ((line = br.readLine()) != null) {
                String w = normalize(line);

                if (w.isEmpty()) continue;
                if (w.length() != 5) continue;
                if (!isRussianLetters(w)) continue;

                unique.add(w);
            }
        } catch (IOException e) {
            log.println("Dictionary load error: " + e.getMessage());
            throw new DictionaryLoadException("Не удалось загрузить словарь: " + path, e);
        }

        if (unique.isEmpty()) {
            throw new EmptyDictionaryException("Словарь пуст или не содержит 5-буквенных русских слов: " + path);
        }

        List<String> words = new ArrayList<>(unique);
        log.println("Dictionary loaded: " + words.size() + " words (5 letters)");

        return new WordleDictionary(words);
    }

    private String normalize(String s) {
        return s.trim().toLowerCase().replace('ё', 'е');
    }

    private boolean isRussianLetters(String s) {
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c < 'а' || c > 'я') return false;
        }
        return true;
    }
}
