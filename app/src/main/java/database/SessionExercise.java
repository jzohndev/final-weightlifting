package database;

import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by big1 on 8/3/2016.
 */
public class SessionExercise {
    private int sessionId = -1;
    private int workoutId = -1;
    private int exerciseId = -1;
    private int numberOfSets = 3;
    private int defaultReps = 10;
    private DateTime endTime;
    //private List<SessionSet> exerciseSets;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public SessionExercise() {

    }

    public SessionExercise(int sessionId, int exerciseId){
        this.sessionId = sessionId;
        this.exerciseId = exerciseId;
    }

    public SessionExercise(int sessionId, int exerciseId, int numberOfSets) {
        this.sessionId = sessionId;
        this.exerciseId = exerciseId;
        this.numberOfSets = numberOfSets;
    }

    public SessionExercise(int sessionId, int exerciseId, int setNumber, DateTime endTime) {
        this.sessionId = sessionId;
        this.exerciseId = exerciseId;
        this.numberOfSets = setNumber;
        this.endTime = endTime;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public void setNumberOfSets(int numberOfSets) {
        this.numberOfSets = numberOfSets;
    }

    public int getNumberOfSets() {
        return numberOfSets;
    }

    public void setDefaultReps(int defaultReps){
        this.defaultReps = defaultReps;
    }

    public int getDefaultReps(){
        return defaultReps;
    }

    public void setEndTime(DateTime endTime) {
        this.endTime = endTime;
    }

    public DateTime getEndTime() {
        return endTime;
    }



/*    public void setExerciseSets(List<SessionSet> exerciseSets){
        this.exerciseSets = exerciseSets;
    }

    public List<SessionSet> getExerciseSets(){
        return exerciseSets;
    }

    public SessionSet getExerciseSet(int setNumber){
        return exerciseSets.get(setNumber);
    }

    public void removeExerciseSet(SessionSet exerciseSet, int setNumber){
        exerciseSets.remove(setNumber);
    }*/
}
