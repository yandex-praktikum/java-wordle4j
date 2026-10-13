package ru.yandex.practicum;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

/*
в главном классе нам нужно:
    создать лог-файл (он должен передаваться во все классы)
    создать загрузчик словарей WordleDictionaryLoader
    загрузить словарь WordleDictionary с помощью класса WordleDictionaryLoader
    затем создать игру WordleGame и передать ей словарь
    вызвать игровой метод в котором в цикле опрашивать пользователя и передавать информацию в игру
    вывести состояние игры и конечный результат
 */
public class Wordle {

    public static void main(String[] args) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("wordle.log"))) {
            Set<String> alreadySuggested = new HashSet<>();
            Set<String> userInput = new HashSet<>();
            WordleDictionaryLoader wordleDictionaryLoader = new WordleDictionaryLoader(writer);
            WordleDictionary dictionary = wordleDictionaryLoader.loadDictionary("words_ru.txt");
            WordleHinter hinter = new WordleHinter(dictionary, alreadySuggested, userInput, writer);
            WordleValidator wordleValidator = new WordleValidator(dictionary, writer);
            WordleGame wordleGame = new WordleGame(dictionary, userInput, alreadySuggested, hinter, wordleValidator, writer);
            wordleGame.processGame();
        } catch (IOException e) {
            System.out.println("Ошибка при создании компонента записи в лог");
        }

    }

}
