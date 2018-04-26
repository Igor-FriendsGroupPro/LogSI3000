package FriendsGroup.pro;

/*
Класс получает различные аргументы из командной строки
 */

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
//public String[][] block = new String [][];

    public static void main(String[] args) {
        boolean showFiles = false; // Показывать файлы
        String targetPath = ""; // Путь из аргумента
        String targetFile = ""; // Файл для парсинга
        Integer countFiles = 0; // Количество файлов

        // Перебор всех аргументов и определение задачи
        System.out.println("Передано аргументов: " + args.length);
        switch (args.length) {
            case 0:
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
                        if (object.isFile()) {
                            targetFile = object.getName();

                            LogFile tempLogFile = new LogFile();
                            System.out.println("");
                            if (tempLogFile.parsigFile(targetPath, targetFile)) {
                                System.out.println("Найдено блоков: " + tempLogFile.countBloks);
                            }

                            // Получение последнего файла на текущий момент
//                    Date date = new Date();
//                    String newDateTime = new SimpleDateFormat("dd.MM.yyyy HH:mm").format(date);
//                    System.out.println(compileFileName(newDateTime));
//                            System.out.println(getBlock(targetPath + "\\" + targetFile));

                            countFiles++;
                        }
                    }
                    System.out.println("");
                    System.out.println("Найдено файлов: " + countFiles);
                }
                break;
            case 2:
                targetPath = args[0]; // Получили путь
                System.out.println("Директория: " + targetPath);
                targetFile = args[1]; // Получили файл
                System.out.println("Файл: " + targetFile);

                System.out.println("");
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