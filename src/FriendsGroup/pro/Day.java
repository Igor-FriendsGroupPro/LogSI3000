package FriendsGroup.pro;

import java.util.Formatter;

public class Day {
        // Данные суток
    private int year;   // год
    private int month;  // месяц
    private int day;    // день
    private int countCall;          //Всего звонков
    private int durationCall;     //Продолжительность общая

        // Данные по часам
    private int[] countOutCall = new int[24];       //Исходящих звонков
    private int[] countInCall = new int[24];        //Входящих звонков
    private int[] durationOutCall = new int[24];  //Продолжительность исходящих звонков
    private int[] durationInCall = new int[24];   //Продолжительность входящих звонков

    public void clear() {
        year = 0;
        month = 0;
        day = 0;
        countCall = 0;
        durationCall = 0;
        for (int i = 0; i < 24; i++) {
            countOutCall[i] = 0;
            countInCall[i] = 0;
            durationOutCall[i] = 0;
            durationInCall[i] = 0;
        }
    }

    public void print(String nameFile) {
        Formatter line = new Formatter();
        if (day != 0) {
            Log reportFile = new Log();
            System.out.println();

            line.format("Дата: " + getDDMMYYYY());
            System.out.println(line.toString());
//            reportFile.writeLine(nameFile, line.toString(), false);
//            reportFile.writeLine(nameFile, "\n", false);

            line = new Formatter();
            line.format("Всего звонков: %d продолжительностью %d сек", getCountCallInDay(), getDurationCallInDay());
            System.out.println(line.toString());
//            reportFile.writeLine(nameFile, line.toString(), false);
//            reportFile.writeLine(nameFile, "\n", false);
            //System.out.println("Среднее время входящих звонков: " + getAvgDurationCallInDay() + " сек");

            line = new Formatter();
            line.format("ЧНН %02d:00-%02d:00 входящих звонков %d", getcHNN(), getcHNN() + 1, getCountInCall(getcHNN()));
            System.out.println(line);
//            reportFile.writeLine(nameFile, line.toString(), false);
//            reportFile.writeLine(nameFile, "\n", false);

            line = new Formatter();
            line.format("Время\tВх\tДл Вх\tИсх\tДл Исх");
            System.out.println(line);
            for (int clock = 0; clock < 24; clock++) {
                line = new Formatter();
                line.format("%02d:00-%02d:00\t%d\t%d\t%d\t%d", clock, clock + 1,
                        getCountInCall(clock), getDurationInCall(clock),
                        getCountOutCall(clock), getDurationOutCall(clock));
                System.out.println(line);
//                reportFile.writeLine(nameFile, line.toString(), false);
//                reportFile.writeLine(nameFile, "\n", false);
            }

        }

    }


    // Геттеры
    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }


    public int getCountCallInDay() {
        for (int i = 0; i < 24; i++) {
            countCall = countCall + countInCall[i] + countOutCall[i];
        }
        return countCall;
    }

    public int getCountCall(int hour) {
        return countInCall[hour] + countOutCall[hour];
    }

    public int getCountInCall(int hour) {
        return countInCall[hour];
    }

    public int getCountOutCall(int hour) {
        return countOutCall[hour];
    }


    public int getAvgDurationCallInDay() {
        int duration = 0;
        int count = 0;
        int avg = 0;

        for (int i = 0; i < 24; i++) {
            duration = duration + getDurationInCall(i);
            count = count + getCountInCall(i);
        }
        if (count != 0) {
            avg = duration / count;
        }
        return avg;
    }

    public int getDurationCallInDay() {
        for (int i = 0; i < 24; i++) {
            durationCall = durationCall + durationInCall[i] + durationOutCall[i];
        }
        return durationCall;
    }

    public int getDurationCall(int hour) {
        return durationInCall[hour] + durationOutCall[hour];
    }

    public int getDurationOutCall(int hour) {
        return durationOutCall[hour];
    }

    public int getDurationInCall(int hour) {
        return durationOutCall[hour];
    }

    public int getcHNN() {
        int cHNN = 0;   // час наибольшей нагрузки(ЧНН)
        int countCallcHNN = 0;
        for (int i = 0; i < 24; i++) {
            if (countInCall[i] > countCallcHNN) {
                countCallcHNN = countInCall[i];
                cHNN = i;
            }
        }
        return cHNN;
    }

    public String getDDMMYYYY() {
        return String.format("%02d.%02d.%d", day, month, year);
    }


    // Сеттеры
    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setCountInCall(int hour, int count) {
        countInCall[hour] = count;
    }

    public void setCountOutCall(int hour, int count) {
        countOutCall[hour] = count;
    }

    public void setDurationInCall(int hour, int duration) {
        durationInCall[hour] = duration;
    }

    public void setDurationOutCall(int hour, int duration) {
        durationOutCall[hour] = duration;
    }

}
