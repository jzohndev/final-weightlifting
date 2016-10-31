package routine;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import data.LoadDates;
import database.DatabaseHelper;
import database.Routine;
import database.Workout;

/**
 * Created by big1 on 7/23/2016.
 */
public class RoutineBuilderHelper {
    private static String routineName;
    private static List<Workout> workouts;
    private static String routineDescription;

    public RoutineBuilderHelper() {

    }

    public static void setDefaultRoutineName(Context context) {
        DatabaseHelper db = new DatabaseHelper(context);
        int numberOfRoutines = db.getNumberOfRoutines();
        if (routineName == null) routineName = "Routine " + (numberOfRoutines + 1);
    }

    public static void setRoutineName(String _routineName) {
        routineName = _routineName;
    }

    public static String getRoutineName() {
        return routineName;
    }

    public static void addWorkout(Workout workout) {
        if (workouts == null) workouts = new ArrayList<>();
        workouts.add(workout);
    }

    public static List<Workout> getWorkoutList() {
        if (workouts == null) workouts = new ArrayList<>();
        return workouts;
    }

    public static void removeWorkout(long workoutId) {
        for (Workout workout : workouts) {
            if (workout.getId() == workoutId) {
                workouts.remove(workout);
                return;
            }
        }
    }

    public static boolean createRoutine(Context context) {
        DatabaseHelper db = new DatabaseHelper(context);
        Routine ro = db.getRoutine(routineName);
        if (ro.getId() != -1) {
            Toast.makeText(context, "Routine name already in use.", Toast.LENGTH_SHORT).show();
            return false;
        }
        Routine routine = new Routine(routineName, routineDescription, LoadDates.getTodayDate(), workouts.size());
        long routineId = db.createRoutine(routine);
        for (Workout workout : workouts){
            long workoutId = workout.getId();
            db.createRoutineWorkout(routineId, workoutId);
        }
        return true;

    }

    public static void setRoutineDescription(String description) {
        routineDescription = description;
    }

    public static String getRoutineDescription() {
        return routineDescription;
    }
}

