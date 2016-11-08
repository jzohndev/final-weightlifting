package c1_begin;

import android.support.v4.util.ArrayMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import database.Exercise;
import database.SessionExercise;
import database.SessionWorkout;

/**
 * Created by big1 on 8/23/2016.
 */
public class BeginWorkoutHelper {
    private static BeginWorkoutHelper ourInstance = new BeginWorkoutHelper();
    private static long sWorkoutId;
    private static long sSessionId;
    private static SessionWorkout sSessionWorkout;
    private static List<Exercise> sExercises = new ArrayList<>();
    private static Exercise sCurrentExercise;
    private static Map<Long, List<SessionExercise>> sExerciseSession = new ArrayMap<>();
    private static Exercise sSelectedExercise;

    public static BeginWorkoutHelper getInstance() {
        return ourInstance;
    }

    private BeginWorkoutHelper() {
    }

    public void setWorkoutId(long workoutId) {
        sWorkoutId = workoutId;
    }

    public long getWorkoutId() {
        return sWorkoutId;
    }

    public void setSessionId(long sessionId) {
        sSessionId = sessionId;
    }

    public long getSessionId() {
        return sSessionId;
    }

    public void setSessionWorkout(SessionWorkout sessionWorkout) {
        sSessionWorkout = sessionWorkout;
    }

    public SessionWorkout getSessionWorkout() {
        return sSessionWorkout;
    }

    public void setExercises(List<Exercise> exercises) {
        sExercises = exercises;
    }

    public List<Exercise> getExercises() {
        return sExercises;
    }

    public int getExercisesSize() {
        return sExercises.size();
    }

    public Exercise getExerciseByPosition(int position) {
        return sExercises.get(position);
    }

    public List<SessionExercise> getExerciseSession(long exerciseId) {
        return sExerciseSession.get(exerciseId);
    }

    public void addSessionExercise(long exerciseId, SessionExercise sessionExercise) {
        if (sExerciseSession.get(exerciseId) == null){
            List<SessionExercise> createSessionExercise = new ArrayList<>();
            createSessionExercise.add(sessionExercise);
            sExerciseSession.put(exerciseId, createSessionExercise);
        } else {
            sExerciseSession.get(exerciseId).add(sessionExercise);
        }
    }

    public int getExerciseSessionSize(long exerciseId) {
        if (sExerciseSession.get(exerciseId) == null) {
            return 0;
        } else {
            return sExerciseSession.get(exerciseId).size();
        }
    }

    public void setSelectedExercise(Exercise selectedExercise) {
        sSelectedExercise = selectedExercise;
    }

    public Exercise getSelectedExercise() {
        return sSelectedExercise;
    }

    public boolean isDone(){
        return sExercises.size() == sExerciseSession.size();
    }
}
