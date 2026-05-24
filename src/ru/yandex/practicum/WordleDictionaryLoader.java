package ru.yandex.practicum;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/*
этот класс содержит в себе всю рутину по работе с файлами словарей и с кодировками
    ему нужны методы по загрузке списка слов из файла по имени файла
    на выходе должен быть класс WordleDictionary

    1) прочесть файл
    2) собрать слова в List
    3) создать объект класса WordleDictionary

 */

public class WordleDictionaryLoader {

        public WordleDictionary load(String fileName) {

            List<String> words = new ArrayList<>();// создание списка слов из словаря

            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) { //

                String line; // объявление переменной строки из словаря которую читать буду одна за другой

                while ((line = reader.readLine()) != null) { // цикл: читаю строчку и если не равна нулю

                    words.add(line); // добавляю в список слов
                }

            } catch (IOException e) {
                e.printStackTrace();
                }


            return new WordleDictionary(words); // возвращаю готовый словарь подходящих под условия игры слов


            // BufferedReader
            // FileReader
            // UTF-8
            // читать файл построчно
        }
}

