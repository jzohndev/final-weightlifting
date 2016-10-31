package data;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import database.DatabaseHelper;
import database.Exercise;
import database.Workout;

/**
 * Created by big1 on 7/16/2016.
 */
public class PreLoader {
    private DatabaseHelper db;
    private Context context;
    private final String LOG = "PreLoader";
    private Map<String, Long> workouts;
    private Map<String, Long> exercises;

    public PreLoader(Context context) {
        this.context = context;
        if (!databaseExists()) {
            db = new DatabaseHelper(context);
            Log.e(LOG, "Data file not found, creating database.");
            preLoadWorkouts();
            preLoadExercises();
            preLoadWorkoutExercises();
            Log.e(LOG, "Database created.");
        }else Log.e(LOG, "Data file detected.");
    }

    private boolean databaseExists(){
        File dbFile = context.getDatabasePath(DatabaseHelper.DATABASE_NAME);
        return dbFile.exists();
    }

    private void preLoadWorkouts(){
        Workout test1 = new Workout("TEST Chest & Biceps", "Monday workout.", LoadDates.getTodayDate());
        Workout test2 = new Workout("TEST Shoulders & Triceps", "Tuesday workout.", LoadDates.getTodayDate());
        Workout test3 = new Workout("TEST Back & Legs", "Thursday workout.", LoadDates.getTodayDate());
        workouts = new HashMap<>();
        workouts.put("test1", db.createWorkout(test1));
        workouts.put("test2", db.createWorkout(test2));
        workouts.put("test3", db.createWorkout(test3));
    }

    private void preLoadExercises(){
        // my chest
        Exercise benchPress1 = new Exercise("Barbell Bench Press - Medium Grip",
                "Chest",
                "Variation of the standard Barbell Bench Press that utilizes a closer grip than the Wide Grip.");
        Exercise cableFly1 = new Exercise("Standing Cable Fly",
                "Chest",
                "Cable fly that utilizes chest muscles.");
        Exercise benchPress2 = new Exercise("Incline Bench Press",
                "Chest",
                "Variation of the standard bench press using a slightly inclined bench");

        // my triceps
        Exercise skullCrusher = new Exercise("EZ-Bar Skullcrusher",
                "Triceps",
                "Standard laying skullcrusher that utilizes the triceps.");
        Exercise pushdown = new Exercise("Tricep Pushdown",
                "Triceps",
                "A standard standing pushdown.");
        Exercise extension1 = new Exercise("Standing Overhead Dumbbell Tricep Extension",
                "Triceps",
                "A standard standing tricep extension.");

        // my shoulders
        Exercise dumbbellPress = new Exercise("Dumbbell Press",
                "Shoulders",
                "A standard seated dumbbell press");
        Exercise row1 = new Exercise("Upright EZ-Bar Row",
                "Shoulders",
                "An upright row that utilizes the shoulders.");
        Exercise raise = new Exercise("Lateral Raise",
                "Shoulders",
                "A standard arm raise that utilizes the shoulders.");

        // my biceps
        Exercise dumbbellCurl1 = new Exercise("Alternating Dumbbell Curl",
                "Biceps",
                "A standard standing dumbbell curl.");
        Exercise dumbbellCurl2 = new Exercise("Alternating Hammer Curl",
                "Biceps",
                "Variation of the standard dumbbell curl.");
        Exercise benchPress3 = new Exercise("Barbell Bench Press - Wide Grip",
                "Biceps",
                "Standard compound exercise that utilizes the chest muscles.");

        // my back
        Exercise deadlift = new Exercise("Barbell Deadlift",
                "Back",
                "Standard deadlift.");
        Exercise row2 = new Exercise("Wide-Grip Seated Cable Row",
                "Back",
                "A standard cable row utilizing the back.");
        Exercise row3 = new Exercise("Standing T-Bar Row",
                "Back",
                "A lift that encorporates the back.");

        // my legs
        Exercise squat = new Exercise("Barbell Squat",
                "Legs",
                "The well-known squat.");
        Exercise press = new Exercise("Leg Press",
                "Legs",
                "Who doesn't know the leg press.");
        Exercise extension2 = new Exercise("Leg Extension",
                "Legs",
                "I'm getting tired of typing.");

        //other exercises
        Exercise press1 = new Exercise("Seated Military Press",
                "Shoulders",
                "A sitting barbell press that utilizes the shoulders");
        Exercise row = new Exercise("Upright Barbell Row",
                "Shoulders",
                "A standing barbell row that utilizes the shoulders");
        Exercise shrugs = new Exercise("Dumbbell Shrug",
                "Shoulders",
                "A standing shrug using dumbbells that utilizes the shoulders.");
        Exercise partials = new Exercise("Power Partials",
                "Shoulders",
                "A standard dumbbell lift");

        exercises = new HashMap<>();
        exercises.put("benchPress1", db.createExercise(benchPress1));
        exercises.put("cableFly1", db.createExercise(cableFly1));
        exercises.put("benchPress2", db.createExercise(benchPress2));
        exercises.put("skullCrusher", db.createExercise(skullCrusher));
        exercises.put("pushdown", db.createExercise(pushdown));
        exercises.put("extension1", db.createExercise(extension1));

        exercises.put("dumbbellPress", db.createExercise(dumbbellPress));
        exercises.put("row1", db.createExercise(row1));
        exercises.put("raise", db.createExercise(raise));
        exercises.put("dumbbellCurl1", db.createExercise(dumbbellCurl1));
        exercises.put("dumbbellCurl2", db.createExercise(dumbbellCurl2));
        exercises.put("benchPress3", db.createExercise(benchPress3));

        exercises.put("deadlift", db.createExercise(deadlift));
        exercises.put("row2", db.createExercise(row2));
        exercises.put("row3", db.createExercise(row3));
        exercises.put("squat", db.createExercise(squat));
        exercises.put("press", db.createExercise(press));
        exercises.put("extension2", db.createExercise(extension2));

        exercises.put("press1", db.createExercise(press1));
        exercises.put("row", db.createExercise(row));
        exercises.put("shrugs", db.createExercise(shrugs));
        exercises.put("partials", db.createExercise(partials));
    }

