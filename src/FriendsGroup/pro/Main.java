package FriendsGroup.pro;

import java.io.File;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Formatter;

public class Main {

    public static void main(String[] args) throws ParseException {
        String targetPath = "";                     // Путь из аргумента
        String targetFile = "";                     // Файл для парсинга
        String lastFile;                            // Последний обработаный файл на момент запуска
        String tableCalls = "calls";                // Таблица звонков
        String tableSubscribers = "subscribers";    // Таблица абонентов

        // Инициализация лог файла
        Log fileLog = new Log();
        fileLog.setLogOptions("LogSI3000.log");

        // База данных
        PostgresDB database = new PostgresDB();
        database.setDatabaseOptions("postgres", "postgres");

        // Создание таблицы
        try {
            database.createTableCalls(tableCalls);
            database.createTableAbonent(tableSubscribers);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        lastFile = database.getFileNameLast(tableCalls);
        //long lastIDCall = database.getIDLastCall();


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

                File fileList[] = new File(targetPath).listFiles(); // Список объектов (файлов и директорий)

                // Перебор всех объектов
                for (File object : fileList) {

                    if (object.isFile()) {
                        targetFile = object.getName();
                        ParsingLogFile tempParsingLogFile = new ParsingLogFile();
                        tempParsingLogFile.ParsingNameLogFile(targetFile);

                        // Обработка отсутствующего файла
                        if (!database.existEntry("NameLogFile", tempParsingLogFile.shortLogFileName, tableCalls)
                                || lastFile.equals(tempParsingLogFile.shortLogFileName)) {

                            if (tempParsingLogFile.parsigFile(targetPath, targetFile)) {
                                System.out.println("Обработан файл  " + tempParsingLogFile.shortLogFileName + " дата "
                                        + tempParsingLogFile.dateFile + " блоков " + tempParsingLogFile.countBloks);

                                // Преребор блоков
                                for (int i = 0; i <= tempParsingLogFile.countBloks; i++) {
//                                    System.out.println("ПорядковыйНомерЗвонка " + String.valueOf(tempParsingLogFile.SI[i]));
                                    if (!database.existEntry("CallID", String.valueOf(tempParsingLogFile.SI[i]), tableCalls)) {

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
                                        database.writeCall(tableCalls, tempPhoneRing);

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