package ru.yandex.practicum;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class WordleGame {
    public static final int WORD_LENGTH = 5;
    public static final int MAX_STEPS = 6;

    private final WordleDictionary dictionary;
    private final Random random = new Random();
    private final Set<String> usedWords = new LinkedHashSet<>();
    private final Set<String> suggestedWords = new LinkedHashSet<>();
    private final Map<String, String> guessHints = new LinkedHashMap<>();

    private final String answer;
    private int attemptsLeft = MAX_STEPS;
    private boolean solved;

    public WordleGame(WordleDictionary dictionary, PrintWriter log) {
        this(dictionary, chooseAnswer(dictionary), log);
    }

    public WordleGame(WordleDictionary dictionary, String answer, PrintWriter log) {
        if (dictionary == null || log == null) {
            throw new IllegalArgumentException("Словарь и журнал обязательны");
        }
        String normalizedAnswer = WordleDictionary.normalizeWord(answer);
        if (!dictionary.contains(normalizedAnswer)) {
            throw new IllegalArgumentException("Ответ отсутствует в словаре");
        }

        this.dictionary = dictionary;
        this.answer = normalizedAnswer;
        log.println("Начата игра. В словаре слов: " + dictionary.size());
    }

    public String makeMoveAndGetHint(String guess) throws InvalidWordException {
        if (isGameOver()) {
            throw new IllegalStateException("Игра уже завершена");
        }
        String normalized = WordleDictionary.normalizeWord(guess);
        if (normalized.length() != WORD_LENGTH) {
            throw new InvalidWordException("Введите слово из 5 русских букв");
        }
        if (!dictionary.contains(normalized)) {
            throw new WordNotFoundInDictionaryException("Слова нет в словаре");
        }
        if (usedWords.contains(normalized)) {
            throw new WordAlreadyUsedException("Это слово уже использовалось");
        }

        String hint = buildHint(normalized, answer);
        usedWords.add(normalized);
        guessHints.put(normalized, hint);
        attemptsLeft--;
        solved = normalized.equals(answer);
        return hint;
    }

    public String suggestWord() {
        List<String> candidates = new ArrayList<>();
        for (String candidate : dictionary.getWords()) {
            if (usedWords.contains(candidate) || suggestedWords.contains(candidate)) {
                continue;
            }
            boolean matches = true;
            for (Map.Entry<String, String> guess : guessHints.entrySet()) {
                if (!guess.getValue().equals(buildHint(guess.getKey(), candidate))) {
                    matches = false;
                    break;
                }
            }
            if (matches) {
                candidates.add(candidate);
            }
        }

        if (candidates.isEmpty()) {
            throw new IllegalStateException("Не найдено подходящих слов для подсказки");
        }
        String suggestion = candidates.get(random.nextInt(candidates.size()));
        suggestedWords.add(suggestion);
        return suggestion;
    }

    public String getAnswer() {
        return answer;
    }

    public int getAttemptsLeft() {
        return attemptsLeft;
    }

    public boolean isSolved() {
        return solved;
    }

    public boolean isGameOver() {
        return solved || attemptsLeft == 0;
    }

    private static String chooseAnswer(WordleDictionary dictionary) {
        if (dictionary == null) {
            throw new IllegalArgumentException("Словарь обязателен");
        }
        return dictionary.getRandomWord();
    }

    public static String buildHint(String guess, String answer) {
        String normalizedGuess = WordleDictionary.normalizeWord(guess);
        String normalizedAnswer = WordleDictionary.normalizeWord(answer);
        if (normalizedGuess.length() != WORD_LENGTH || normalizedAnswer.length() != WORD_LENGTH) {
            throw new IllegalArgumentException("Оба слова должны содержать 5 русских букв");
        }

        char[] hint = new char[WORD_LENGTH];
        Arrays.fill(hint, '-');
        boolean[] usedAnswerLetters = new boolean[WORD_LENGTH];

        for (int i = 0; i < WORD_LENGTH; i++) {
            if (normalizedGuess.charAt(i) == normalizedAnswer.charAt(i)) {
                hint[i] = '+';
                usedAnswerLetters[i] = true;
            }
        }
        for (int i = 0; i < WORD_LENGTH; i++) {
            if (hint[i] == '+') {
                continue;
            }
            for (int j = 0; j < WORD_LENGTH; j++) {
                if (!usedAnswerLetters[j] && normalizedGuess.charAt(i) == normalizedAnswer.charAt(j)) {
                    hint[i] = '^';
                    usedAnswerLetters[j] = true;
                    break;
                }
            }
        }
        return new String(hint);
    }
}
