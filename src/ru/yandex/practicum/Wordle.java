package ru.yandex.practicum;

import ru.yandex.practicum.InvalidWordLengthException;
import ru.yandex.practicum.WordNotInDictionaryException;
import ru.yandex.practicum.WordNotFoundException;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Wordle {

    private static PrintWriter log;
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("wordle.log", StandardCharsets.UTF_8))) {
            log = writer;
            logMessage("Программа запущена");

            WordleDictionaryLoader loader = new WordleDictionaryLoader();
            WordleDictionary dictionary = loader.loadDictionary("words.txt");
            logMessage("Словарь загружен. Слов в словаре: " + dictionary.size());

            WordleGame game = new WordleGame(dictionary);
            playGame(game);

        } catch (WordNotFoundException e) {
            System.err.println("Ошибка: " + e.getMessage());
            if (log != null) logMessage("Ошибка: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Произошла ошибка. Подробности в логе.");
            if (log != null) e.printStackTrace(log);
        }
    }

    private static void playGame(WordleGame game) {
        System.out.println("Добро пожаловать в Wordle!");
        System.out.println("Угадайте слово из 5 букв. У вас 6 попыток.");
        System.out.println("Подсказки: + (буква на месте), ^ (буква есть в слове), - (буквы нет)");
        System.out.println("Для подсказки слова нажмите Enter без ввода.\n");

        while (!game.isGameOver()) {
            System.out.println("Осталось попыток: " + game.getAttemptsLeft());
            System.out.print("Введите слово: ");
            String input = scanner.nextLine().trim().toLowerCase().replace('ё', 'е');

            if (input.isEmpty()) {
                String hintWord = game.getHintWord();
                if (hintWord == null) {
                    System.out.println("Нет доступных слов для подсказки.");
                } else {
                    System.out.println("Подсказка: " + hintWord);
                }
                continue;
            }

            try {
                String hint = game.makeGuess(input);
                System.out.println(input);
                System.out.println(hint);

                if (game.isWordGuessed()) {
                    System.out.println("Поздравляю! Вы угадали слово: " + game.getAnswer());
                    logMessage("Игра завершена победой. Загаданное слово: " + game.getAnswer());
                    return;
                }

            } catch (InvalidWordLengthException e) {
                System.out.println(e.getMessage());
                logMessage("Ошибка длины слова: " + input);
            } catch (WordNotInDictionaryException e) {
                System.out.println(e.getMessage());
                logMessage("Слово не в словаре: " + input);
            } catch (Exception e) {
                logMessage("Ошибка при обработке хода: " + e.getMessage());
                System.out.println("Произошла ошибка. Попробуйте снова.");
            }
        }

        System.out.println("Вы проиграли. Загаданное слово: " + game.getAnswer());
        logMessage("Игра завершена поражением. Загаданное слово: " + game.getAnswer());
    }

    private static void logMessage(String message) {
        if (log != null) {
            log.println(message);
            log.flush();
        }
    }
}