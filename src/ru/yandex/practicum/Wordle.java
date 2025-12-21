package ru.yandex.practicum;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Wordle {

    public static void main(String[] args) {
        try (PrintWriter log = new PrintWriter(
                Files.newBufferedWriter(Path.of("wordle.log"), StandardCharsets.UTF_8))) {

            log.println("---Wordle started---");

            WordleDictionaryLoader loader = new WordleDictionaryLoader(log);
            WordleDictionary dictionary = loader.load("words_ru.txt");

            WordleGame game = new WordleGame(dictionary, log);
            game.run();

            log.println("---Wordle finished---");

        } catch (ProgramException e) {
            System.out.println("Ошибка программы: " + e.getMessage() + " (подробности в wordle.log)");
        } catch (Exception e) {
            System.out.println("Произошла ошибка. Подробности в wordle.log");
            try (PrintWriter log = new PrintWriter(
                    Files.newBufferedWriter(Path.of("wordle.log"), StandardCharsets.UTF_8))) {
                e.printStackTrace(log);
            } catch (Exception ignored) {
            }
        }
    }
}
