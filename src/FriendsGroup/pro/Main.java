package FriendsGroup.pro;

/*
Класс получает различные аргументы из командной строки
 */

import java.io.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Formatter;

public class Main {

    public static void main(String[] args) throws ParseException {
        boolean showFiles = false; // Показывать файлы
        String targetPath = ""; // Путь из аргумента
        String targetFile = ""; // Файл для парсинга
        Integer countFiles = 0; // Количество файлов
        long allDuration = 0;
        long allCountCall = 0;
        int allDays = 0;
        String reportFile = "";

        // База данных
        PostgresDB database = new PostgresDB();
        Day tempDay = new Day(); // Создали первый день
        database.setParametrsDatabase("postgres", "postgres");
//        System.out.println(database.getNameOfAbonent("220000"));
//        System.out.println(database.getIDLastCall());

        // Очистка таблицы
        database.clearTable("Calls");

        // Перебор всех аргументов и определение задачи
        System.out.println("Передано аргументов: " + args.length);
        Formatter line = new Formatter();

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
                                ParsingLogFile tempParsingLogFile = new ParsingLogFile();
                                if (tempParsingLogFile.parsigFile(targetPath, targetFile)) {
                                        System.out.println("Обработка даты: " + tempParsingLogFile.dateFile);
                                        // Преребор блоков
                                        for (int i = 0; i <= tempParsingLogFile.countBloks; i++) {
                                            // Анализ
                                            line = new Formatter();

                                            // Новый день?
                                            if (tempDay.getDDMMYYYY().compareTo(tempParsingLogFile.dateCall[i]) != 0) {
                                                line = new Formatter();
                                            }

                                            PhoneRing tempPhoneRing = new PhoneRing();
                                            tempPhoneRing.setCalledAbonent(tempParsingLogFile.CN[i], tempParsingLogFile.A2[i]);
                                            tempPhoneRing.setDefiantAbonent(tempParsingLogFile.DN[i], tempParsingLogFile.A0[i]);
                                            tempPhoneRing.setCallID(tempParsingLogFile.SI[i]);
                                            tempPhoneRing.setFileName(tempParsingLogFile.logFileName);
                                            tempPhoneRing.setDateAndTime(tempParsingLogFile.YYYYDDMMHHmmss[i]);
                                            tempPhoneRing.setDuration(tempParsingLogFile.callDuration[i]);

                                            // База данных
                                            line = new Formatter();
                                            database.setParametrsDatabase("postgres", "postgres");
                                            try {
                                                String nameTable = new String();
                                                nameTable = "Calls"; // + line.format("%d%02d", tempParsingLogFile.yearCall[i], tempParsingLogFile.monthCall[i]);
                                                database.createTableCalls(nameTable);
                                                database.writeCall(nameTable, tempPhoneRing);
                                            } catch (SQLException e) {
                                                e.printStackTrace();
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
                ParsingLogFile tempParsingLogFile = new ParsingLogFile();
                if (tempParsingLogFile.parsigFile(targetPath, targetFile)) {
                    System.out.println("Найдено блоков: " + tempParsingLogFile.countBloks);
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