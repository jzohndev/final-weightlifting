package database;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

import java.util.Date;

/**
 * Created by big1 on 7/15/2016.
 */
public class Workout {
    private int id = -1;
    private String name;
    private String description;
    private DateTime createdDate;
    private int timesCompleted = 0;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public Workout() {
    }

    public Workout(String name) {
        this.name = name;
    }

    public Workout(DateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Workout(String name, DateTime createdDate) {
        this.name = name;
        this.createdDate = createdDate;
    }

    public Workout(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Workout(String name, String description, DateTime createdDate) {
        this.name = name;
        this.description = description;
        this.createdDate = createdDate;
    }

    public Workout(String name, String description, DateTime createdDate, int timesCompleted) {
        this.name = name;
        this.description = description;
        this.createdDate = createdDate;
        this.timesCompleted = timesCompleted;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setCreatedDate(DateTime createdDate) {
        this.createdDate = createdDate;
    }

    public DateTime getCreatedDate() {
        return createdDate;
    }

    public void setTimesCompleted(int timesCompleted) {
        this.timesCompleted = timesCompleted;
    }

    public int getTimesCompleted() {
        return timesCompleted;
    }
}
