package c1_begin;

import android.content.Context;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import java.util.List;
import java.util.Map;

import database.DatabaseHelper;
import database.Exercise;
import database.ScheduledSession;
import database.SessionExercise;
import database.SessionSet;
import database.SessionWorkout;

/**
 * Created by jzohn on 11/5/2016.
 */
public class ScheduleHelper {
    private static ScheduleHelper ourInstance = new ScheduleHelper();



    public static ScheduledSession scheduledSession;
    public static SessionWorkout sessionWorkout;
    public static List<SessionExercise> sessionExercises;
    public static List<Exercise> workoutExercises;
    public static Map<Integer, List<SessionSet>> sessionSetMap;

    // Constructors
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public static ScheduleHelper getInstance() {
        return ourInstance;
    }

    private ScheduleHelper() {

    }

    public void initScheduleData(DatabaseHelper db) {
        scheduledSession = db.getScheduledSession(LocalDate.now());
    }

    public void initSessionData(DatabaseHelper db) {
        sessionWorkout = db.getSessionWorkout(scheduledSession.getWorkoutId());
        sessionExercises = db.getSessionExercises(scheduledSession.getSessionId());
        workoutExercises = db.getWorkoutExercises(scheduledSession.getWorkoutId());
        sessionSetMap = db.getSessionSets(workoutExercises, scheduledSession.getSessionId());
    }
    // scheduledSession
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public ScheduledSession getSchedule(){
        return scheduledSession;
    }

    // sessionWorkout
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public SessionWorkout getSessionWorkout(){
        return sessionWorkout;
    }

    public void startSessionWorkout(Context context){
        sessionWorkout.setStartTime(DateTime.now());
        new DatabaseHelper(context).updateSessionWorkout(sessionWorkout);
    }

    public boolean checkWorkoutIsComplete(){
        if (sessionWorkout == null){
            return false;
        } else if (sessionWorkout.getEndTime() == null){
            return false;
        } else {
            return true;
        }
    }

    // sessionExercises
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public List<SessionExercise> getSessionExercises(){
        return sessionExercises;
    }

    public SessionExercise getSessionExercise(int exerciseId){
        SessionExercise returnSessionExercise = new SessionExercise();
        for (SessionExercise sessionExercise : sessionExercises){
            if (sessionExercise.getExerciseId() == exerciseId){
                returnSessionExercise = sessionExercise;
            }
        }
        return returnSessionExercise;
    }

    public int getExerciseTotalSets(int position){
        return sessionExercises.get(position).getNumberOfSets();
    }

    public void updateSessionExercisesDataSet(Context context){
        final DatabaseHelper db = new DatabaseHelper(context);
        sessionExercises.clear();
        sessionExercises = db.getSessionExercises(sessionWorkout.getSessionId());
        if (this.workoutIsComplete()){
            scheduledSession.setStatus("Complete");
            db.updateScheduledSession(scheduledSession);
            sessionWorkout.setEndTime(DateTime.now());
            db.updateSessionWorkout(sessionWorkout);
        }
    }

    private boolean workoutIsComplete(){
        int count = 0;
        for (SessionExercise currentSessionExercise : sessionExercises){
            if (currentSessionExercise.getEndTime() != null){
                count++;
            }
        }
        return count == workoutExercises.size();
    }

    // workoutExercises
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public List<Exercise> getWorkoutExercises(){
        return workoutExercises;
    }

    // sessionSetMap
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public int getExerciseCompletedSets(int exerciseId){
        List<SessionSet> sets = sessionSetMap.get(exerciseId);
        int count = 0;
        for (SessionSet currentSet : sets){
            if (currentSet.getEndTime() != null){
                count++;
            }
        }
        return count;
    }

    public List<SessionSet> getSessionSets(int exerciseId){
        return sessionSetMap.get(exerciseId);
    }

    public void updateSessionSet(int exerciseId, int position, SessionSet sessionSet){
        sessionSetMap.get(exerciseId).set(position, sessionSet);
    }
}
