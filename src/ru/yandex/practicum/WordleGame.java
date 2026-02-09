package ru.yandex.practicum;

import java.util.Map;
import java.util.*;

public class WordleGame {
    public static final int MAX_STEPS = 6;
    public static final int WORD_LENGTH = 5;
    private Logger logger;
    private String answer;
    private int steps;
    private int maxWordLength;
    private WordleDictionary dictionary;
    private Set<Character> forbiddenLetters;
    private Set<Character> requiredLetters;
    private HashMap<Integer, Character> requiredPositionsForLetters;
    private Set<String> wordsUsed;
    private HashMap<Character, Set<Integer>> forbiddenPositionsByLetter;
    private Set<String> possibleWords;
    private Random random;
    private boolean gameWin;
    private boolean gameOver;


    WordleGame(WordleDictionary dictionary, Logger logger) {
        this.logger = logger;
        this.random = new Random();
        this.steps = MAX_STEPS;
        this.maxWordLength = WORD_LENGTH;
        this.dictionary = dictionary;
        this.answer = generateAnswer();
        this.requiredLetters = new HashSet<>();
        this.forbiddenLetters = new HashSet<>();
        this.requiredPositionsForLetters = new HashMap<>();
        this.wordsUsed = new HashSet<>();
        this.forbiddenPositionsByLetter = new HashMap<>();
        this.possibleWords = new HashSet<>(dictionary.getWords());
        this.gameWin = false;
        this.gameOver = false;
    }

    private String generateAnswer() {
        String answer = dictionary.getRandomWord();
        logger.info("Игра началась");
        logger.info("Компьютер загадал: " + answer);
        return answer;
    }

    private String evaluateAttempt(String word) {
        String comparisonResult = compareWithAnswer(word, getAnswer());
        updateGameKnowledge(comparisonResult, word);
        return comparisonResult;
    }

