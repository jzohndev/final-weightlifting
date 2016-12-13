package database;

import org.joda.time.LocalDate;

public class ScheduledSession {
    private int sessionId = -1;
    private int workoutId = -1;
    private LocalDate date;
    private String status = "None";

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public ScheduledSession(){

    }

    public ScheduledSession(LocalDate date, int workoutId){
        this.date = date;
        this.workoutId = workoutId;
    }

    public ScheduledSession(LocalDate date, int workoutId, String status){
        this.date = date;
        this.workoutId = workoutId;
        this.status = status;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void setSessionId(int sessionId){
        this.sessionId = sessionId;
    }

    public int getSessionId(){
        return sessionId;
    }

    public void setWorkoutId(int workoutId){
        this.workoutId = workoutId;
    }

    public int getWorkoutId(){
        return workoutId;
    }

    public void setDate(LocalDate date){
        this.date = date;
    }

    public LocalDate getDate(){
        return date;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public String getStatus () {
        return status;
    }
}
