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
        PostgresDB database = new PostgresDB();
        database.setUrlDatabase("");
        database.setNameDatabase("LogSI3000");
        database.setLoginDatabase("admin");
        database.setPasswordDatabase("password");
        database.setnameTableInDatabase("LogTxt");

        System.out.println("База данных доступна " + database.testDatabase());

        database.ClearTable();


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

//                    for (int j=10; j<=30; j++) {
                        long countCall = 0;
                        long avg = 0;

                        String condition = "2018-04";

                        // Перебор всех объектов
                        for (File object : objectsList) {
                            countFiles = 0;
                            if (object.isFile()) {
                                targetFile = object.getName();

                                // Парсинг лог файла и определение количества блоков
                                LogFile tempLogFile = new LogFile();
                                if (tempLogFile.parsigFile(targetPath, targetFile)) {

                                    // Преребор блоков
                                    for (int i = 1; i <= tempLogFile.countBloks; i++) {
                                        // Условие отбора
                                        if (tempLogFile.defiant[i] == 0 & tempLogFile.SD[i].startsWith(condition)) {
                                            countCall++;
                                            allDuration[Integer.parseInt(tempLogFile.SD[i].substring(8, 10))] =
                                                    allDuration[Integer.parseInt(tempLogFile.SD[i].substring(8, 10))] +
                                                            tempLogFile.callDuration[i];
                                            allcauntCall[Integer.parseInt(tempLogFile.SD[i].substring(8, 10))] =
                                                    allcauntCall[Integer.parseInt(tempLogFile.SD[i].substring(8, 10))] +
                                                            1;
//                                            System.out.println(tempLogFile.A0[i] + " " + tempLogFile.SD[i] + " длительностью " + tempLogFile.callDuration[i] + " сек");
//                                            System.out.println(allDuration);
                                        }
                                    }
                                }
                            }
                            countFiles++;
                        }
                    for (int i = 1 ; i<=30; i++){
                        System.out.println("2018-04-" + i + " " + allcauntCall[i] + " " + allDuration[i] + " " + allDuration[i]/allcauntCall[i]);
                    }

                        //                    System.out.println("");
//                    System.out.println("Найдено файлов: " + countFiles);
//                        avg = allDuration / countCall;
//                        System.out.println(countCall + " " + allDuration +" " + avg);
//                    }
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