package FriendsGroup.pro;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Log {
    /** Общее значение имени файла для всех копий объекта */
    private static String fileName;

    /** Возвращает имя файла */
    public String getFileName() {
        return fileName;
    }

    /** Установка общих параметров проекта для записи журнала логов в один файл
     * @param file Имя файла журнала логов
     * */
    public void setLogOptions(String file) {
        fileName = file;
    }

    /** Запись одной строки журнала логов без отступов
     * @param stringLine Строка для записи
     * */
    public void logEntry(String stringLine) {
        logEntry(0, stringLine);
    }

    /** Запись одной строки журнала логов с отступами
     * @param levelTabulation Количество отсупов (табуляций)
     * @param stringLine Строка для записи
     * */
    public void logEntry(int levelTabulation, String stringLine) {
        try(FileOutputStream fileLog = new FileOutputStream(fileName, true))
        {
            String journalLine = "";
            // Набор отступов}
            for (int i = 0; i < levelTabulation; i++) {
                journalLine = "\t" + journalLine;
            }
            // Добавление в строку даты и времени
            if (stringLine != null) {
                String dataTime = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(Calendar.getInstance().getTime());
                journalLine = journalLine + "[" + dataTime + "] " + stringLine + "\n";
            } else {
                // Перевод курсора на новую строчку
                journalLine = "\n";
            }
            // перевод строки в байты
            byte[] buffer = journalLine.getBytes();
            // Запись в файл
            fileLog.write(buffer);
        }
        catch(IOException ex)
        {
            System.out.println(ex.getMessage());
        }
    }
}
