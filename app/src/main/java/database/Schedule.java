package database;

import org.joda.time.LocalDate;

/**
 * Created by big1 on 10/3/2016.
 */

public class Schedule {
    private LocalDate date;
    private String status = "no workout";
    private Workout workout;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public Schedule(){

    }

    public Schedule(LocalDate date, Workout workout){
        this.date = date;
        this.workout = workout;
    }

    public Schedule(LocalDate date, Workout workout, String status){
        this.date = date;
        this.workout = workout;
        this.status = status;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void setDate(LocalDate date){
        this.date = date;
    }

    public LocalDate getDate(){
        return date;
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
