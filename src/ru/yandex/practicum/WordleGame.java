package ru.yandex.practicum;

import java.io.PrintWriter;
import java.util.*;

public class WordleGame {

    private final WordleDictionary dictionary;
    private final PrintWriter log;

    private final Random rnd = new Random();
    private final WordleEvaluator evaluator = new WordleEvaluator();

    private String answer;
    private int steps;


    private final List<String> guesses = new ArrayList<>();
    private final List<String> patterns = new ArrayList<>();


    private final Set<String> usedWords = new HashSet<>();

    public WordleGame(WordleDictionary dictionary, PrintWriter log) {
        this.dictionary = dictionary;
        this.log = log;
    }

    public void run() {
        answer = dictionary.getRandomWord(rnd);
        steps = 6;

        log.println("ANSWER: " + answer);
        log.println("Steps: " + steps);
        log.println("Dictionary size: " + dictionary.size());

        Scanner sc = new Scanner(System.in);

        while (steps > 0) {
            System.out.println("Введите слово из 5 букв:");
            String input = sc.nextLine();
            String guess = normalize(input);

            boolean isAuto = false;


            if (guess.isEmpty()) {
                guess = suggestByHistoryOrThrow();
                isAuto = true;
            }

            try {
                validateGuess(guess);
            } catch (GameException e) {
                System.out.println("Ошибка: " + e.getMessage());
                continue;
            }

            // Засчитываем ход
            steps--;
            usedWords.add(guess);

            String pattern = evaluator.evaluate(guess, answer);

            System.out.println(guess);
            System.out.println(pattern);

            guesses.add(guess);
            patterns.add(pattern);

            log.println("Guess: " + guess + " Pattern: " + pattern + " StepsLeft: " + steps + " Auto: " + isAuto);

            if (guess.equals(answer)) {
                System.out.println("Победа!");
                System.out.println("Загаданное слово: " + answer);
                return;
            }

            System.out.println("Осталось попыток: " + steps);
        }

        System.out.println("Вы проиграли.");
        System.out.println("Загаданное слово: " + answer);
    }

    private void validateGuess(String guess)
            throws InvalidWordFormatException, WordNotFoundInDictionaryException {

        if (!isValidGuess(guess)) {
            throw new InvalidWordFormatException("нужно слово из 5 русских букв");
        }
        if (!dictionary.contains(guess)) {
            throw new WordNotFoundInDictionaryException("слова нет в словаре");
        }
    }

    private boolean isValidGuess(String s) {
        if (s.length() != 5) return false;
        for (int i = 0; i < 5; i++) {
            char c = s.charAt(i);
            if (c < 'а' || c > 'я') return false;
        }
        return true;
    }

    private String normalize(String s) {
        return s.trim().toLowerCase().replace('ё', 'е');
    }

    private String suggestByHistoryOrThrow() {
        List<String> candidates = new ArrayList<>();

        for (String word : dictionary.allWords()) {
            if (usedWords.contains(word)) continue;

            boolean ok = true;
            for (int i = 0; i < guesses.size(); i++) {
                String g = guesses.get(i);
                String p = patterns.get(i);

                if (!evaluator.evaluate(g, word).equals(p)) {
                    ok = false;
                    break;
                }
            }

            if (ok) candidates.add(word);
        }

        log.println("Hint candidates: " + candidates.size());

        if (candidates.isEmpty()) {
            throw new RuntimeException("No candidates left for hint. Constraints inconsistent.");
        }
        return candidates.get(rnd.nextInt(candidates.size()));
    }
}
