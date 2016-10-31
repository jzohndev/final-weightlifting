package database;

/**
 * Created by big1 on 7/15/2016.
 */
public class Exercise {
    private long id = -1;
    private String name;
    private String muscleGroup;
    private String description;
    private int defaultSets = 3;
    private int defaultReps = 10;
    private int timesPerformed = 0;

    public Exercise(){}

    public Exercise(String name){
        this.name = name;
    }

    public Exercise(String name, String muscleGroup){
        this.name = name;
        this.muscleGroup = muscleGroup;
    }

    public Exercise(String name, String muscleGroup, String description){
        this.name = name;
        this.muscleGroup = muscleGroup;
        this.description = description;
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

    public void setMuscleGroup(String muscleGroup){
        this.muscleGroup = muscleGroup;
    }

    public String getMuscleGroup(){
        return muscleGroup;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

    public void setDefaultSets(int defaultSets){
        this.defaultSets = defaultSets;
    }

    public int getDefaultSets(){
        return defaultSets;
    }

    public void setDefaultReps(int defaultReps){
        this.defaultReps = defaultReps;
    }

    public int getDefaultReps(){
        return defaultReps;
    }

    public void setTimesPerformed(int timesPerformed){
        this.timesPerformed = timesPerformed;
    }

    public long getTimesPerformed(){
        return timesPerformed;
    }
}
