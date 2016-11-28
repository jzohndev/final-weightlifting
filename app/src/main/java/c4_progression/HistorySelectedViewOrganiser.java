package c4_progression;

import android.support.v4.util.ArrayMap;

import java.util.List;
import java.util.Map;

import database.SessionExercise;
import database.SessionWorkout;

/**
 * Created by big1 on 9/1/2016.
 */
public class HistorySelectedViewOrganiser {
    private static HistorySelectedViewOrganiser ourInstance = new HistorySelectedViewOrganiser();
    public static HistorySelectedViewOrganiser getInstance() {
        return ourInstance;
    }

    private HistorySelectedViewOrganiser() {}



    private static SessionWorkout sSessionWorkout;
    private static List<Long> sWorkoutExerciseIds;
    private static Map<Long, List<SessionExercise>> sSessionExerciseSets;
    private static Map<Integer, Integer> sNumberOfViewsPerExercise;

    public void setSessionWorkout(SessionWorkout sessionWorkout){
        sSessionWorkout = sessionWorkout;
    }

    public void setWorkoutExerciseIds(List<Long> workoutExerciseIds){
        sWorkoutExerciseIds = workoutExerciseIds;
    }

    public void setExerciseSets(Map <Long, List<SessionExercise>> sessionExerciseSets){
        sSessionExerciseSets = sessionExerciseSets;
    }

    public void calcNumberOfViewsPerExercise(){
        sNumberOfViewsPerExercise = new ArrayMap<>();
        for (long i : sWorkoutExerciseIds){
            int numberOfViews = 0;
            List<SessionExercise> tempSessionExerciseSets = sSessionExerciseSets.get(i);
            for (SessionExercise exerciseSet : tempSessionExerciseSets){
                numberOfViews++;
                /*if (exerciseSet.getNote() != null){
                    numberOfViews++;
                }*/
            }
            sNumberOfViewsPerExercise.put((int)i, numberOfViews);
        }
    }
}
