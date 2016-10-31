package data;

import java.util.Date;

import database.Exercise;
import database.SessionWorkout;
import database.Workout;

/**
 * Created by big1 on 8/11/2016.
 */
public class IntentResolver {
    private static IntentResolver ourInstance = new IntentResolver();
    private static String callingActivity = "";
    private static String originalCallingActivity = "";
    private static int callingCode = -1;
    private static Workout workout;
    private static Exercise exercise;
    private static Date date;
    private static SessionWorkout sessionWorkout;

    public static IntentResolver getInstance() {
        return ourInstance;
    }

    private IntentResolver() {
    }

    public void setIntent(String from, String orgFrom, int purpose) {
        callingActivity = from;
        originalCallingActivity = orgFrom;
        callingCode = purpose;
    }

    public void setIntent(String from, String orgFrom, int purpose, Workout workout_) {
        callingActivity = from;
        originalCallingActivity = orgFrom;
        callingCode = purpose;
        workout = workout_;
    }

    public void setIntent(String from, String orgFrom, int purpose, Exercise exercise_) {
        callingActivity = from;
        originalCallingActivity = orgFrom;
        callingCode = purpose;
        exercise = exercise_;
    }

    public void setIntent(String from, String orgFrom, int purpose, Date date_) {
        callingActivity = from;
        originalCallingActivity = orgFrom;
        callingCode = purpose;
        date = date_;
    }

    public void setIntent(String from, String orgFrom, int purpose, SessionWorkout sessionWorkout_) {
        callingActivity = from;
        originalCallingActivity = orgFrom;
        callingCode = purpose;
        sessionWorkout = sessionWorkout_;
    }

    public String getFrom(){
        return callingActivity;
    }

    public String getOrgFrom(){
        return originalCallingActivity;
    }

    public int getPurpose(){
        return callingCode;
    }

    public Workout getWorkout(){
        return workout;
    }

    public Exercise getExercise(){
        return exercise;
    }

    public Date getDate() { return date; }

    public SessionWorkout getSessionWorkout(){
        return sessionWorkout;
    }
}
