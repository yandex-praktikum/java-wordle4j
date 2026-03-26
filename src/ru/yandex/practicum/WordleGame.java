package ru.yandex.practicum;

import java.util.*;

import ru.yandex.practicum.exception.NoHintException;
import ru.yandex.practicum.exception.WordNotFoundException;


public class WordleGame {

    private String answer;
    private int steps;
    private WordleDictionary dictionary;
    private List<Attempt> attempts = new ArrayList<>();
    private boolean isWin = false;

    // ограничения
    private Map<Integer, Character> correct = new HashMap<>();
    private Map<Character, Set<Integer>> wrongPos = new HashMap<>();
    private Set<Character> required = new HashSet<>();
    private Set<Character> excluded = new HashSet<>();

    public WordleGame(String answer, int steps, WordleDictionary dictionary) {
        this.answer = answer;
        this.steps = steps;
        this.dictionary = dictionary;
    }

    public void makeStep(String word) throws IllegalArgumentException, WordNotFoundException {
        String playerWord = WordleDictionaryLoader.normalize(word);

        String result = checkAttempt(playerWord, answer);
        Attempt attempt = new Attempt(playerWord, result);
        attempts.add(attempt);

        updateConstraints(attempt);

        if (result.equals("+++++")) {
            isWin = true;
        }

        steps--;
    }

    public int getSteps() {
        return steps;
    }

    public boolean isGameWin() {
        return isWin;
    }

    public String checkAttempt(String attempt, String answer) {
        char[] result = new char[attempt.length()];
        char[] answerChars = answer.toCharArray();

        for (int i = 0; i < attempt.length(); i++) {
            if (attempt.charAt(i) == answerChars[i]) {
                result[i] = '+';
                answerChars[i] = '#';
            }
        }

        for (int i = 0; i < attempt.length(); i++) {
            if (result[i] == '+') continue;

            char c = attempt.charAt(i);
            boolean found = false;

            for (int j = 0; j < answerChars.length; j++) {
                if (answerChars[j] == c) {
                    found = true;
                    answerChars[j] = '#';
                    break;
                }
            }
            result[i] = found ? '^' : '-';
        }
        return new String(result);
    }

    public String getHint() throws NoHintException, WordNotFoundException {
        List<String> hints = new ArrayList<>();

        for (String word : dictionary.getWords()) {
            if (matches(word)) {
                hints.add(word);
            }
        }

        if (hints.isEmpty()) {
            throw new NoHintException();
        }

        Map<Character, Integer> freq = new HashMap<>();

        for (String word : hints) {
            for (char c : word.toCharArray()) {
                freq.put(c, freq.getOrDefault(c, 0) + 1);
            }
        }

        String bestWord = null;
        int bestScore = -1;

        for (String word : hints) {
            int score = 0;
            Set<Character> unique = new HashSet<>();

            for (char c : word.toCharArray()) {
                if (unique.add(c)) {
                    score += freq.get(c);
                }
            }

            if (score > bestScore) {
                bestScore = score;
                bestWord = word;
            }
        }

        return bestWord;
    }

    private boolean matches(String word) {

        // исключённые буквы
        for (char c : word.toCharArray()) {
            if (excluded.contains(c)) {
                return false;
            }
        }

        // обязательные буквы
        for (char c : required) {
            if (word.indexOf(c) == -1) {
                return false;
            }
        }

        // правильные позиции
        for (Map.Entry<Integer, Character> e : correct.entrySet()) {
            if (word.charAt(e.getKey()) != e.getValue()) {
                return false;
            }
        }

        // неправильные позиции
        for (Map.Entry<Character, Set<Integer>> e : wrongPos.entrySet()) {
            char c = e.getKey();

            for (int pos : e.getValue()) {
                if (word.charAt(pos) == c) {
                    return false;
                }
            }
        }

        return true;
    }

    private void updateConstraints(Attempt attempt) {
        String word = attempt.getWord();
        String result = attempt.getResult();

        Map<Character, Integer> presentCount = new HashMap<>();

        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            char r = result.charAt(i);

            if (r == '+' || r == '^') {
                presentCount.put(c, presentCount.getOrDefault(c, 0) + 1);
            }
        }

        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            char r = result.charAt(i);

            if (r == '+') {
                correct.put(i, c);
                required.add(c);

            } else if (r == '^') {
                required.add(c);
                wrongPos.computeIfAbsent(c, k -> new HashSet<>()).add(i);

            } else if (r == '-') {

                if (!presentCount.containsKey(c)) {
                    excluded.add(c);
                }
            }
        }
    }
}