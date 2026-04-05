package ru.yandex.practicum;

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

    /*private String answer;

    private int steps;

    private WordleDictionary dictionary;*/

public static String equalsLetter(String enterWord, String questWord){
        StringBuilder grade = new StringBuilder();
        String input = enterWord.toLowerCase();
        String target = questWord.toLowerCase();

        for (int i = 0; i < input.length(); i++) {
            char e = input.charAt(i);

            if (e == target.charAt(i)) {
                grade.append("+");
            } else if (target.contains(String.valueOf(e))) {
                grade.append("^");
            } else {
                grade.append("-");
            }
        }
        return grade.toString();
    }
}