    private void preLoadWorkoutExercises(){
        db.createWorkoutExercise(workouts.get("test1"), exercises.get("benchPress1"));
        db.createWorkoutExercise(workouts.get("test1"), exercises.get("cableFly1"));
        db.createWorkoutExercise(workouts.get("test1"), exercises.get("benchPress2"));

        db.createWorkoutExercise(workouts.get("test1"), exercises.get("skullCrusher"));
        db.createWorkoutExercise(workouts.get("test1"), exercises.get("pushdown"));
        db.createWorkoutExercise(workouts.get("test1"), exercises.get("extension1"));


        db.createWorkoutExercise(workouts.get("test2"), exercises.get("dumbbellPress"));
        db.createWorkoutExercise(workouts.get("test2"), exercises.get("row1"));
        db.createWorkoutExercise(workouts.get("test2"), exercises.get("raise"));

        db.createWorkoutExercise(workouts.get("test2"), exercises.get("dumbbellCurl1"));
        db.createWorkoutExercise(workouts.get("test2"), exercises.get("dumbbellCurl2"));
        db.createWorkoutExercise(workouts.get("test2"), exercises.get("benchPress3"));


        db.createWorkoutExercise(workouts.get("test3"), exercises.get("deadlift"));
        db.createWorkoutExercise(workouts.get("test3"), exercises.get("row2"));
        db.createWorkoutExercise(workouts.get("test3"), exercises.get("row3"));

        db.createWorkoutExercise(workouts.get("test3"), exercises.get("squat"));
        db.createWorkoutExercise(workouts.get("test3"), exercises.get("press"));
        db.createWorkoutExercise(workouts.get("test3"), exercises.get("extension2"));
    }
}
