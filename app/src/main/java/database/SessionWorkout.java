package database;

import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;

import java.util.Date;

/**
 * Created by big1 on 8/23/2016.
 */
public class SessionWorkout {
    private int sessionId = -1;
    private int workoutId = -1;
    private String note;
    private DateTime startTime;
    private DateTime endTime;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public SessionWorkout(){

    }

    public SessionWorkout(int sessionId, int workoutId){
        this.sessionId = sessionId;
        this.workoutId = workoutId;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setWorkoutId(int workoutId){
        this.workoutId = workoutId;
    }

    public int getWorkoutId(){
        return workoutId;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    public void setStartTime(DateTime startTime){
        this.startTime = startTime;
    }

    public DateTime getStartTime(){
        return startTime;
    }

    public void setEndTime(DateTime endTime) {
        this.endTime = endTime;
    }

    public DateTime getEndTime() {
        return endTime;
    }
}
