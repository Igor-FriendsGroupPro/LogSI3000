package FriendsGroup.pro;

/*
Класс получает различные аргументы из командной строки
 */

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
//public String[][] block = new String [][];

    public static void main(String[] args) throws ParseException {
        boolean showFiles = false; // Показывать файлы
        String targetPath = ""; // Путь из аргумента
        String targetFile = ""; // Файл для парсинга
        Integer countFiles = 0; // Количество файлов
        long[] allDuration = new long[32];
        long[] allcauntCall = new long [32];

        // База данных
//        PostgresDB database = new PostgresDB();
//        database.setUrlDatabase("");
//        database.setNameDatabase("LogSI3000");
//        database.setLoginDatabase("admin");
//        database.setPasswordDatabase("password");
//        database.setnameTableInDatabase("LogTxt");
//
//        System.out.println("База данных доступна " + database.testDatabase());
//
//        database.ClearTable();


        // Перебор всех аргументов и определение задачи
        System.out.println("Передано аргументов: " + args.length);
        switch (args.length) {
            case 0: // аргументов не передано
                System.out.println("Ошибка: директория не получена");
                System.out.println("Синтаксис: java -jar LogSI3000.jar DIRECTORY [FILE]");
                break;
            case 1:
                targetPath = args[0]; // Получили путь
                System.out.println("Директория: " + targetPath);
                showFiles = true;

                // Проверка наличия пути и вывод результата
                if (targetPath.length() > 0) {
                    File objectsList[] = new File(targetPath).listFiles(); // Список объектов (файлов и директорий)

                    Day tempDay = new Day(); // Создали новый день
                    tempDay.setYear(1900);
                    tempDay.setMonth(1);
                    tempDay.setDay(30);

                    // Перебор всех объектов
                        for (File object : objectsList) {
                            countFiles = 0;
                            if (object.isFile()) {
                                targetFile = object.getName();

                                // Парсинг лог файла и определение количества блоков
                                LogFile tempLogFile = new LogFile();
                                if (tempLogFile.parsigFile(targetPath, targetFile)) {
                                    // Новый день?
                                    if (tempDay.getDDMMYYYY().compareTo(tempLogFile.dateFile) != 0) {
                                        //Вывод старого дня
                                        System.out.println();
                                        System.out.println("Дата: " + tempDay.getDDMMYYYY());
                                        System.out.printf("ЧНН %02d:00-%02d:00 входящих звонков %d",
                                                tempDay.getcHNN(), tempDay.getcHNN() + 1, tempDay.getCountInCall(tempDay.getcHNN()));
                                        System.out.println();

                                        for (int i = 0; i < 24; i++) {
                                            System.out.printf("%02d:00-%02d:00 %d %d %d %d", i, i+1,
                                                    tempDay.getCountInCall(i), tempDay.getDurationInCall(i),
                                                    tempDay.getCountOutCall(i), tempDay.getDurationOutCall(i));
                                            System.out.println();
                                        }

                                        // Новый день
                                        tempDay.setDay(tempLogFile.day);
                                        tempDay.setMonth(tempLogFile.month);
                                        tempDay.setYear(tempLogFile.year);
                                        for (int i = 0; i < 24; i++) {
                                            tempDay.setCountInCall(i, 0);
                                            tempDay.setCountOutCall(i, 0);
                                            tempDay.setDurationInCall(i, 0);
                                            tempDay.setDurationOutCall(i, 0);

                                        }

//                                    // Преребор блоков
                                        for (int i = 1; i <= tempLogFile.countBloks; i++) {
                                            // Анализ
                                            if (tempLogFile.defiant[i] == 0) {
                                                // Длительность входящего
                                                tempDay.setDurationInCall(tempLogFile.hourCall[i],
                                                        tempLogFile.callDuration[i]);
                                                tempDay.setCountInCall(tempLogFile.hourCall[i],
                                                        tempDay.getCountInCall(tempLogFile.hourCall[i])+1);
                                                System.out.printf("Входящий %02d:00", tempLogFile.hourCall[i]);
                                                System.out.println();

                                            } else {
                                                // Длительность исходящего
                                                tempDay.setDurationOutCall(tempLogFile.hourCall[i],
                                                        tempLogFile.callDuration[i]);
                                                tempDay.setCountOutCall(tempLogFile.hourCall[i],
                                                        tempDay.getCountOutCall(tempLogFile.hourCall[i])+1);
                                                System.out.printf("Исходящий %02d:00", tempLogFile.hourCall[i]);
                                                System.out.println();
                                            }
                                        }

                                    }
                                }
                            }
                            countFiles++;
                        }
                }
                break;
            case 2:
                targetPath = args[0]; // Получили путь
                System.out.println("Директория: " + targetPath);
                targetFile = args[1]; // Получили файл
                System.out.println("Файл: " + targetFile);

//                System.out.println("");
                LogFile tempLogFile = new LogFile();
                if (tempLogFile.parsigFile(targetPath, targetFile)) {
                    System.out.println("Найдено блоков: " + tempLogFile.countBloks);
                }
                break;
        }
    }

//    Сборка даты и времени в имя файла
    static String compileFileName(String dateTime) {
        Integer hour = Integer.parseInt(dateTime.substring(11, 13)) - 2; // Час предшествующий

        String NOD = "29773";
        String year = dateTime.substring(6, 10), month = dateTime.substring(3, 5), day = dateTime.substring(0, 2),
               minute = "00", number = "00??";
        return NOD + year + month + day + hour.toString() + minute + number;
    }

}