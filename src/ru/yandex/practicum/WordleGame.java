package ru.yandex.practicum;

import java.util.*;

public class WordleGame {
    public static final int MAX_ATTEMPTS = 6;

    private final WordleDictionary dictionary;
    private final String target;
    private final Random random = new Random();

    private final List<String> guesses = new ArrayList<>();
    private final List<String> hints = new ArrayList<>();
    private final Set<String> usedHints = new HashSet<>();

    private int attemptsLeft;
    private boolean gameOver;
    private boolean win;

    public WordleGame(WordleDictionary dictionary) {
        this.dictionary = Objects.requireNonNull(dictionary);
        this.target = dictionary.getRandomWord();
        this.attemptsLeft = MAX_ATTEMPTS;
    }

    public String makeGuess(String rawGuess)
            throws InvalidWordException, WordNotFoundInDictionary, GameStateException {

        if (gameOver) {
            throw new GameStateException("Игра уже завершена");
        }

        String guess = WordleDictionary.normalize(rawGuess);

        if (!WordleDictionary.isValidWordFormat(guess)) {
            throw new InvalidWordException("Слово должно состоять из 5 русских букв");
        }

        if (!dictionary.contains(guess)) {
            throw new WordNotFoundInDictionary("Слово отсутствует в словаре: " + guess);
        }

        String hint = WordleDictionary.analyzeGuess(guess, target);

        guesses.add(guess);
        hints.add(hint);
        attemptsLeft--;

        if (guess.equals(target)) {
            win = true;
            gameOver = true;
        } else if (attemptsLeft <= 0) {
            gameOver = true;
        }

        return hint;
    }

    public String getHint() throws GameStateException {
        if (gameOver) {
            throw new GameStateException("Игра уже завершена");
        }

        List<String> candidates = new ArrayList<>();

        for (String word : dictionary.getWords()) {
            if (guesses.contains(word) || usedHints.contains(word)) {
                continue;
            }
            if (isCompatible(word)) {
                candidates.add(word);
            }
        }

        if (candidates.isEmpty()) {
            throw new GameStateException("Не найдено ни одного подходящего слова");
        }

        String hint = candidates.get(random.nextInt(candidates.size()));
        usedHints.add(hint);
        return hint;
    }

    private boolean isCompatible(String candidate) {
        for (int i = 0; i < guesses.size(); i++) {
            String guess = guesses.get(i);
            String hint = hints.get(i);

            if (!WordleDictionary.analyzeGuess(guess, candidate).equals(hint)) {
                return false;
            }
        }
        return true;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isWin() {
        return win;
    }

    public int getAttemptsLeft() {
        return attemptsLeft;
    }

    public String getTarget() {
        return target;
    }

    public List<String> getGuesses() {
        return Collections.unmodifiableList(new ArrayList<>(guesses));
    }

    public List<String> getHints() {
        return Collections.unmodifiableList(new ArrayList<>(hints));
    }
}
