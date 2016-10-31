package database;

import java.util.Date;

/**
 * Created by big1 on 7/15/2016.
 */
public class Workout {
    private long id = -1;
    private String name;
    private String description;
    private Date createdDate;
    private long iconId = -1;
    private int timesCompleted = 0;

    public Workout() {
    }

    public Workout(String name) {
        this.name = name;
    }

    public Workout(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Workout(String name, Date createdDate) {
        this.name = name;
        this.createdDate = createdDate;
    }

    public Workout(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Workout(String name, String description, Date createdDate) {
        this.name = name;
        this.description = description;
        this.createdDate = createdDate;
    }

    public Workout(String name, String description, Date createdDate, long iconId) {
        this.name = name;
        this.description = description;
        this.createdDate = createdDate;
        this.iconId = iconId;
    }

    public Workout(String name, String description, Date createdDate, long iconId, int timesCompleted) {
        this.name = name;
        this.description = description;
        this.createdDate = createdDate;
        this.iconId = iconId;
        this.timesCompleted = timesCompleted;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
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

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setIconId(long iconId) {
        this.iconId = iconId;
    }

    public long getIconId() {
        return iconId;
    }

    public void setTimesCompleted(int timesCompleted){
        this.timesCompleted = timesCompleted;
    }

    public int getTimesCompleted(){
        return timesCompleted;
    }
}
