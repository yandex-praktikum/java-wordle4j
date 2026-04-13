package ru.yandex.practicum;

import java.io.File;
import java.util.*;

/*
в главном классе нам нужно:
    создать лог-файл (он должен передаваться во все классы) ####
    создать загрузчик словарей WordleDictionaryLoader ####
    загрузить словарь WordleDictionary с помощью класса WordleDictionaryLoader ####
    затем создать игру WordleGame и передать ей словарь
    вызвать игровой метод в котором в цикле опрашивать пользователя и передавать информацию в игру
    вывести состояние игры и конечный результат
 */
public class Wordle {

    public static void main(String[] args) {
        File commonLog = new File("error.log");
        File file = new File("words_ru.txt");

        WordleDictionaryLoader loader = new WordleDictionaryLoader(commonLog);
        WordleDictionary dictionary = loader.load(file);
        WordleGame logicOfGame = new WordleGame(dictionary);

        Scanner sc = new Scanner(System.in);

        System.out.println("Игра");
        while (true) {
            menu();
            String enter = sc.nextLine();
            switch (enter.trim().toLowerCase()) {
                case "начать" -> startWordlGame(logicOfGame, sc);
                case "сведения" -> System.out.println(aboutWordleGame());
                case "выход" -> {
                    System.out.println(exiteWordleGame());
                    return;
                }
                default -> System.out.println("такого варианта нет. :( ");

            }
        }

    }

    private static void menu() {
        StringBuilder mainSB = new StringBuilder();

        mainSB.append("Введите, что вы хотите сделать:\n")
                .append("-> Начать <- для запуска игры\n")
                .append("-> Сведения <- чтобы узнать правила\n")
                .append("-> Выход <- чтобы завершить игру");

        System.out.println(mainSB);
    }

    public static void startWordlGame(WordleGame logicOfGame, Scanner sc) {
        logicOfGame.runWordleGame(sc);
    }

    public static String aboutWordleGame() {
        StringBuilder aboutSB = new StringBuilder();

        aboutSB.append("===Правила игры===\n")
                .append("За 6 попыток угадать слово.\n")
                .append("Можно использовать только слова из библиотеки\n")
                .append("-  им отмечается буква, которой НЕТ в загаданном слове\n")
                .append("+ этим символом отмечается буква, которая находится на правильной позиции;\n")
                .append("^ так отмечается буква, которая ЕСТЬ, но находится в другом месте.\n")
                .append("===Управление===\n")
                .append("клавиша ENTER в пустой строке -> подсказка от компьютера\n")
                .append("напишите  Выход  -> завершает игру\n")
                .append("\n");
        return aboutSB.toString();

    }

    public static String exiteWordleGame() {
        return "Спасибо за игру <3";
    }
}