    private String compareWithAnswer(String word, String hiddenWord) {
        if (word.equals(hiddenWord)) {
            return ("+".repeat(word.length()));
        }

        StringBuilder builder = new StringBuilder("-".repeat(word.length()));
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == hiddenWord.charAt(i)) {
                builder.replace(i, i + 1, "+");
            }
        }

        for (int i = 0; i < word.length(); i++) {
            if (builder.charAt(i) == '-') {
                if (hiddenWord.contains(Character.toString(word.charAt(i)))) {
                    builder.replace(i, i + 1, "^");
                }
            }
        }
        String comparisonResult = builder.toString();
        logger.info("Результат сравнения: " + comparisonResult);
        return comparisonResult;
    }

    private void updateGameKnowledge(String compareAttemptResult, String word) {
        for (int i = 0; i < compareAttemptResult.length(); i++) {
            if (compareAttemptResult.charAt(i) == '^') {
                addRequiredLetter(word.charAt(i));
                addForbiddenPositionsForLetter(word.charAt(i), i);
            } else if (compareAttemptResult.charAt(i) == '+') {
                addIndexRequiredLetter(i, word.charAt(i));
            }
        }

        for (int i = 0; i < word.length(); i++) {
            if (compareAttemptResult.charAt(i) == '-') {
                addForbiddenLetter(word.charAt(i));
            }
        }
        logger.debug(new StringBuilder().append("Состояние символьных переменных после попытки: ").append("\n")
                .append("requiredLetters: ").append(requiredLetters).append("\n")
                .append("forbiddenLetters: ").append(forbiddenLetters).append("\n")
                .append("requiredPositionsForLetters: ").append(requiredPositionsForLetters).append("\n")
                .append("forbiddenPositionsByLetter: ").append(forbiddenPositionsByLetter).append("\n")
                .toString()
        );
    }

    public String process(String word) throws GameException {
        if (answer.equals(word)) {
            setWonState();
        }

        String result = "";

        if (isValidWordToAttempt(word)) {
            result = evaluateAttempt(word);
            updatePossibleWords();
            addWordUsed(word);
            decrementAttempts();
        }

        if (getSteps() <= 0) {
            setIsGameOver();
        }

        return result;
    }

    public String makeAttempt(String word) throws GameException {
        logger.info("Игрок ввел слово: " + word);
        String normalizedWord = normalized(word);

        return process(normalizedWord);
    }

    public ArrayList<String> requestHint() throws GameException {
        logger.info("Игрок запросил подсказку");
        ArrayList<String> suggestion = useSuggestion();
        String suggestionWord = suggestion.getFirst();
        process(suggestionWord);

        return suggestion;
    }

    private ArrayList<String> useSuggestion() throws GameException {
        ArrayList<String> hintData = new ArrayList<>();
        String suggest = suggestWord();

        logger.info("Подсказка: " + suggest);

        hintData.add(suggest);
        hintData.add(evaluateAttempt(suggest));

        return hintData;
    }

    private void updatePossibleWords() throws GameException {
        possibleWords.removeIf(word -> !isValidSuggestion(word));
        if (!possibleWords.contains(answer)) {
            throw new WordNotFoundInDictionaryException("Словарь не содержит загаданное слово");
        }
    }

    private String suggestWord() throws GameException {
        if (possibleWords.isEmpty()) {
            throw new NoSuggestionsAvailableException("Словарь подсказок пуст");
        }

        List<String> wordsList = getPossibleWords();
        int index = random.nextInt(wordsList.size());

        return wordsList.get(index);
    }

    private String normalized(String word) {
        if (word == null || word.isBlank()) {
            return word;
        }
        return word.toLowerCase().replace("ё", "е");
    }

    private boolean matchesRequiredPositions(String wordToCheck) {
        for (Map.Entry<Integer, Character> entry : requiredPositionsForLetters.entrySet()) {
            if (!entry.getValue().equals(wordToCheck.charAt(entry.getKey()))) {
                return false;
            }
        }
        return true;
    }

    private boolean isInDictionary(String wordToCheck) {
        return dictionary.containsWord(wordToCheck);
    }

    private boolean containsAllRequiredLetters(String wordToCheck) {
        for (Character character : requiredLetters) {
            if (!wordToCheck.contains(character.toString())) {
                return false;
            }
        }
        return true;
    }

    private boolean hasNoLettersOnForbiddenPositions(String wordToCheck) {
        for (int i = 0; i < wordToCheck.length(); i++) {
            Character letter = wordToCheck.charAt(i);
            if (forbiddenPositionsByLetter.containsKey(letter)) {
                Set<Integer> indexes = forbiddenPositionsByLetter.get(letter);
                if (indexes.contains(i)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isValidWordToAttempt(String wordToCheck) throws GameException {
        if (!isCorrectLength(wordToCheck)) {
            throw new InvalidWordLengthException("Слово некорректной длины");
        }
        if (!isNotUsed(wordToCheck)) {
            throw new WordAlreadyUsedException("Слово уже использовалось");
        }

        if (!isInDictionary(wordToCheck)) {
            throw new WordNotInDictionaryException("Такого слова нет в словаре");
        }

        return true;
    }

    private boolean isValidSuggestion(String word) {
        return hasNoForbiddenLetters(word)
                && containsAllRequiredLetters(word)
                && matchesRequiredPositions(word)
                && hasNoLettersOnForbiddenPositions(word)
                && isNotUsed(word);
    }

    private boolean hasNoForbiddenLetters(String wordToCheck) {
        for (int i = 0; i < wordToCheck.length(); i++) {
            if (forbiddenLetters.contains(wordToCheck.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private boolean isCorrectLength(String wordToCheck) {
        return wordToCheck.length() == maxWordLength;
    }

    private boolean isNotUsed(String wordToCheck) {
        return !wordsUsed.contains(wordToCheck);
    }

    public boolean gameWin() {
        return gameWin;
    }

    public boolean gameOver() {
        return gameOver;
    }

    public void addForbiddenPositionsForLetter(char letter, int position) {
        forbiddenPositionsByLetter.computeIfAbsent(letter, k -> new HashSet<>()).add(position);
    }

    public void addWordUsed(String word) {
        wordsUsed.add(word);
    }

    public void addIndexRequiredLetter(int index, Character letter) {
        requiredPositionsForLetters.put(index, letter);
    }

    public void addForbiddenLetter(Character letter) {
        forbiddenLetters.add(letter);
    }

    public void addRequiredLetter(Character letter) {
        requiredLetters.add(letter);
    }

    public int getSteps() {
        return steps;
    }

    public List<String> getPossibleWords() {
        return new ArrayList<>(possibleWords);
    }

    public String getAnswer() {
        return answer;
    }

    private void decrementAttempts() {
        steps--;
    }

    private void setWonState() {
        this.gameWin = true;
    }

    private void setIsGameOver() {
        gameOver = true;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
