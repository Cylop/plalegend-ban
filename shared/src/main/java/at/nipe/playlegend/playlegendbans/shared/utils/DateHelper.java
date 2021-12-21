package at.nipe.playlegend.playlegendbans.shared.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHelper {

    public static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("dd-MM-yyyy HH:mm");

    public static String getFormattedDate(Date date) {
        return getFormattedDate(date, DATE_TIME_FORMAT);
    }

    public static String getFormattedDate(Date date, SimpleDateFormat format) {
        return format.format(date);
    }

}
