package data;

import android.content.Context;

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
    private static long todaySchedule;
    private static Map<Date, Long> weekSchedules;
    private static Map<Date, Long> monthSchedules;

    private static DatabaseHelper db;
    private static Context context;

    public LoadSchedules(Context context_){
        context = context_;
        initializeMonthSchedules();
        initializeWeekSchedules();
        initializeTodaySchedule();
    }

    private static void initializeMonthSchedules(){
        List<Date> monthDates = LoadDates.getMonthDates();
        if (db == null)
            db = new DatabaseHelper(context);
        monthSchedules = new HashMap<>();
        long workoutId;
        for (Date date : monthDates){
            Schedule currentSchedule = db.getSchedule(date);
            workoutId = currentSchedule.getWorkoutId();
            monthSchedules.put(date, workoutId);
        }
    }

    private static void initializeWeekSchedules(){
        List<Date> weekDates = LoadDates.getWeekDates();
        long workoutId;
        weekSchedules = new HashMap<>();
        for (Date date : weekDates){
            Schedule currentSchedule = db.getSchedule(date);
            workoutId = currentSchedule.getWorkoutId();
            weekSchedules.put(date, workoutId);
        }
    }

    private static void initializeTodaySchedule(){
        Date todayDate = LoadDates.getTodayDate();
        todaySchedule = db.getSchedule(todayDate).getWorkoutId();
    }

    public static Map<Date, Long> getMonthSchedules(){
        return monthSchedules;
    }

    public static Map<Date, Long> getWeekSchedules(){
        return weekSchedules;
    }

    public static Long getTodaySchedule(){
        return todaySchedule;
    }
}
