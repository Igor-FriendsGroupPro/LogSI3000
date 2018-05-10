package FriendsGroup.pro;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class LogFile {
    // Общие данные
    String logFullFileName; // Полный путь к файлу
    String logFileName;     // Только имя файла
    String logDirectory;    // Директория с файлами
    String nOD;             // Номер нода
    String dateFile;    // Дата и время создания файла
    int numberFile;         // Порядковый номер файла
    int countBloks;         // Количество блоков
    int year;   // год
    int month;  // месяц
    int day;    // день

    // Данные из блока
    int[] SI = new int[1000];           //Номер по порядку
    int[] CI = new int[1000];           //Какой-то внутренний номер
    String[] DN = new String[1000];     //Номер исходящий
    String[] CN = new String[1000];     //Номер назначения
    String[] SD = new String[1000];     //Дата и время начала звонка
    int[] hourCall = new int[1000];     //Час звонка
    String[] ED = new String[1000];     //Дата и время окончания звонка
    String[] A0 = new String[1000];     //IP вызывающего
    String[] A2 = new String[1000];     //IP назначения
    int[] defiant = new int[1000];      //Вызывающий абонент
                                        //    0 - Ростелеком. Для нас входящий
                                        //    112 - ЦОВ

    int[] callDuration = new int[1000]; //Длительность звонка

    // Разбор файла
    Boolean parsigFile (String directory, String fileName){

        // Разбор имени файла
        if ((fileName.length() == 25) && fileName.endsWith(".txt")) {
            nOD = fileName.substring(0, 5);
            logDirectory = directory;
            logFileName = fileName;
            logFullFileName = logDirectory + "\\" + logFileName;
//            System.out.println(logFileName);

            year = Integer.parseInt(logFileName.substring(5, 9));
            month = Integer.parseInt(logFileName.substring(9, 11));
            day = Integer.parseInt(logFileName.substring(11, 13));
            numberFile = Integer.parseInt(logFileName.substring(17, 21));

            dateFile = logFileName.substring(11, 13) + "." +
                            logFileName.substring(9, 11) + "." +
                            logFileName.substring(5, 9);

            // Чтение блоков
            countBloks = -1;
            String tegLine;
            try {
                FileInputStream fstream = new FileInputStream(logFullFileName);
                BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
                String strLine;
                while ((strLine = br.readLine()) != null) {
//                    System.out.println(strLine);
                    tegLine = strLine.substring(0, 5);
                    switch (tegLine){
                        case "<R200":
                            countBloks++;
                            SI[countBloks] = Integer.parseInt(strLine.substring(strLine.indexOf("SI") + 4,
                                    strLine.indexOf("CI") - 2));
                            CI[countBloks] = Integer.parseInt(strLine.substring(strLine.indexOf("CI") + 4,
                                    strLine.indexOf("FL") - 2));
                            // Обработка анонимных звонков
                            if (strLine.indexOf(">") - strLine.indexOf("DN") == 5) {
                                DN[countBloks] = "anonim";
                            } else {
                                DN[countBloks] = strLine.substring(strLine.indexOf("DN") + 4,
                                        strLine.indexOf(">") - 2);
                            }
                            break;
                        case "<I100":
                            CN[countBloks] = strLine.substring(strLine.indexOf("CN") + 4, strLine.indexOf(">") - 2);
                            break;
                        case "<I102":
                            SD[countBloks] = strLine.substring(strLine.indexOf("SD") + 4, strLine.indexOf("FL") - 2);
                            hourCall[countBloks] = Integer.parseInt(SD[countBloks].substring(11, 13));
//                            System.out.println(hourCall[countBloks] + " из " + SD[countBloks]);
                            break;
                        case "<I103":
                            ED[countBloks] = strLine.substring(strLine.indexOf("ED") + 4, strLine.indexOf("FL") - 2);
                            LogDateTime tempDT = new LogDateTime();
                            callDuration[countBloks] = tempDT.duration(SD[countBloks], ED[countBloks]);
                            break;
                        case "<I127":
                            A0[countBloks] = strLine.substring(strLine.indexOf("A0") + 4, strLine.indexOf("A2") - 2);

                            switch (A0[countBloks]) {
                                case "172.20.212.64": // Входящий от Ростелекома
                                    defiant[countBloks] = 0;
                                    break;
                                    default:
                                        defiant[countBloks] = -1; // Неизвестный
                                        break;
                            }

                            A2[countBloks] = strLine.substring(strLine.indexOf("A2") + 4, strLine.indexOf(">") - 2);
                            break;
                    }
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }


            return true; // Разбор файла удался
        } else {
            return false; // Разбор файла не удался
        }


    }

}
