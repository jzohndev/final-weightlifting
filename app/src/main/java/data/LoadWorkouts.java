package data;

import android.content.Context;

import java.util.List;
import database.DatabaseHelper;
import database.Workout;

/**
 * Created by big1 on 7/16/2016.
 */
public class LoadWorkouts {
    public static List<Workout> allWorkouts;
    public static Workout todayWorkout;
    public static List<Workout> weekWorkouts;
    public static List<Workout> monthWorkouts;
    private DatabaseHelper db;

    public LoadWorkouts(Context context){
        db = new DatabaseHelper(context);
        initializeAllWorkouts();
        initializeTodayWorkout();
    }

    private void initializeAllWorkouts(){
        allWorkouts = db.getAllWorkouts();
    }

    private void initializeTodayWorkout(){
        todayWorkout = new Workout();
        for (Workout workout : allWorkouts){
            if (workout.getCreatedDate().compareTo(LoadDates.getTodayDate()) == 0){
                todayWorkout = workout;
            }
        }
    }

    public static void reloadAllWorkouts(Context context){
        DatabaseHelper db = new DatabaseHelper(context);
        allWorkouts = db.getAllWorkouts();
    }


}
