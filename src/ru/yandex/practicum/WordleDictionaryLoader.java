package ru.yandex.practicum;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/*
этот класс содержит в себе всю рутину по работе с файлами словарей и с кодировками
    ему нужны методы по загрузке списка слов из файла по имени файла
    на выходе должен быть класс WordleDictionary
 */
public class WordleDictionaryLoader {
   private File logFile; 

    public WordleDictionaryLoader(File logFile) {
        this.logFile = logFile;
    }
    List<String> reader(File file)  {
        List<String> list = new ArrayList<>();
        try (FileReader reader = new FileReader(file);
             BufferedReader br = new BufferedReader(reader)) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.length() == 5) {
                    list.add(line);
                }
            }
        } catch (Exception e) {
            saveLogError(e);
        }
        return list;
    }

    private void saveLogError(Exception e) {
        String time = LocalDateTime.now().toString();
        try (FileWriter writer = new FileWriter(logFile, true)) {
            writer.write(e + "\n");
            writer.write(time + "\n");
            writer.write(("=").repeat(20) + "\n");
        } catch (IOException ioException) {
        }
    }
}
