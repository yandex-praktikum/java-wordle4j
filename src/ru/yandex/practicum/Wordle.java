package ru.yandex.practicum;
import java.util.Scanner;
/*
в главном классе нам нужно:
    создать лог-файл (он должен передаваться во все классы)
    создать загрузчик словарей WordleDictionaryLoader
    загрузить словарь WordleDictionary с помощью класса WordleDictionaryLoader
    затем создать игру WordleGame и передать ей словарь
    вызвать игровой метод в котором в цикле опрашивать пользователя и передавать информацию в игру
    вывести состояние игры и конечный результат
 */

public class Wordle {

    public static void main(String[] args) {

        WordleDictionaryLoader loader = new WordleDictionaryLoader(); // создание загрузчика словарей WordleDictionaryLoader

        WordleDictionary dictionary = loader.load("words_ru.txt"); // чтение файла и загрузка его в лист
        System.out.println(dictionary.size()); // проверка что словарь загрузился - 67763 слова

        WordleGame game = new WordleGame(dictionary); // создание игры
        Scanner scanner = new Scanner(System.in); // создание сканера чтобы читать слово пользователя из консоли

        while (!game.gameOver()) {
            System.out.println("Введите слово");
            String guess = scanner.nextLine(); // считываем, что ввел пользователь.. надо бы проверить длину слова
            String hint = game.makeMove(guess); // сделать ход, выводим подсказу получается
            System.out.println(hint);

            if (game.isWin(guess)) {
                System.out.println("Поздравляем! Вы победили! ");
                break;
            }
        }

        System.out.println("Игра завершена");



    }

}
