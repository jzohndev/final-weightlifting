package database;

import java.util.Date;

/**
 * Created by big1 on 7/15/2016.
 */
public class Routine {
    private long id = -1;
    private String name;
    private String description;
    private Date createdDate;
    private int numberOfDays;
    private long iconId = -1;
    private String inUse;

    public Routine(){}

    public Routine(String name){
        this.name = name;
    }

    public Routine(String name, String description){
        this.name = name;
        this.description = description;
    }

    public Routine(String name, String description, Date createdDate){
        this.name = name;
        this.description = description;
        this.createdDate = createdDate;
    }

    public Routine(String name, String description, Date createdDate, int numberOfDays){
        this.name = name;
        this.description = description;
        this.createdDate = createdDate;
        this.numberOfDays = numberOfDays;
    }

    public Routine(String name, String description, Date createdDate, int numberOfDays, long iconId){
        this.name = name;
        this.description = description;
        this.createdDate = createdDate;
        this.numberOfDays = numberOfDays;
        this.iconId = iconId;
    }

    public Routine(String name, String description, Date createdDate, int numberOfDays, long iconId, String inUse){
        this.name = name;
        this.description = description;
        this.createdDate = createdDate;
        this.numberOfDays = numberOfDays;
        this.iconId = iconId;
        this.inUse = inUse;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void setId(long id){
        this.id = id;
    }

    public long getId(){
        return id;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

    public void setCreatedDate(Date createdDate){
        this.createdDate = createdDate;
    }

    public Date getCreatedDate(){
        return createdDate;
    }

    public void setNumberOfDays(int numberOfDays){
        this.numberOfDays = numberOfDays;
    }

    public int getNumberOfDays(){
        return numberOfDays;
    }

    public void setIconId(long iconId){
        this.iconId = iconId;
    }

    public long getIconId(){
        return iconId;
    }

    public void setInUse(String inUse){
        this.inUse = inUse;
    }

    public String getInUse(){
        return inUse;
    }

}
