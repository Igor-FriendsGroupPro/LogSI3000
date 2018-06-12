package FriendsGroup.pro;

import java.util.Formatter;

public class PhoneRing {

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
        month = Integer.parseInt(YYYYDDMMHHmmss.substring(4, 6));
        day = Integer.parseInt(YYYYDDMMHHmmss.substring(6, 8));
        hour = Integer.parseInt(YYYYDDMMHHmmss.substring(8, 10));
        minute = Integer.parseInt(YYYYDDMMHHmmss.substring(10, 12));
        second = Integer.parseInt(YYYYDDMMHHmmss.substring(12, 14));

        Formatter tempString = new Formatter();
        date = tempString.format("%4d.%02d.%02d", year, month, day).toString();
        tempString = new Formatter();
        time = tempString.format("%02d:%02d:%02d", hour, minute, second).toString();
    }

    // Длительность звонка
    public void setDuration(int duration) {
        this.duration = duration;
    }

    // Вызывающий абонент
    public void setDefiantAbonent(String number, String ipDN) {
//        System.out.println(number);
//        if (number.length() > 11){
//            number = number.substring(0, 11);
//        }
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
    public long getCallID() {
        return callID;
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

    // Направление звонка
    public String getDirection() {
        String response = "Неопределено";

//        Number > Number
        if (getDefiantName().compareTo(String.valueOf(getDefiantNumber())) == 0 && getCalledName().compareTo(String.valueOf(getCalledNumber())) == 0) {
            response = "Неопределено";
        }
//        Number > !Number
        if (getDefiantName().compareTo(String.valueOf(getDefiantNumber())) == 0 && getCalledName().compareTo(String.valueOf(getCalledNumber())) != 0) {
            response = "Входящий";
        }
//        !Number > Number
        if (getDefiantName().compareTo(String.valueOf(getDefiantNumber())) != 0 && getCalledName().compareTo(String.valueOf(getCalledNumber())) == 0) {
            response = "Исходящий";
        }
//        !Number > !Number
        if (getDefiantName().compareTo(String.valueOf(getDefiantNumber())) != 0 && getCalledName().compareTo(String.valueOf(getCalledNumber())) != 0) {
            response = "Внутренний";
        }

//        Без сим-карты > !Number
        if (getDefiantName().compareTo("Без сим-карты") == 0 && getCalledName().compareTo(String.valueOf(getCalledNumber())) != 0) {
            response = "Входящий";
        }


        return response;
    }
}
