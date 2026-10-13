package ru.yandex.practicum;

import java.io.PrintWriter;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;

public class WordleHinter {

    private WordleDictionary dictionary;
    private Set<String> alreadySuggested;
    private Set<String> userInput;
    private final PrintWriter logger;
    private Random random;

    public WordleHinter(WordleDictionary dictionary, Set<String> alreadySuggested, Set<String> userInput,
                        PrintWriter logger) {
        this.dictionary = dictionary;
        this.alreadySuggested = alreadySuggested;
        this.userInput = userInput;
        this.logger = logger;
        this.random = new Random();
    }

    public String generateSuggestion(String answer) {
        List<String> suggestions = dictionary.getWords()
                .stream()
                .filter(word -> !alreadySuggested.contains(word))
                .filter(word -> !userInput.contains(word))
                .filter(findSuitableWords(answer))
                .toList();
        int bound = suggestions.size();
        if (bound < 1) {
            return "Вы использовали все доступные подсказки";
        }
        return suggestions.get(random.nextInt(bound));
    }

    public String generateSymbolicHint(String userWordInput, String answer) {
        StringBuilder hintSymbolic = new StringBuilder();
        String[] userInputAsChars = userWordInput.split("");
        String[] answerAsChars = answer.split("");

        for (int j = 0; j < userInputAsChars.length; j++) {
            if (userInputAsChars[j].equals(answerAsChars[j])) {
                hintSymbolic.append("+");
            } else if (answer.contains(userInputAsChars[j])) {
                hintSymbolic.append("^");
            } else {
                hintSymbolic.append("-");
            }
        }
        return hintSymbolic.toString();
    }

    private Predicate<String> findSuitableWords(String answer) {
        return word -> {
            int counter = 0;
            String[] split = word.split("");
            if (answer.contains(split[0])) {
                counter++;
            }
            if (answer.contains(split[1])) {
                counter++;
            }
            if (answer.contains(split[2])) {
                counter++;
            }
            if (answer.contains(split[3])) {
                counter++;
            }
            if (answer.contains(split[4])) {
                counter++;
            }
            return counter == 5;
        };
    }

}
