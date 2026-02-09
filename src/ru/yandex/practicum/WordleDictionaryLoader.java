package ru.yandex.practicum;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class WordleDictionaryLoader {
    private Logger logger;

    public WordleDictionaryLoader(Logger logger) {
        this.logger = logger;
    }

    public WordleDictionary load(String fileName) throws DictionaryLoadException {

        WordleDictionary wordleDictionary = new WordleDictionary(logger);
        logger.info("Началась загрузка словаря из " + fileName);
        try (FileReader fileReader = new FileReader(fileName, StandardCharsets.UTF_8);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                wordleDictionary.addWord(line);
            }
            logger.info("Загрузка словаря завершена");
        } catch (IOException e) {
            throw new DictionaryLoadException(
                    "Ошибка загрузки словаря из файла: " + fileName, e
            );
        }
        return wordleDictionary;
    }
}
