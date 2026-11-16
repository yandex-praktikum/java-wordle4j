package ru.yandex.practicum;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Wordle {
    private static final String DICTIONARY_FILE = "words_ru.txt";
    private static final String LOG_FILE = "wordle.log";

    public static void main(String[] args) {
        try (PrintWriter log = new PrintWriter(new OutputStreamWriter(
                Files.newOutputStream(Path.of(LOG_FILE)), StandardCharsets.UTF_8))) {
            run(new Scanner(System.in, StandardCharsets.UTF_8.name()), log);
        } catch (Exception e) {
            System.err.println("Не удалось создать файл журнала: " + e.getMessage());
        }
    }

    private static void run(Scanner scanner, PrintWriter log) {
        try {
            WordleDictionary dictionary = new WordleDictionaryLoader(log).loadFromFile(DICTIONARY_FILE);
            WordleGame game = new WordleGame(dictionary, log);

            System.out.println("Добро пожаловать в Wordle!");
            System.out.println("Введите слово из 5 букв. Пустая строка — подсказка.");

            while (!game.isGameOver() && scanner.hasNextLine()) {
                System.out.print("> ");
                String input = scanner.nextLine();

                if (input.trim().isEmpty()) {
                    String suggestion = game.suggestWord();
                    System.out.println("Подсказка: " + suggestion);
                    log.println("Подсказка: " + suggestion);
                    continue;
                }

                try {
                    String guess = WordleDictionary.normalizeWord(input);
                    String hint = game.makeMoveAndGetHint(guess);
                    System.out.println(guess);
                    System.out.println(hint);
                    System.out.println("Осталось попыток: " + game.getAttemptsLeft());
                    log.println("Слово: " + guess + ", подсказка: " + hint
                            + ", осталось попыток: " + game.getAttemptsLeft());
                } catch (InvalidWordException e) {
                    System.out.println(e.getMessage());
                }
            }

            if (!game.isGameOver()) {
                log.println("Игра прервана: завершён ввод пользователя");
                return;
            }

            if (game.isSolved()) {
                System.out.println("Поздравляем! Вы угадали слово.");
            } else if (game.getAttemptsLeft() == 0) {
                System.out.println("Попытки закончились.");
            }
            System.out.println("Загаданное слово: " + game.getAnswer());
            log.println("Игра завершена. Ответ: " + game.getAnswer());
        } catch (Exception e) {
            log.println("Ошибка программы: " + e.getMessage());
            e.printStackTrace(log);
        }
    }
}
