package c1_begin;

import android.content.Context;

import org.joda.time.LocalDate;

import java.util.List;

import database.DatabaseHelper;
import database.Exercise;
import database.Schedule;
import database.SessionExercise;
import database.SessionSet;
import database.SessionWorkout;
import database.Workout;

/**
 * Created by jzohn on 11/5/2016.
 */
public class ScheduleHelper {
    private static ScheduleHelper ourInstance = new ScheduleHelper();
    public static Schedule schedule;
    public static SessionWorkout sessionWorkout;
    public static List<SessionExercise> sessionExercises;
    public static List<Exercise> workoutExercises;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public static ScheduleHelper getInstance() {
        return ourInstance;
    }

    private ScheduleHelper() {

    }

    public void initScheduleData(DatabaseHelper db) {
        schedule = db.getSchedule(LocalDate.now());
    }

    public void initSessionData(DatabaseHelper db) {
        workoutExercises = db.getWorkoutExercises(schedule.getWorkout().getId());

        sessionWorkout = db.getSessionWorkout(schedule.getDate());
        final int sessionId = sessionWorkout.getSessionId();
        if (sessionId == -1) {
            db.createSessionWorkout(schedule);
            db.createSessionExercises(sessionWorkout.getSessionId(), workoutExercises);
        }
        sessionExercises =
                db.getSessionExercises(sessionWorkout.getSessionId());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public Schedule getSchedule(){
        return schedule;
    }

    public SessionWorkout getSessionWorkout(){
        return sessionWorkout;
    }

    public List<SessionExercise> getSessionExercises(){
        return sessionExercises;
    }

    public List<Exercise> getWorkoutExercises(){
        return workoutExercises;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public int getExerciseTotalSets(int position){
        return sessionExercises.get(position).getNumberOfSets();
    }

    public int getExerciseCompletedSets(int position){
        List<SessionSet> sets = sessionExercises.get(position).getExerciseSets();
        int count = 0;
        for (SessionSet currentSet : sets){
            if (currentSet.getEndTime() != null){
                count++;
            }
        }
        return count;
    }

    public List<SessionSet> getSessionSets(int exercisePosition){
        return sessionExercises.get(exercisePosition).getExerciseSets();
    }
}
