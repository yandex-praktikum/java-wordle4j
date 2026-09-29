package ru.yandex.practicum;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class WordleDictionaryLoader {

    private PrintWriter writer;

    public WordleDictionaryLoader(PrintWriter writer) {
        this.writer = writer;
    }

    public WordleDictionary loadWords(String fileName) throws IOException {
        ArrayList<String> words = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(
                new FileReader(fileName, Charset.forName("UTF-8")))) {

            String word;

            while ((word = reader.readLine()) != null) {
                word = word.toLowerCase().replace("ё", "е");
                words.add(word);
            }
        }

        writer.println("Словарь загружен. Количество слов: " + words.size());

        return new WordleDictionary(words);
    }
}