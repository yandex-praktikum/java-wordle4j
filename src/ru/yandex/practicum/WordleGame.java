package ru.yandex.practicum;

import ru.yandex.practicum.InvalidWordLengthException;
import ru.yandex.practicum.WordNotInDictionaryException;

import java.util.*;
import java.util.stream.Collectors;

public class WordleGame {

    private static final int MAX_ATTEMPTS = 6;
    private static final int WORD_LENGTH = 5;

    private final String answer;
    private final WordleDictionary dictionary;
    private int attemptsLeft;
    private final List<String> history;
    private final Set<Character> correctLetters;
    private final Set<Character> misplacedLetters;
    private final Set<Character> wrongLetters;
    private final Set<String> usedHints;

    public WordleGame(WordleDictionary dictionary) {
        this.dictionary = dictionary;
        this.answer = dictionary.getRandomWord();
        this.attemptsLeft = MAX_ATTEMPTS;
        this.history = new ArrayList<>();
        this.correctLetters = new HashSet<>();
        this.misplacedLetters = new HashSet<>();
        this.wrongLetters = new HashSet<>();
        this.usedHints = new HashSet<>();
    }

    public String getAnswer() {
        return answer;
    }

    public int getAttemptsLeft() {
        return attemptsLeft;
    }

    public boolean isGameOver() {
        return attemptsLeft == 0 || isWordGuessed();
    }

    public boolean isWordGuessed() {
        if (history.isEmpty()) return false;
        return history.get(history.size() - 1).equals(answer);
    }

    public String makeGuess(String guess) throws InvalidWordLengthException, WordNotInDictionaryException {
        if (guess.length() != WORD_LENGTH) {
            throw new InvalidWordLengthException("Слово должно состоять из " + WORD_LENGTH + " букв");
        }
        if (!dictionary.contains(guess)) {
            throw new WordNotInDictionaryException("Слово не найдено в словаре");
        }

        history.add(guess);
        attemptsLeft--;

        return generateHint(guess);
    }

    private String generateHint(String guess) {
        char[] hint = new char[WORD_LENGTH];
        Arrays.fill(hint, '-');

        boolean[] usedInAnswer = new boolean[WORD_LENGTH];
        for (int i = 0; i < WORD_LENGTH; i++) {
            if (guess.charAt(i) == answer.charAt(i)) {
                hint[i] = '+';
                usedInAnswer[i] = true;
                correctLetters.add(guess.charAt(i));
            }
        }

        for (int i = 0; i < WORD_LENGTH; i++) {
            if (hint[i] == '+') continue;

            char c = guess.charAt(i);
            for (int j = 0; j < WORD_LENGTH; j++) {
                if (!usedInAnswer[j] && c == answer.charAt(j)) {
                    hint[i] = '^';
                    usedInAnswer[j] = true;
                    misplacedLetters.add(c);
                    break;
                }
            }
            if (hint[i] != '^') {
                wrongLetters.add(c);
            }
        }

        return new String(hint);
    }

    public String getHintWord() {
        List<String> possibleWords = dictionary.getAllWords().stream()
                .filter(word -> !history.contains(word))
                .filter(word -> !usedHints.contains(word))
                .filter(this::matchesCurrentKnowledge)
                .collect(Collectors.toList());

        if (possibleWords.isEmpty()) {
            return null;
        }

        Random random = new Random();
        String hint = possibleWords.get(random.nextInt(possibleWords.size()));
        usedHints.add(hint);
        return hint;
    }

    private boolean matchesCurrentKnowledge(String word) {
        if (history.contains(word)) return false;

        for (int i = 0; i < WORD_LENGTH; i++) {
            char answerChar = answer.charAt(i);
            char wordChar = word.charAt(i);
            if (correctLetters.contains(answerChar) && wordChar != answerChar) {
                return false;
            }
        }

        for (char c : misplacedLetters) {
            if (!word.contains(String.valueOf(c))) return false;
        }

        for (char c : wrongLetters) {
            if (word.contains(String.valueOf(c))) return false;
        }

        return true;
    }
}