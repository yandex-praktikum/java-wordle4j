package ru.yandex.practicum;

import java.io.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/*
этот класс содержит в себе всю рутину по работе с файлами словарей и с кодировками
    ему нужны методы по загрузке списка слов из файла по имени файла
    на выходе должен быть класс WordleDictionary
 */
public class WordleDictionaryLoader {
    private final File commonLog;

    public WordleDictionary load(File file) {
        Set<String> words = reader(file); // Читаем файл в сет
        return new WordleDictionary(words); // Создаем и возвращаем объект другого класса
    }

    public WordleDictionaryLoader(File commonLog) {
        this.commonLog = commonLog;
    }

    Set<String> reader(File file) {
        Set<String> list = new HashSet<>();
        try (FileReader reader = new FileReader(file);
             BufferedReader br = new BufferedReader(reader)) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.length() == 5) {
                    list.add(line.toLowerCase());// отрежет слова мусор ,программа будет быстрее
                }
            }
        } catch (Exception e) {
            saveLogError(e);
        }
        return list;
    }

    private void saveLogError(Exception e) {
        String time = LocalDateTime.now().toString();
        try (FileWriter writer = new FileWriter(commonLog, true)) {
            writer.write(e + "\n");
            writer.write(time + "\n");
            writer.write(("=").repeat(20) + "\n");
        } catch (IOException ignored) {
        }
    }
}
