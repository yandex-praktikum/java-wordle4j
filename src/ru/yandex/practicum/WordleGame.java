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

        public WordleGame(WordleDictionary dictionary) {
            this.dictionary = dictionary;
            this.answer = dictionary.normalizeWord(dictionary.getRandomWord());
            this.attempts = 0;
        }


        private String answer; // загаданное программой слово
        private int attempts; // количество его попыток (не больше 6, далее игра завершается)
        private static final int MAX_ATTEMPTS = 5;

        private WordleDictionary dictionary; // (словарь игры)

            public String makeMove(String guess) { //
                guess = dictionary.normalizeWord(guess);

                if (guess.length() != MAX_ATTEMPTS) {
                    return "Слово должно содержать 5 букв";
                }
                if (!dictionary.contains(guess)) {
                    throw new WordNotFoundException("Такого слова нет в словаре :(");
                }
                attempts++;
                return generateHint(guess);
            }

            public String generateHint(String guess) { // создание подсказки для пользователя: + если буква есть и на нужном месте, - если такой буквы нет, ^ если буква есть,и но на другом месте в загаднном слове
                    String hint = "";

                    for (int i = 0; i < guess.length(); i++) { // циклом пройтись по буквам слова пользователя и далее сравнить
                        char guessChar = guess.charAt(i); // буква пользователя
                        char answerChar = answer.charAt(i); // буква правильного ответа

                        if (guessChar == answerChar) {
                            hint += "+"; // буква верная и на правильном месте

                        } else if (answer.contains(String.valueOf(guessChar))) {
                            hint += "^"; // буква верная но на неправильном месте

                        } else {
                            hint += "-"; // такой буквы нет
                        }
                    }

                    return hint; // ну и вывод подсказки
                }

            public boolean isWin(String guess) { // совпадает с ответом?
                return dictionary.normalizeWord(guess).equals(answer);
                }


            public boolean gameOver() {
               return attempts >= MAX_ATTEMPTS;
            }

    }

    // + EXCEPTIONS!!!!
