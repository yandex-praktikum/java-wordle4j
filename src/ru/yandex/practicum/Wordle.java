package ru.yandex.practicum;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Wordle {
    private static final String DICTIONARY_FILE = "russian_nouns.txt";
    private static final String LOG_FILE = "wordle.log";

    public static void main(String[] args) {
        String dictionaryFile = args.length > 0 ? args[0] : DICTIONARY_FILE;

        try (PrintWriter log = new PrintWriter(
                new OutputStreamWriter(new FileOutputStream(LOG_FILE), StandardCharsets.UTF_8), true);
             BufferedReader console = new BufferedReader(
                     new InputStreamReader(System.in, StandardCharsets.UTF_8))) {

            log.println("Запуск Wordle");

            WordleDictionaryLoader loader = new WordleDictionaryLoader();
            WordleDictionary dictionary = loader.load(dictionaryFile);
            log.println("Словарь загружен. Слов: " + dictionary.size());

            WordleGame game = new WordleGame(dictionary);
            log.println("Загадано слово: " + game.getTarget());

            System.out.println("Игра Wordle. У вас 6 попыток.");
            System.out.println("Введите слово из 5 русских букв или нажмите Enter для подсказки.");

            while (!game.isGameOver()) {
                System.out.println("Осталось попыток: " + game.getAttemptsLeft());
                System.out.print("> ");

                String input = console.readLine();
                if (input == null) {
                    log.println("Поток ввода закрыт");
                    break;
                }

                input = input.trim();

                if (input.isEmpty()) {
                    try {
                        String hint = game.getHint();
                        System.out.println("Подсказка: " + hint);
                        log.println("Выдана подсказка: " + hint);

                        String result = game.makeGuess(hint);
                        System.out.println(hint);
                        System.out.println(result);
                        log.println("Автоход: " + hint + " -> " + result);

                        if (game.isWin()) {
                            System.out.println("Вы угадали слово!");
                        }
                    } catch (InvalidWordException | WordNotFoundInDictionary e) {
                        log.println("Ошибка автоподсказки: " + e.getMessage());
                        System.out.println("Не удалось сделать автоматический ход.");
                    } catch (GameStateException e) {
                        log.println("Ошибка состояния игры: " + e.getMessage());
                        System.out.println("Внутренняя ошибка игры. Подробности в логе.");
                        break;
                    }
                    continue;
                }

                try {
                    String hint = game.makeGuess(input);
                    System.out.println(WordleDictionary.normalize(input));
                    System.out.println(hint);
                    log.println("Ход: " + WordleDictionary.normalize(input) + " -> " + hint);

                    if (game.isWin()) {
                        System.out.println("Вы угадали слово!");
                    }
                } catch (InvalidWordException | WordNotFoundInDictionary e) {
                    System.out.println(e.getMessage());
                    log.println("Некорректный ход: " + e.getMessage());
                } catch (GameStateException e) {
                    log.println("Ошибка состояния игры: " + e.getMessage());
                    System.out.println("Внутренняя ошибка игры. Подробности в логе.");
                    break;
                }
            }

            if (game.isWin()) {
                System.out.println("Победа! Загаданное слово: " + game.getTarget());
            } else {
                System.out.println("Игра окончена. Загаданное слово: " + game.getTarget());
            }

            log.println("Игра завершена. Победа: " + game.isWin());

        } catch (DictionaryException e) {
            System.err.println("Ошибка загрузки словаря: " + e.getMessage());
        } catch (Exception | Error e) {
            try (PrintWriter log = new PrintWriter(
                    new OutputStreamWriter(new FileOutputStream(LOG_FILE, true), StandardCharsets.UTF_8), true)) {
                log.println("Критическая ошибка:");
                e.printStackTrace(log);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            System.err.println("Критическая ошибка. Подробности в " + LOG_FILE);
        }
    }
}
