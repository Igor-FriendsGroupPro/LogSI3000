package FriendsGroup.pro;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogDateTime {
    int duration (String dateTimeStart, String dateTimeEnd) {
        long response = 0;
        Date dateStart = new Date();
        Date dateEnd = new Date();
        SimpleDateFormat formatStart = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat formatEnd = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        try {
            dateStart = formatStart.parse(dateTimeStart);
            dateEnd = formatEnd.parse(dateTimeEnd);
            response = (dateEnd.getTime() - dateStart.getTime())/1000;
            System.out.println(dateTimeStart + " - " + dateTimeEnd + " Длительность разговора: " + response);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (int) response;
    }

    boolean relevantDate (String year, String month, String dateCurrent) {
        System.out.println(dateCurrent.startsWith(year + "-" + month));
        return dateCurrent.startsWith(year + "-" + month);
    }
}
