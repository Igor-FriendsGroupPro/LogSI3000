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
        long allDuration = 0;
        long allCountCall = 0;
        int allDays = 0;

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


        Day tempDay = new Day(); // Создали первый день

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


                    // Перебор всех объектов
                        for (File object : objectsList) {
                            countFiles = 0;
                            if (object.isFile()) {
                                targetFile = object.getName();

                                // Парсинг лог файла и определение количества блоков
                                LogFile tempLogFile = new LogFile();
                                if (tempLogFile.parsigFile(targetPath, targetFile)) {

//                                    // Преребор блоков
                                        for (int i = 0; i <= tempLogFile.countBloks; i++) {
                                            // Анализ

                                                System.out.println(tempLogFile.SD[i] + "\t" +
                                                        tempLogFile.DN[i] + "\t" + tempLogFile.CN[i] + "\t" + tempLogFile.callDuration[i] + "\t" +
                                                        tempLogFile.DName[i] + " > " + tempLogFile.CName[i]);

                                            // Новый день?
                                            if (tempDay.getDDMMYYYY().compareTo(tempLogFile.dateCall[i]) != 0) {
                                                //Вывод старого дня
                                                tempDay.print();
                                                allDuration = allDuration + tempDay.getDurationCallInDay();
                                                allCountCall = allCountCall + tempDay.getCountCallInDay();
                                                allDays++;
                                                // Новый день
                                                tempDay.clear();
                                                tempDay.setDay(tempLogFile.dayCall[i]);
                                                tempDay.setMonth(tempLogFile.monthCall[i]);
                                                tempDay.setYear(tempLogFile.yearCall[i]);
                                            }

//                                            System.out.println("Дата звонка: " + tempLogFile.dateFile + " " + tempLogFile.hourCall[i]);
                                                if (tempLogFile.defiant[i] == 0) {
                                                    // Длительность входящего
                                                    tempDay.setDurationInCall(tempLogFile.hourCall[i],
                                                            tempLogFile.callDuration[i]);
                                                    tempDay.setCountInCall(tempLogFile.hourCall[i],
                                                            tempDay.getCountInCall(tempLogFile.hourCall[i]) + 1);

                                                } else {
                                                    // Длительность исходящего
                                                    tempDay.setDurationOutCall(tempLogFile.hourCall[i],
                                                            tempLogFile.callDuration[i]);
                                                    tempDay.setCountOutCall(tempLogFile.hourCall[i],
                                                            tempDay.getCountOutCall(tempLogFile.hourCall[i]) + 1);
                                                }

                                        }
                                }
                            }
                            countFiles++;
                        }
                    //Вывод старого дня
                    tempDay.print();
//                    System.out.println();
//                    System.out.println("Всего дней: " + allDays);
//                    System.out.println("Всего звонков: " + allCountCall + " продолжительностью: " + allDuration);

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