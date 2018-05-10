package FriendsGroup.pro;

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


    public int getDurationCallInDay() {
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
            if (countInCall[i] > countCallcHNN) {cHNN = i;}
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
