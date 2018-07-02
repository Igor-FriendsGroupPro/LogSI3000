package FriendsGroup.pro;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ParsingLogFile {
    // Общие данные
    String logFullFileName; // Полный путь к файлу
    String logFileName;     // Только имя файла
    String shortLogFileName;// Только короткое имя файла без .txt
    String logDirectory;    // Директория с файлами
    String nOD;             // Номер нода
    String dateFile;        // Дата и время создания файла
    int numberFile;         // Порядковый номер файла
    int countBloks;         // Количество блоков
    int year;   // год
    int month;  // месяц
    int day;    // день

    // Данные из блока
    int[] SI = new int[500];           //ID звонка
    int[] CI = new int[500];           //Какой-то внутренний номер

    String[] Direction = new String[500];     //Направление звонка

    String[] DN = new String[500];     //Номер исходящий
    String[] DName = new String[500];     //Имя абонента
    boolean[] DNSide = new boolean[500];     //Внутренний абонент

    String[] CN = new String[500];     //Номер назначения
    String[] CName = new String[500];     //Имя абонента
    boolean[] CNSide = new boolean[500];     //Внутренний абонент

    String[] SD = new String[500];     //Дата и время начала звонка
    String[] TI = new String[500];     //ХЗ
    String[] PI = new String[500];     //ХЗ
    String[] CI113 = new String[500];  //ХЗ
    int[] yearCall = new int[500];     // год
    int[] monthCall = new int[500];    // месяц
    int[] dayCall = new int[500];      // день
    String[] YYYYDDMMHHmmss = new String[500];      //Дата звонка
    String[] dateCall = new String[500];      //Дата звонка
    String[] timeCall = new String[500];      //Время звонка
    int[] hourCall = new int[500];     //Час звонка
    String[] ED = new String[500];     //Дата и время окончания звонка
    String[] A0 = new String[500];     //IP вызывающего
    String[] A2 = new String[500];     //IP назначения
    int[] defiant = new int[500];      //Вызывающий абонент
                                        //    0 - Ростелеком. Для нас входящий
                                        //    112 - ЦОВ

    int[] callDuration = new int[500]; //Длительность звонка

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
//                            CI[countBloks] = Integer.parseInt(strLine.substring(strLine.indexOf("CI") + 4,
//                                    strLine.indexOf("FL") - 2));
                            // Обработка анонимных звонков
                            if (strLine.indexOf(">") - strLine.indexOf("DN") == 5) {
                                DN[countBloks] = "0";
//                                DName[countBloks] = "0";

//                                DNSide[countBloks] = false;
                            } else {
                                DN[countBloks] = strLine.substring(strLine.indexOf("DN") + 4,
                                        strLine.indexOf(">") - 1);
                                Abonent tempAbonent = new Abonent();
                                DName[countBloks] = tempAbonent.getNameAbonent(DN[countBloks]);
//                                DNSide[countBloks] = tempAbonent.getSide(DN[countBloks]);
                            }

                            break;
                        case "<I100":
                            CN[countBloks] = strLine.substring(strLine.indexOf("CN") + 4, strLine.indexOf(">") - 2);
                            Abonent tempAbonent = new Abonent();
                            CName[countBloks] = tempAbonent.getNameAbonent(CN[countBloks]);
//                            CNSide[countBloks] = tempAbonent.getSide(CN[countBloks]);

                            // Пропустить блок с переадресацией от ЕДДС на ЦОВ
                            if (CN[countBloks].compareTo("20093") == 0) {
                                countBloks--;
                                while ( strLine.substring(0, 6).compareTo("</R200") != 0 )
                                {
                                    strLine = br.readLine();
                                }
                            }

                            break;
                        case "<I102":
                            SD[countBloks] = strLine.substring(strLine.indexOf("SD") + 4, strLine.indexOf("FL") - 2);
                            yearCall[countBloks] = Integer.parseInt(SD[countBloks].substring(0, 4));
                            monthCall[countBloks] = Integer.parseInt(SD[countBloks].substring(5, 7));
                            dayCall[countBloks] = Integer.parseInt(SD[countBloks].substring(8, 10));
                            dateCall[countBloks] = SD[countBloks].substring(8, 10) + "."
                                    + SD[countBloks].substring(5, 7) + "."
                                    + SD[countBloks].substring(0, 4);
                            hourCall[countBloks] = Integer.parseInt(SD[countBloks].substring(11, 13));
                            timeCall[countBloks] = SD[countBloks].substring(11, 19);

                            YYYYDDMMHHmmss[countBloks] = SD[countBloks].substring(0, 4) +
                                    SD[countBloks].substring(5, 7) +
                                    SD[countBloks].substring(8, 10) +
                                    SD[countBloks].substring(11, 13) +
                                    SD[countBloks].substring(14, 16) +
                                    SD[countBloks].substring(17, 19);


                            break;
/*                        case "<I103":
                            ED[countBloks] = strLine.substring(strLine.indexOf("ED") + 4, strLine.indexOf("FL") - 2);
                            break;
                        case "<I113":
                            TI[countBloks] = strLine.substring(strLine.indexOf("TI") + 4, strLine.indexOf("MI") - 2);
                            PI[countBloks] = strLine.substring(strLine.indexOf("PI") + 4, strLine.indexOf("CI") - 2);
                            CI113[countBloks] = strLine.substring(strLine.indexOf("CI") + 4, strLine.indexOf(">") - 2);
                            break;
*/
                        case "<I115":
                            callDuration[countBloks] = Integer.parseInt(strLine.substring(strLine.indexOf("DU") + 4, strLine.indexOf(">") - 2));
                            break;
                        case "<I127":
                            A0[countBloks] = strLine.substring(strLine.indexOf("A0") + 4, strLine.indexOf("A2") - 2);

/*                            switch (A0[countBloks]) {
                                case "172.20.212.64": // Входящий от Ростелекома
                                    defiant[countBloks] = 0;
//                                    System.out.println(hourCall[countBloks] + " из " + SD[countBloks]);
                                    break;
                                    default:
                                        defiant[countBloks] = -1; // Неизвестный
                                        break;
                            }
*/
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

    void ParsingNameLogFile (String fileName) {

        if ((fileName.length() == 25) && fileName.endsWith(".txt")) {
            nOD = fileName.substring(0, 5);
            logFileName = fileName;
            shortLogFileName = fileName.substring(0, 21);
            year = Integer.parseInt(logFileName.substring(5, 9));
            month = Integer.parseInt(logFileName.substring(9, 11));
            day = Integer.parseInt(logFileName.substring(11, 13));
            numberFile = Integer.parseInt(logFileName.substring(17, 21));

            dateFile = logFileName.substring(11, 13) + "." +
                    logFileName.substring(9, 11) + "." +
                    logFileName.substring(5, 9);
        }

    }
}
