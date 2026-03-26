package ru.yandex.practicum;

import java.util.Objects;


public class Attempt {

    private String word;
    private String result;

    public Attempt(String word, String result) {
        this.word = word;
        this.result = result;
    }

    public String getWord() {
        return word;
    }

    public String getResult() {
        return result;
    }

    @Override
    public String toString() {
        return word + " " + result;
    }

    @Override
    public boolean equals(Object o) {
        if (this.getClass() != o.getClass()) {
            return false;
        }
        if (this == o) {
            return true;
        }
        Attempt other = (Attempt) o;
        return this.word.equals(other.word) && this.result.equals(other.result);
    }

    @Override
    public int hashCode() {
        return Objects.hash(word, result);
    }
}
