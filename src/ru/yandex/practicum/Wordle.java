package ru.yandex.practicum;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Wordle {

    public static void main(String[] args) {

        try (PrintWriterLogger logger =
                     new PrintWriterLogger(new FileWriter("game.log", true), true)) {

            Wordle app = new Wordle();
            app.startGame(logger);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void startGame(Logger logger) throws DictionaryLoadException {

        try {
            WordleDictionary dictionary = loadDictionary(logger);
            WordleGame game = new WordleGame(
                    dictionary.normalized().filteredByLength(5),
                    logger
            );

            runGameLoop(game, logger);

        } catch (Exception e) {
            logger.error("Ошибка при запуске игры " + e);
            throw e;
        }
    }

    WordleDictionary loadDictionary(Logger logger) throws DictionaryLoadException {
        WordleDictionaryLoader loader = new WordleDictionaryLoader(logger);
        return loader.load("words_ru.txt");
    }

    void runGameLoop(WordleGame game, Logger logger) {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Для получения подсказки нажмите Enter \n");
        while (!game.gameOver()) {

            System.out.print("Введите слово: ");
            String input = scanner.nextLine();
            try {
                if (input.isBlank()) {
                    processHint(game);

                    if (game.gameWin()) {
                        System.out.println("Победа!");
                        logger.info("Игрок победил");
                        return;
                    }
                } else {
                    processUserWord(game, input);

                    if (game.gameWin()) {
                        System.out.println("Победа!");
                        logger.info("Победа с подсказкой");
                        return;
                    }
                }

            } catch (GameException e) {
                System.out.println("Ошибка: " + e.getMessage());
                logger.debug("Ошибка в игровом процессе: " + e.getMessage());
            }
        }

        System.out.println("Игра окончена. Было загадано слово: " + game.getAnswer());
        logger.info("Игра окончена. Игрок проиграл. Слово: " + game.getAnswer());
    }

    void processHint(WordleGame game) throws GameException {

        ArrayList<String> hint = game.requestHint();

        System.out.println("Подсказка: " + hint.getFirst());
        System.out.println("Результат: " + hint.getLast());
    }

    void processUserWord(WordleGame game, String input) throws GameException {

        if (!notContainForbiddenCharacters(input)) {
            throw new GameException("Слово содержит запрещенные символы");
        }

        String result = game.makeAttempt(input);
        System.out.println(result);
    }

    boolean notContainForbiddenCharacters(String word) {
        if (word == null || word.isBlank()) {
            return false;
        }
        return !word.matches(".*[^а-яёА-ЯЁ\\-].*");
    }
}
