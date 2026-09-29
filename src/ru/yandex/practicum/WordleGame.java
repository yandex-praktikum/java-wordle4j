package ru.yandex.practicum;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WordleGame {

    private String answer;
    private int steps;
    private WordleDictionary dictionary;
    private List<String> pastWords = new ArrayList<>();
    private List<String> pastResults = new ArrayList<>();
    private List<String> hints = new ArrayList<>();
    private PrintWriter writer;
    private Random random = new Random();

    public WordleGame(WordleDictionary dictionary, PrintWriter writer) {
        if (dictionary == null || dictionary.size() == 0) {
            throw new RuntimeException("Словарь пуст");
        }

        this.dictionary = dictionary;
        this.writer = writer;

        int index = random.nextInt(dictionary.size());

        while (dictionary.getWord(index).length() != 5) {
            index = random.nextInt(dictionary.size());
        }

        answer = dictionary.getWord(index);
    }

    public WordleGame(WordleDictionary dictionary, String answer, PrintWriter writer) {
        if (dictionary == null || dictionary.size() == 0) {
            throw new RuntimeException("Словарь пуст");
        }

        this.dictionary = dictionary;
        this.answer = answer;
        this.writer = writer;
    }

    public boolean checkAnswer(String word) throws WordNotFoundInDictionary {
        if (answer == null) {
            throw new RuntimeException("Загаданное слово отсутствует");
        }

        if (writer == null) {
            throw new RuntimeException("Логгер отсутствует");
        }

        if (!dictionary.containsWord(word)) {
            throw new WordNotFoundInDictionary(
                    "Такого слова нет в словаре: " + word
            );
        }

        pastWords.add(word);
        pastResults.add(checkLetters(word));

        writer.println("Проверка слова: " + word);

        steps++;

        writer.println("Количество попыток: " + steps);

        return answer.equals(word);
    }

    public String checkLetters(String word) {
        if (answer == null || answer.length() != 5) {
            throw new RuntimeException("Некорректное загаданное слово");
        }

        StringBuilder result = new StringBuilder("-----");
        boolean[] used = new boolean[5];

        for (int i = 0; i < 5; i++) {
            if (word.charAt(i) == answer.charAt(i)) {
                result.setCharAt(i, '+');
                used[i] = true;
            }
        }

        for (int i = 0; i < 5; i++) {
            if (result.charAt(i) == '+') {
                continue;
            }

            for (int j = 0; j < 5; j++) {
                if (!used[j] && word.charAt(i) == answer.charAt(j)) {
                    result.setCharAt(i, '^');
                    used[j] = true;
                    break;
                }
            }
        }

        return result.toString();
    }

    public String getLastResult() {
        return pastResults.get(pastResults.size() - 1);
    }

    public String hintWord() {
        if (dictionary == null || dictionary.size() == 0) {
            throw new RuntimeException("Словарь пуст");
        }

        for (int i = 0; i < dictionary.size(); i++) {
            String word = dictionary.getWord(i);

            if (word.length() != 5
                    || pastWords.contains(word)
                    || hints.contains(word)) {
                continue;
            }

            boolean suitable = true;

            for (int k = 0; k < pastWords.size(); k++) {
                String pastWord = pastWords.get(k);
                String pastResult = pastResults.get(k);

                for (int j = 0; j < 5; j++) {
                    if (pastResult.charAt(j) == '+'
                            && word.charAt(j) != pastWord.charAt(j)) {
                        suitable = false;
                    } else if (pastResult.charAt(j) == '-'
                            && word.contains(String.valueOf(pastWord.charAt(j)))) {
                        suitable = false;
                    } else if (pastResult.charAt(j) == '^'
                            && word.charAt(j) == pastWord.charAt(j)) {
                        suitable = false;
                    }
                }
            }

            if (suitable) {
                hints.add(word);
                return word;
            }
        }

        return answer;
    }

    public int getSteps() {
        return steps;
    }

    public String getAnswer() {
        return answer;
    }
}