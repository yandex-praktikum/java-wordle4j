package ru.yandex.practicum;

import java.util.*;

/*
в этом классе хранится словарь и состояние игры
    текущий шаг
    всё что пользователь вводил
    правильный ответ

в этом классе нужны методы, которые
    проанализируют совпадение слова с ответом
    предложат слово-подсказку с учётом всего, что вводил пользователь ранее

не забудьте про специальные типы исключений для игровых и неигровых ошибок
 */
public class WordleGame {

    private Set<Character> listOfChar = new HashSet<>();
    private WordleDictionary dictionary;
    private int attempts = 1;
    private String targetWord;
    private String enterWord;

    public int getAttempts() {
        return attempts;
    }

    public String getTargetWord() {
        return targetWord;
    }

    public String getEnterWord() {
        return enterWord;
    }

    public WordleGame(WordleDictionary dictionary) {
        this.dictionary = dictionary;
    }

    private String promptUser(int step, Scanner sc) {
        System.out.print("Ход " + step + ". Введи слово: ");
        return sc.nextLine().toLowerCase();
    }

    public void runWordleGame(Scanner sc) {

        targetWord = dictionary.getRandomWord();


        while (attempts <= 6) {

            enterWord = promptUser(attempts, sc);

            if (enterWord.trim().equalsIgnoreCase("Выход")) {
                return;
            }

            if (enterWord.isBlank()) {
                enterWord = dictionary.getRandomWordIfEnter(listOfChar, targetWord);
                System.out.println("Подсказка от игры: " + enterWord);
            }

            if (enterWord.equals(targetWord)) {
                System.out.println("Вы угадали: " + enterWord);
                return;
            }

            if (!dictionary.containsToDictionary(enterWord)) {
                System.out.println("Такого слова в словаре нет, введите другое ");
                continue;
            }


            equalsLetter(enterWord, targetWord);
            attempts++;
        }
        System.out.println("Проиграл! Было слово: " + targetWord);
    }

    private void equalsLetter(String enterWord, String questWord) {
        StringBuilder grade = new StringBuilder();

        for (int i = 0; i < enterWord.length(); i++) {
            char e = enterWord.charAt(i);

            if (e == questWord.charAt(i)) {
                grade.append("+");
                listOfChar.add(e);
            } else if (questWord.contains(String.valueOf(e))) {
                grade.append("^");
                listOfChar.add(e);
            } else {
                grade.append("-");
            }
        }
        System.out.println(grade);

    }
}
