package ru.yandex.practicum;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/*
этот класс содержит в себе всю рутину по работе с файлами словарей и с кодировками
    ему нужны методы по загрузке списка слов из файла по имени файла
    на выходе должен быть класс WordleDictionary
 */
public class WordleDictionaryLoader {

    private PrintWriter printWriter;

    public WordleDictionaryLoader(PrintWriter writer) {
        this.printWriter = writer;
    }

    public WordleDictionary loadDictionary(String pathToDictionary) {
        Path path = Paths.get(pathToDictionary);
        List<String> words;

        try {
            words = Files.readAllLines(path)
                    .stream()
                    .filter(word -> word.length() == 5)
                    .map(String::toLowerCase)
                    .map(String::trim)
                    .map(word -> word.replace('ё', 'e'))
                    .toList();
        } catch (IOException e) {
            printWriter.println("Ошибка при загрузке словаря");
            throw new RuntimeException(e);
        }

        return new WordleDictionary(words);
    }

}
