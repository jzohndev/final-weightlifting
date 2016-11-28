package data;

import android.content.Context;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import database.DatabaseHelper;
import database.Schedule;

/**
 * Created by big1 on 7/30/2016.
 */
public class LoadSchedules {
    private static Schedule todaySchedule;
    private static List<Schedule> weekSchedules;
    private static List<Schedule> monthSchedules;

    private static DatabaseHelper db;
    private static Context context;

    public LoadSchedules(Context context_){
        context = context_;
        initializeMonthSchedules();
        initializeWeekSchedules();
        initializeTodaySchedule();
    }

    private static void initializeMonthSchedules(){
        List<LocalDate> monthDates = LoadDates.getMonthDates();
        if (db == null)
            db = new DatabaseHelper(context);
        monthSchedules = new ArrayList<>();
        int workoutId;
        for (LocalDate date : monthDates){
            monthSchedules.add(db.getSchedule(date));
        }
    }

    private static void initializeWeekSchedules(){
        List<LocalDate> weekDates = LoadDates.getWeekDates();
        int workoutId;
        weekSchedules = new ArrayList<>();
        for (LocalDate date : weekDates){
            weekSchedules.add(db.getSchedule(date));
        }
    }

    private static void initializeTodaySchedule(){
        LocalDate todayDate = LoadDates.getTodayDate();
        todaySchedule = db.getSchedule(todayDate);
    }

    public static List<Schedule> getMonthSchedules(){
        return monthSchedules;
    }

    public static List<Schedule> getWeekSchedules(){
        return weekSchedules;
    }

    public static Schedule getTodaySchedule(){
        return todaySchedule;
    }
}
