package data;

import android.content.Context;

import java.util.List;

import database.DatabaseHelper;
import database.Exercise;

/**
 * Created by big1 on 7/20/2016.
 */
public class LoadExercises {
    public static List<Exercise> allExercises;
    public static List<Exercise> todayExercises;

    private DatabaseHelper db;

    public LoadExercises(Context context) {
        db = new DatabaseHelper(context);
        initializeAllExercises();
        initializeMonthExercises();
        initializeWeekExercises();
        initializeTodayExercises();

    }

    private void initializeAllExercises() {
        allExercises = db.getAllExercises();
    }

    private void initializeMonthExercises(){

    }

    private void initializeWeekExercises(){

    }

    private void initializeTodayExercises() {

    }
}
