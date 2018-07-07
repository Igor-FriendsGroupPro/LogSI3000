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
        String lastFile;        // Последний обработаный файл на момент запуска
        Integer countFiles = 0; // Количество файлов
        long allDuration = 0;
        long allCountCall = 0;
        int allDays = 0;
        String nameTable = "calls";
        String nameTableSubscribers = "subscribers";

        // База данных
        PostgresDB database = new PostgresDB();
        database.setParametrsDatabase("postgres", "postgres");
        // Создание таблицы
        try {
            database.createTableCalls(nameTable);
            database.createTableAbonent(nameTableSubscribers);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        lastFile = database.getFileNameLast(nameTable);
        //long lastIDCall = database.getIDLastCall();

        Day tempDay = new Day(); // Создали первый день


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

                File fileList[] = new File(targetPath).listFiles(); // Список объектов (файлов и директорий)

                // Перебор всех объектов
                for (File object : fileList) {

                    countFiles = 0;
                    if (object.isFile()) {
                        targetFile = object.getName();
                        ParsingLogFile tempParsingLogFile = new ParsingLogFile();
                        tempParsingLogFile.ParsingNameLogFile(targetFile);

                        // Обработка отсутствующего файла
                        if (!database.existEntry("NameLogFile", tempParsingLogFile.shortLogFileName, nameTable)
                                || lastFile.compareTo(tempParsingLogFile.shortLogFileName) == 0) {

                            if (tempParsingLogFile.parsigFile(targetPath, targetFile)) {
                                System.out.println("Обработан файл  " + tempParsingLogFile.shortLogFileName + " дата "
                                        + tempParsingLogFile.dateFile + " блоков " + tempParsingLogFile.countBloks);

                                // Преребор блоков
                                for (int i = 0; i <= tempParsingLogFile.countBloks; i++) {
//                                    System.out.println("ПорядковыйНомерЗвонка " + String.valueOf(tempParsingLogFile.SI[i]));
                                    if (!database.existEntry("CallID", String.valueOf(tempParsingLogFile.SI[i]), nameTable)) {

                                        // Создание отдельного звонка
                                        PhoneRing tempPhoneRing = new PhoneRing();
                                        tempPhoneRing.setCalledName(tempParsingLogFile.CN[i], tempParsingLogFile.A2[i]);
                                        tempPhoneRing.setDestinationName(tempParsingLogFile.DN[i], tempParsingLogFile.A0[i]);
                                        tempPhoneRing.setCallID(tempParsingLogFile.SI[i]);
                                        tempPhoneRing.setFileName(tempParsingLogFile.shortLogFileName);
                                        tempPhoneRing.setDateAndTime(tempParsingLogFile.YYYYDDMMHHmmss[i]);
                                        tempPhoneRing.setDuration(tempParsingLogFile.callDuration[i]);

                                        //System.out.println("ID звонка  " + tempPhoneRing.getCallID());

                                        // База данных таблица звонков
                                        database.writeCall(nameTable, tempPhoneRing);

                                    }
                                }
                            }

                        } else {
                            System.out.println("Данные из файла " + tempParsingLogFile.shortLogFileName + " уже есть в базе данных");
                        }// Обработка отсутствующего файла завершена
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

}