package ru.yandex.practicum;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Wordle {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try (PrintWriter writer = new PrintWriter(new FileWriter("wordle.log"))) {
            WordleDictionaryLoader wordleDictionaryLoader = new WordleDictionaryLoader(writer);

            WordleDictionary dictionary = wordleDictionaryLoader.loadWords(args[0]);

            WordleGame game = new WordleGame(dictionary, writer);

            writer.println("Игра началась!");

            boolean answer = false;

            while (game.getSteps() < 6) {
                try {
                    String attempts = scanner.nextLine().toLowerCase().replace("ё", "е");

                    if (attempts.isEmpty()) {
                        String hint = game.hintWord();
                        writer.println("Подсказка: " + hint);
                        System.out.println(hint);
                        continue;
                    }

                    if (attempts.length() != 5) {
                        System.out.println("Неправильное количество символов.");
                        continue;
                    }

                    answer = game.checkAnswer(attempts);

                    writer.println("Попытка: " + attempts);

                    String result = game.checkLetters(attempts);

                    writer.println("Результат: " + result);
                    System.out.println(result);

                    if (answer) {
                        System.out.println("Вы угадали слово!");
                        break;
                    }

                } catch (WordNotFoundInDictionary e) {
                    writer.println("Ошибка: " + e.getMessage());
                    System.out.println(e.getMessage());
                }
            }

            writer.println("Загаданное слово: " + game.getAnswer());

            if (!answer) {
                writer.println("Попытки закончились");
                System.out.println("Попытки закончились.");
            }

        } catch (Exception e) {
            System.out.println("Произошла ошибка: " + e.getMessage());
        }
    }
}