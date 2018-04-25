package FriendsGroup.pro;

/*
Класс получает различные аргументы из командной строки
 */

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {

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
                break;
            case 2:
                targetPath = args[0]; // Получили путь
                System.out.println("Директория: " + targetPath);
                targetFile = args[1]; // Получили файл
                System.out.println("Файл: " + targetFile);
                System.out.println(targetFile.length());
                break;
        }

        // Проверка наличия пути и вывод результата
        if (showFiles && (targetPath.length() > 0)) {
            File objectsList[] = new File(targetPath).listFiles(); // Список объектов (файлов и директорий)

            // Перебор всех объектов
            for (File object : objectsList) {
                if (showFiles && object.isFile()) {
                    System.out.println(object.getName() + " " + parsigFileName(object.getName(), "date"));
                    Date date = new Date();

                    String newDateTime = new SimpleDateFormat("dd.MM.yyyy HH:mm").format(date);
                    System.out.println(compileFileName(newDateTime));

                    countFiles++;
                }
            }
            System.out.println("Найдено файлов: " + countFiles);
        }
    }

//    Разбор имени файла в дату и время
    static String parsigFileName(String fileName, String parametr) {
        Date date = new Date();
        String answer = "";

        if (fileName.length() >= 21) {

            switch (parametr) {
                case "NOD": // Номер нода
                    answer = fileName.substring(0, 5);
                    break;
                case "year": // Год
                    answer = fileName.substring(5, 9);
                    break;
                case "month": //Месяц
                    answer = fileName.substring(9, 11);
                    break;
                case "day": //День
                    answer = fileName.substring(11, 13);
                    break;
                case "time": //Время
                    answer = fileName.substring(13, 15) + ":" + fileName.substring(15, 17);
                    break;
                case "number": // Порядковый номер
                    answer = fileName.substring(17, 21);
                    break;
                case "date": // Дата в удобном виде
                    answer = fileName.substring(11, 13) + "." +
                            fileName.substring(9, 11) + "." +
                            fileName.substring(5, 9) + " " +
                            fileName.substring(13, 15) + ":" + fileName.substring(15, 17);
                    break;
            }
        }
        return answer;
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