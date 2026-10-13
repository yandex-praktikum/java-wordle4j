package ru.yandex.practicum;

import ru.yandex.practicum.exceptions.InputContainsSpacesException;
import ru.yandex.practicum.exceptions.InputOutOfLanguageException;
import ru.yandex.practicum.exceptions.InputOutOfLengthException;
import ru.yandex.practicum.exceptions.WordNotFoundInDictionary;

import java.io.PrintWriter;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

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

    private String answer;

    private int steps;

    private WordleDictionary dictionary;

    private boolean isLoss;

    private Set<String> alreadySuggested;

    private Set<String> userInput;

    private WordleHinter hinter;

    private WordleValidator wordleValidator;

    public WordleGame(WordleDictionary dictionary,
                      Set<String> userInput,
                      Set<String> alreadySuggested,
                      WordleHinter hinter,
                      WordleValidator wordleValidator, PrintWriter writer) {
        this.dictionary = dictionary;
        this.wordleValidator = wordleValidator;
        this.steps = 5;
        this.isLoss = true;
        this.userInput = userInput;
        this.alreadySuggested = alreadySuggested;
        this.hinter = hinter;
        this.answer = setupAnswer();
    }

    public void processGame() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Игра началась");
        System.out.println("Попробуйте угадать слово из 5-и букв");

        for (int i = 0; i <= steps; i++) {
            try {
                System.out.println("Введите слово");
                String userWordInput = scanner.nextLine();
                if (userWordInput.isEmpty()) {
                    String suggestion = hinter.generateSuggestion(answer);
                    alreadySuggested.add(suggestion);
                    System.out.println("Подсказка: " + suggestion);
                    continue;
                }

                userWordInput = wordleValidator.validateUserInput(userWordInput);
                userInput.add(userWordInput);

                if (userWordInput.equals(answer)) {
                    System.out.println("Вы выйграли");
                    isLoss = false;
                    break;
                } else {
                    String symbolicHint = hinter.generateSymbolicHint(userWordInput, answer);
                    System.out.println(symbolicHint);
                }

            } catch (InputOutOfLengthException e) {
                i--;
                System.out.println("Слово должно состоять из 5 букв");
            } catch (InputContainsSpacesException e) {
                i--;
                System.out.println("Слово не должно содержать пробелы");
            } catch (InputOutOfLanguageException e) {
                i--;
                System.out.println("Слово должно содержать символы русского алфавита");
            } catch (WordNotFoundInDictionary e) {
                i--;
                System.out.println("Введенное слово не найдено в словаре");
            }
        }
        if (isLoss) {
            System.out.println("Вы проиграли");
            System.out.println("Загаданное слово: " + answer);
        }
    }

    private String setupAnswer() {
        List<String> words = dictionary.getWords();
        Random random = new Random();
        this.answer = words.get(random.nextInt(words.size()));
        return answer;
    }

}
