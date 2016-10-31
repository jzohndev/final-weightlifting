package database;

import java.util.Date;

/**
 * Created by big1 on 8/23/2016.
 */
public class SessionWorkout {
    private long mSessionId = -1;
    private Date mDate;
    private long mWorkoutId = -1;
    private Date mStartTime;
    private String mNote;
    private Date mEndTime;

    public SessionWorkout(){

    }

    public SessionWorkout(long sessionId, Date date, long workoutId){
        this.mSessionId = sessionId;
        this.mDate = date;
        this.mWorkoutId = workoutId;
    }

    public void setSessionId(long mSessionId) {
        this.mSessionId = mSessionId;
    }

    public long getSessionId() {
        return mSessionId;
    }

    public void setDate(Date date){
        this.mDate = date;
    }

    public Date getDate(){
        return mDate;
    }

    public void setWorkoutId(long workoutId){
        this.mWorkoutId = workoutId;
    }

    public long getWorkoutId(){
        return mWorkoutId;
    }

    public void setStartTime(Date startTime){
        this.mStartTime = startTime;
    }

    public Date getStartTime(){
        return mStartTime;
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
