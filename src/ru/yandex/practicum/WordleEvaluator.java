package ru.yandex.practicum;

public class WordleEvaluator {
    public String evaluate(String guess, String answer) {
        char[] res = new char[5];
        int[] freq = new int[32];

        for (int i = 0; i < 5; i++) {
            char a = answer.charAt(i);
            freq[a - 'а']++;
        }

        for (int i = 0; i < 5; i++) {
            char g = guess.charAt(i);
            char a = answer.charAt(i);
            if (g == a) {
                res[i] = '+';
                freq[g - 'а']--;
            }
        }

        for (int i = 0; i < 5; i++) {
            if (res[i] == '+') continue;

            char g = guess.charAt(i);
            int idx = g - 'а';

            if (freq[idx] > 0) {
                res[i] = '^';
                freq[idx]--;
            } else {
                res[i] = '-';
            }
        }

        return new String(res);
    }
}
