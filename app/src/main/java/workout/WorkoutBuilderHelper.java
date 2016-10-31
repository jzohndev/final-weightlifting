package workout;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import data.LoadDates;
import database.DatabaseHelper;
import database.Exercise;
import database.Workout;

/**
 * Created by big1 on 7/20/2016.
 */
public class WorkoutBuilderHelper {
    private static WorkoutBuilderHelper ourInstance =
            new WorkoutBuilderHelper();
    private static String name = "";
    private static String description = "";
    private static List<Exercise> exercises = new ArrayList<>();

    public static WorkoutBuilderHelper getInstance() {
        return ourInstance;
    }

    private WorkoutBuilderHelper() {
    }

    public void initializeDefaultWorkoutName(Context context) {
        final DatabaseHelper db = new DatabaseHelper(context);
        final int numberOfWorkouts = db.getNumberOfWorkouts();
        if (name.contains("")) {
            name = "Workout " + (numberOfWorkouts + 1);
        }
    }

    public void setWorkoutName(String workoutName) {
        name = workoutName;
    }

    public String getWorkoutName() {
        return name;
    }

    public List<Exercise> getExerciseList() {
        return exercises;
    }

    public void setWorkoutDescription(String description) {
        WorkoutBuilderHelper.description = description;
    }

    public String getWorkoutDescription() {
        return description;
    }

    public void addExercise(Exercise exercise) {
        exercises.add(exercise);
    }

    public void removeExercise(long exerciseId) {
        for (Exercise exercise : exercises) {
            if (exercise.getId() == exerciseId) {
                exercises.remove(exercise);
                return;
            }
        }
    }

    public void updateSets(long exerciseId, int sets) {
        for (int i = 0; i < exercises.size(); i++) {
            if (exercises.get(i).getId() == exerciseId) {
                exercises.get(i).setDefaultSets(sets);
            }
        }
    }

    public void updateReps(long exerciseId, int reps) {
        for (int i = 0; i < exercises.size(); i++){
            if (exercises.get(i).getId() == exerciseId){
                exercises.get(i).setDefaultReps(reps);
            }
        }
    }

    public boolean createWorkout(Context context) {
        final DatabaseHelper db = new DatabaseHelper(context);
        Workout testWorkoutExists = db.getWorkout(name);
        if (testWorkoutExists.getId() != -1) {
            Toast.makeText(context, "Workout name already used.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            Workout workout = new Workout(name, description, LoadDates.getTodayDate());
            long workoutId = db.createWorkout(workout);
            for (Exercise exercise : exercises) {
                db.createWorkoutExercise(workoutId, exercise.getId(),
                        exercise.getDefaultSets(), exercise.getDefaultReps());
            }
            return true;
        }
    }

    public void finish() {
        name = "";
        description = "";
        exercises.removeAll(exercises);
    }
}
