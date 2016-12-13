package database;

import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;

/**
 * Created by jzohn on 11/7/2016.
 */

public class SessionSet {
    private int sessionId = -1;
    private int exerciseId = -1;
    private int setNumber;
    private String note;
    private int reps;
    private int weight;
    private DateTime endTime;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public SessionSet() {

    }

    public SessionSet(int sessionId, int exerciseId, int setNumber) {
        this.sessionId = sessionId;
        this.exerciseId = exerciseId;
        this.setNumber = setNumber;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }

    public int getSetNumber() {
        return setNumber;
    }

    public void setSetNumber(int setNumber) {
        this.setNumber = setNumber;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public DateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(DateTime endTime) {
        this.endTime = endTime;
    }
}
