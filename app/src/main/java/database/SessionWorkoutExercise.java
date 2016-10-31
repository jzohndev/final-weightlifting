package database;

import java.util.Date;

/**
 * Created by big1 on 8/3/2016.
 */
public class SessionWorkoutExercise {
    private long mSessionId;
    private long mExerciseId;
    private int mSetNumber;
    private int mRepsCompleted = -1;
    private int mWeightUsed;
    private String mNote;
    private Date mEndTime;

    public SessionWorkoutExercise() {
    }

    public SessionWorkoutExercise(long sessionId, long exerciseId, int setNumber) {
        this.mSessionId = sessionId;
        this.mExerciseId = exerciseId;
        this.mSetNumber = setNumber;
    }

    public SessionWorkoutExercise(long sessionId, long exerciseId, int setNumber, Date endTime) {
        this.mSessionId = sessionId;
        this.mExerciseId = exerciseId;
        this.mSetNumber = setNumber;
        this.mEndTime = endTime;
    }

    public SessionWorkoutExercise(long sessionId, long exerciseId,
                                  int setNumber, int repsCompleted,
                                  int weightUsed, String note, Date endTime) {
        this.mSessionId = sessionId;
        this.mExerciseId = exerciseId;
        this.mSetNumber = setNumber;
        this.mRepsCompleted = repsCompleted;
        this.mWeightUsed = weightUsed;
        this.mNote = note;
        this.mEndTime = endTime;
    }

    public void setSessionId(long mSessionId) {
        this.mSessionId = mSessionId;
    }

    public long getSessionId() {
        return mSessionId;
    }

    public void setExerciseId(long mExerciseId) {
        this.mExerciseId = mExerciseId;
    }

    public long getExerciseId() {
        return mExerciseId;
    }

    public void setSetNumber(int mSetNumber) {
        this.mSetNumber = mSetNumber;
    }

    public int getSetNumber() {
        return mSetNumber;
    }

    public void setRepsCompleted(int mRepsCompleted) {
        this.mRepsCompleted = mRepsCompleted;
    }

    public int getRepsCompleted() {
        return mRepsCompleted;
    }

    public void setWeightUsed(int mWeightUsed) {
        this.mWeightUsed = mWeightUsed;
    }

    public int getWeightUsed() {
        return mWeightUsed;
    }

    public void setNote(String note) {
        this.mNote = note;
    }

    public String getNote() {
        return mNote;
    }

    public void setEndTime(Date endTime) {
        this.mEndTime = endTime;
    }

    public Date getEndTime() {
        return mEndTime;
    }
}
