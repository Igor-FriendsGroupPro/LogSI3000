package FriendsGroup.pro;

import java.util.ArrayList;
import java.util.Formatter;

public class PhoneRing {

//    public ArrayList favouritelist = new ArrayList();

    // Внутренний идентификатор звонка в SI3000
    private int callID;

    // Дата и время начала звонка
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private int second;

    private String date;
    private String time;

    // Длительность звонка
    private int duration;

    // Вызывающий абонент DN, A0
    private long defiantNumber;
    private String defiantName;
    private String ipDN;        // IP адрес

    // Вызываемый абонент СN, A2
    private long calledNumber;
    private String calledName;
    private String ipCN;        // IP адрес

    // Направление звонка
    private String direction;

    // Сеттеры
    // Внутренний идентификатор звонка в SI3000
    public void setCallID(int callID) {
        this.callID = callID;
    }

    // Дата и временя звонка
    public void setDateAndTime(String YYYYDDMMHHmmss) {
        year = Integer.parseInt(YYYYDDMMHHmmss.substring(0, 4));
        month = Integer.parseInt(YYYYDDMMHHmmss.substring(5, 7));
        day = Integer.parseInt(YYYYDDMMHHmmss.substring(8, 10));
        hour = Integer.parseInt(YYYYDDMMHHmmss.substring(11, 13));
        minute = Integer.parseInt(YYYYDDMMHHmmss.substring(14, 16));
        second = Integer.parseInt(YYYYDDMMHHmmss.substring(17, 19));

        Formatter tempString = new Formatter();
        date = tempString.format("%4d.%2d.%2d", year, month, day).toString();
        tempString = new Formatter();
        time = tempString.format("%2d:%2d:%2d", hour, minute, second).toString();
    }

    // Длительность звонка
    public void setDuration(int duration) {
        this.duration = duration;
    }

    // Вызывающий абонент
    public void setDefiantAbonent(String number, String ipDN) {
        defiantNumber = Long.valueOf(number).longValue();
        defiantName = new Abonent().getNameAbonent(number);
        this.ipDN = ipDN;
    }

    // Вызываемый абонент
    public void setCalledAbonent(String number, String ipCN) {
        calledNumber = Long.valueOf(number).longValue();
        calledName = new Abonent().getNameAbonent(number);
        this.ipCN = ipCN;
    }

    // Геттеры
    // Внутренний идентификатор звонка в SI3000
    public long getCalledNumber() {
        return calledNumber;
    }

    // Дата звонка
    public int getYear() {
        return year;
    }
    public int getMonth() {
        return month;
    }
    public int getDay() {
        return day;
    }
    public String getDate() {
        return date;
    }

    // Время звонка
    public int getHour() {
        return hour;
    }
    public int getMinute() {
        return minute;
    }
    public int getSecond() {
        return second;
    }
    public String getTime() {
        return time;
    }

    // Длительность звонка
    public int getDuration() {
        return duration;
    }

    // Вызывающий абонент
    public long getDefiantNumber() {
        return defiantNumber;
    }
    public String getDefiantName() {
        return defiantName;
    }
    public String getIPDN() {
        return ipDN;
    }

    // Вызываемый абонент
    public long getCalledNumber() {
        return calledNumber;
    }
    public String getCalledName() {
        return calledName;
    }
    public String getIPCN() {
        return ipCN;
    }

    // Направление
    public String getCalledName() {
        return defiantName + " > " + calledName;
    }
}
