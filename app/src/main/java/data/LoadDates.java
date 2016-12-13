package data;

import android.util.Log;
import android.widget.PopupMenu;

import org.joda.time.DateTime;
import org.joda.time.Days;
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
    private static LocalDate todayDate;
    private static List<LocalDate> weekDates;
    private static List<LocalDate> monthDates;

    public LoadDates() {
        initializeTodayDate();
        initializeWeekDates();
        initializeMonthDates();
    }

    private void initializeTodayDate() {
        todayDate = LocalDate.now().withDayOfWeek(1);
    }

    private void initializeWeekDates() {
        weekDates = new ArrayList<>();
        LocalDate now = LocalDate.now();
        for (int i = 1; i <= 7; i++) {
            weekDates.add(now.withDayOfWeek(i));
        }
    }

    private void initializeMonthDates() {
        monthDates = new ArrayList<>();
        LocalDate now = LocalDate.now();
        for (int i = 1; i < now.dayOfMonth().getMaximumValue(); i++){
            monthDates.add(now.withDayOfMonth(i));
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public static LocalDate getTodayDate() {
        return todayDate;
    }

    public static List<LocalDate> getWeekDates() {
        return weekDates;
    }

    public static List<LocalDate> getMonthDates() {
        return monthDates;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public static String dateTimeToString(DateTime dateTime){
        DateTimeFormatter formatter = forPattern("yyyy-MM-dd HH:mm:ss");
        return formatter.print(dateTime);
    }

    public static DateTime stringToDateTime(String timeString){
        DateTimeFormatter formatter = forPattern("yyyy-MM-dd HH:mm:ss");
        return formatter.parseDateTime(timeString);
    }

    public static LocalDate stringToLocalDate(String localDateString){
        DateTimeFormatter formatter = forPattern("yyyy-MM-dd");
        return formatter.parseLocalDate(localDateString);
    }

    public static String localDateToString(LocalDate localDate){
        return localDate.toString("yyyy-MM-dd");
    }
}
