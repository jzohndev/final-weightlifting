package database;

import org.joda.time.LocalDate;

/**
 * Created by big1 on 10/3/2016.
 */

public class Schedule {
    private LocalDate date;
    private Workout workout;
    private String status = "incomplete";

    private long workoutId; // TODO REMOVE
    private String completed = "no"; // TODO REMOVE

    public Schedule(){

    }

    public Schedule(LocalDate date, long workoutId){
        this.date = date;
        this.workoutId = workoutId;
    }

    public Schedule(LocalDate date, long workoutId, String completed){
        this.date = date;
        this.workoutId = workoutId;
        this.completed = completed;
    }

    public void setDate(LocalDate date){
        this.date = date;
    }

    public LocalDate getDate(){
        return date;
    }

    public void setWorkoutId(long workoutId){
        this.workoutId = workoutId;
    }

    public long getWorkoutId(){
        return workoutId;
    }

    public void setCompleted(String completed){
        this.completed = completed;
    }

    public String getCompleted(){
        return completed;
    }




    public void setWorkout(Workout workout){
        this.workout = workout;
    }

    public Workout getWorkout(){
        return workout;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public String getStatus () {
        return status;
    }
}
