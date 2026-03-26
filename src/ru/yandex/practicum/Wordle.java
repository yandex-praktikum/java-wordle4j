package ru.yandex.practicum;

import ru.yandex.practicum.exception.EmptyDictionaryException;
import ru.yandex.practicum.exception.InvalidInputException;
import ru.yandex.practicum.exception.NoHintException;
import ru.yandex.practicum.exception.WordNotFoundException;

import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Wordle {

    public static void main(String[] args) throws NoHintException,
            InvalidInputException, ArrayIndexOutOfBoundsException,
            EmptyDictionaryException {

        try (PrintWriter log = new PrintWriter("wordle.log")) {

            WordleDictionaryLoader loader = new WordleDictionaryLoader();
            WordleDictionary dictionary = loader.load("words_ru.txt");

            String answer = dictionary.getRandomWord();

            WordleGame game = new WordleGame(answer, 6, dictionary);

            System.out.println("Игра началась!");
            log.println("Игра началась. Ответ: " + answer);

            boolean gameOver = false;
            Scanner scanner = new Scanner(System.in);

            while (!gameOver) {
                System.out.println("Введите команду: \n" +
                        "1 - ввести слово\n" +
                        "2 - выйти из игры");

                int command;

                try {
                    command = scanner.nextInt();
                    scanner.nextLine();
                } catch (InputMismatchException e) {
                    System.out.println("Ошибка: введите число команды (1 или 2)");
                    log.println("Ошибка ввода команды: не число");

                    scanner.nextLine();
                    continue;
                }

                switch (command) {
                    case 1:
                        System.out.println("Введите слово (Enter = подсказка): ");
                        try {
                            String word = scanner.nextLine();

                            if (word.trim().isEmpty()) {
                                String hint = game.getHint();
                                System.out.println("Подсказка: " + hint);
                                log.println("Подсказка: " + hint);
                                break;
                            }

                            if (word.length() > 5) {
                                throw new InvalidInputException("Введено неподходящее слово");
                            }

                            game.makeStep(word);

                            String result = game.checkAttempt(word, answer);

                            System.out.println("Результат вашей попытки: " + result + "\n" +
                                    "Ваши попытки: " + game.getSteps() + "\n");

                            log.println("Ход: " + word + " -> " + result);

                        } catch (WordNotFoundException | InvalidInputException e) {
                            log.println("Ошибка ввода: " + e.getMessage());
                        }

                        if (game.isGameWin()) {
                            System.out.println("Отлично! Вы угадали слово: " + answer);
                            log.println("Победа");
                            gameOver = true;
                        } else if (game.getSteps() == 0) {
                            System.out.println("Вы не угадали слово: " + answer);
                            log.println("Поражение");
                            gameOver = true;
                        }
                        break;

                    case 2:
                        log.println("Игрок вышел из игры");
                        gameOver = true;
                        break;

                    default:
                        log.println("Неизвестная команда");
                }
            }

            log.flush();

        } catch (Exception e) {
            System.err.println("Критическая ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }
}