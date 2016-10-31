package data;

import android.util.Log;
import android.widget.PopupMenu;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static org.joda.time.format.DateTimeFormat.forPattern;

/**
 * Created by big1 on 7/16/2016.
 */
public class LoadDates {
    private static Date todayDate;
    private static List<Date> weekDates;
    private static List<Date> monthDates;
    private static final String LOG = "LoadDates";

    public LoadDates() {
        initializeTodayDate();
        initializeWeekDates();
        initializeMonthDates();
    }

    private void initializeTodayDate() {
        Calendar calendar = Calendar.getInstance();
        todayDate = calendar.getTime();
    }

    private void initializeWeekDates() {
        weekDates = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.SUNDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        for (int i = 0; i < 7 ; i++){
            weekDates.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
    }

    private void initializeMonthDates() {
        monthDates = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int currentMonth = calendar.get(Calendar.MONTH);
        do {
            monthDates.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        } while (currentMonth == calendar.get(Calendar.MONTH));
    }

    public static Date getTodayDate() {
        return todayDate;
    }

    public static List<Date> getWeekDates() {
        return weekDates;
    }

    public static List<Date> getMonthDates() {
        return monthDates;
    }





    //
    public static String dateToString(Date date){
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String dateString = fmt.format(date);
        Log.e(LOG, dateString);
        return dateString;
    }

    public static Date stringToDate(String dateString) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = null;
        try {
            date = fmt.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.e(LOG, date.toString());
        return date;
    }

    public static String dateTimeToString(Date time){
        SimpleDateFormat fmt = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        String dateTimeString = fmt.format(time);
        Log.e(LOG, dateTimeString);
        return dateTimeString;
    }

    public static Date stringToDateTime(String timeString){
        SimpleDateFormat fmt = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        Date dateTime = null;
        try {
            dateTime = fmt.parse(timeString);
        } catch (ParseException e){
            e.printStackTrace();
        }
        Log.e(LOG, dateTime.toString());
        return dateTime;
    }

    public static LocalDate stringToLocalDate(String localDateString){

        DateTimeFormatter formatter = forPattern("yyyy-MM-dd");
        return formatter.parseLocalDate(localDateString);

    }

    public static String localDateToString(LocalDate localDate){
        return localDate.toString("yyyy-MM-dd");
    }
}
