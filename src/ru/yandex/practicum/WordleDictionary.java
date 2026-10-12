package ru.yandex.practicum;

import java.util.*;

public class WordleDictionary {
    private final List<String> words;
    private final Set<String> wordSet;

    public WordleDictionary(Collection<String> words) throws EmptyDictionaryException {
        if (words == null || words.isEmpty()) {
            throw new EmptyDictionaryException("Словарь пуст");
        }
        this.words = Collections.unmodifiableList(new ArrayList<>(words));
        this.wordSet = new HashSet<>(words);
    }

    public List<String> getWords() {
        return words;
    }

    public boolean contains(String word) {
        return wordSet.contains(word);
    }

    public String getRandomWord() {
        return words.get(new Random().nextInt(words.size()));
    }

    public int size() {
        return words.size();
    }

    public static String normalize(String word) {
        if (word == null) {
            return "";
        }
        return word.trim()
                .toLowerCase(Locale.ROOT)
                .replace('ё', 'е');
    }

    public static boolean isValidWordFormat(String word) {
        String normalized = normalize(word);
        return normalized.length() == 5 && normalized.matches("[а-я]+");
    }

    public static String analyzeGuess(String guess, String target) {
        if (guess == null || target == null || guess.length() != 5 || target.length() != 5) {
            throw new IllegalArgumentException("Слова должны быть длиной 5 символов");
        }

        char[] result = new char[5];
        Map<Character, Integer> targetCounts = new HashMap<>();

        for (int i = 0; i < 5; i++) {
            char c = target.charAt(i);
            targetCounts.merge(c, 1, Integer::sum);
        }

        for (int i = 0; i < 5; i++) {
            if (guess.charAt(i) == target.charAt(i)) {
                result[i] = '+';
                targetCounts.merge(guess.charAt(i), -1, Integer::sum);
            }
        }

        for (int i = 0; i < 5; i++) {
            if (result[i] == '+') {
                continue;
            }
            char c = guess.charAt(i);
            int count = targetCounts.getOrDefault(c, 0);
            if (count > 0) {
                result[i] = '^';
                targetCounts.put(c, count - 1);
            } else {
                result[i] = '-';
            }
        }

        return new String(result);
    }
}
