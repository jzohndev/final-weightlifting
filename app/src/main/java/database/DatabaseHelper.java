package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.ArrayMap;
import android.util.Log;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import data.LoadDates;

/**
 * Created by big1 on 7/15/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String LOG = "DatabaseHelper";
    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "lifting.db";

    // Table Names
    private static final String TABLE_WORKOUT = "WORKOUT";
    private static final String TABLE_EXERCISE = "EXERCISE";
    private static final String TABLE_WORKOUT_EXERCISE = "WORKOUT_EXERCISE";
    private static final String TABLE_SCHEDULED_SESSION = "SCHEDULED_SESSION";
    private static final String TABLE_ROUTINE = "ROUTINE";
    private static final String TABLE_ROUTINE_WORKOUT = "ROUTINE_WORKOUT";
    private static final String TABLE_SESSION_WORKOUT = "SESSION_WORKOUT";
    private static final String TABLE_SESSION_EXERCISE = "SESSION_EXERCISE";
    private static final String TABLE_SESSION_SET = "SESSION_SET";

    ////////////////////////////////////////////////////////////////////////////////////////////////
    private static final String KEY_SESSION_ID = "SessionId";
    private static final String KEY_WORKOUT_ID = "WorkoutId";
    private static final String KEY_DATE = "Date";
    private static final String KEY_STATUS = "Status";

    private static final String KEY_NOTE = "Note";
    private static final String KEY_START_TIME = "StartTime";
    private static final String KEY_END_TIME = "EndTime";

    private static final String KEY_EXERCISE_ID = "ExerciseId";
    private static final String KEY_NUMBER_OF_SETS = "NumberOfSets";
    private static final String KEY_DEFAULT_REPS = "DefaultReps";

    private static final String KEY_REPS = "Reps";
    private static final String KEY_WEIGHT = "Weight";

    private static final String KEY_ROUTINE_ID = "RoutineId";
    private static final String KEY_NAME = "Name";
    private static final String KEY_MUSCLE_GROUP = "MuscleGroup";
    private static final String KEY_DESCRIPTION = "Description";
    private static final String KEY_ICON_ID = "IconId";
    private static final String KEY_DEFAULT_SETS = "DefaultSets";
    private static final String KEY_TIMES_PERFORMED = "TimesPreformed";
    private static final String KEY_CREATED_DATE = "CreatedDate";
    private static final String KEY_IN_USE = "InUse";
    private static final String KEY_DAY = "Day";
    private static final String KEY_SET_NUMBER = "SetNumber";
    private static final String KEY_GOAL_SETS = "GoalSets";
    private static final String KEY_GOAL_REPS = "GoalReps";
    private static final String KEY_NUMBER_OF_DAYS = "NumberOfDays";
    private static final String KEY_IS_COMPLETED = "IsCompleted";
    private static final String KEY_TIMES_COMPLETED = "TimesCompleted";
    private static final String KEY_COMPLETED = "Completed";

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*                                                                                              Table Create Statements                                                                       */
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // TABLE_EXERCISE
    private static final String CREATE_TABLE_EXERCISE = "CREATE TABLE " + TABLE_EXERCISE
            + " ("
            + KEY_EXERCISE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_NAME + " TEXT NOT NULL,"
            + KEY_MUSCLE_GROUP + " TEXT,"
            + KEY_DESCRIPTION + " TEXT,"
            + KEY_DEFAULT_SETS + " INTEGER,"
            + KEY_DEFAULT_REPS + " INTEGER,"
            + KEY_TIMES_PERFORMED + " INTEGER,"
            + " UNIQUE (" + KEY_EXERCISE_ID + ", " + KEY_NAME + ")"
            + ")";

    // TABLE_WORKOUT
    private static final String CREATE_TABLE_WORKOUT = "CREATE TABLE " + TABLE_WORKOUT
            + " ("
            + KEY_WORKOUT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_NAME + " TEXT NOT NULL,"
            + KEY_DESCRIPTION + " TEXT,"
            + KEY_CREATED_DATE + " DATE,"
            + KEY_ICON_ID + " INTEGER,"
            + KEY_TIMES_COMPLETED + " INTEGER,"
            + " UNIQUE (" + KEY_WORKOUT_ID + ", " + KEY_NAME + ")"
            + ")";

    //TABLE_ROUTINE
    private static final String CREATE_TABLE_ROUTINE = "CREATE TABLE " + TABLE_ROUTINE
            + " ("
            + KEY_ROUTINE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_NAME + " TEXT NOT NULL,"
            + KEY_DESCRIPTION + " TEXT,"
            + KEY_CREATED_DATE + " DATE,"
            + KEY_NUMBER_OF_DAYS + " INTEGER,"
            + KEY_ICON_ID + " INTEGER,"
            + KEY_IN_USE + " TEXT,"
            + " UNIQUE (" + KEY_ROUTINE_ID + ", " + KEY_NAME + ")"
            + ")";

    //TABLE_WORKOUT_EXERCISE
    private static final String CREATE_TABLE_WORKOUT_EXERCISE = "CREATE TABLE " + TABLE_WORKOUT_EXERCISE
            + " ("
            + KEY_WORKOUT_ID + " INTEGER NOT NULL,"
            + KEY_EXERCISE_ID + " INTEGER NOT NULL,"
            + KEY_GOAL_SETS + " INTEGER,"
            + KEY_GOAL_REPS + " INTEGER,"
            + " PRIMARY KEY (" + KEY_WORKOUT_ID + ", " + KEY_EXERCISE_ID + "),"
            + " FOREIGN KEY (" + KEY_WORKOUT_ID + ") REFERENCES " + TABLE_WORKOUT + " (" + KEY_WORKOUT_ID + "),"
            + " FOREIGN KEY (" + KEY_EXERCISE_ID + ") REFERENCES " + TABLE_EXERCISE + " (" + KEY_EXERCISE_ID + ")"
            + ")";


    //TABLE_ROUTINE_WORKOUT
    private static final String CREATE_TABLE_ROUTINE_WORKOUT = "CREATE TABLE " + TABLE_ROUTINE_WORKOUT
            + " ("
            + KEY_ROUTINE_ID + " INTEGER NOT NULL,"
            + KEY_WORKOUT_ID + " INTEGER NOT NULL,"
            + KEY_DAY + " TEXT,"
            + " PRIMARY KEY (" + KEY_ROUTINE_ID + ", " + KEY_DAY + "),"
            + " FOREIGN KEY (" + KEY_ROUTINE_ID + ") REFERENCES " + TABLE_ROUTINE + " (" + KEY_ROUTINE_ID + "),"
            + " FOREIGN KEY (" + KEY_WORKOUT_ID + ") REFERENCES " + TABLE_WORKOUT + " (" + KEY_WORKOUT_ID + ")"
            + ")";

    //TABLE_SCHEDULED_SESSION
    private static final String CREATE_TABLE_SCHEDULED_SESSION = "CREATE TABLE " + TABLE_SCHEDULED_SESSION
            + " ("
            + KEY_SESSION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_WORKOUT_ID + " INTEGER,"
            + KEY_DATE + " DATE,"
            + KEY_STATUS + " TEXT,"
            + " FOREIGN KEY (" + KEY_WORKOUT_ID + ") REFERENCES " + TABLE_WORKOUT + " (" + KEY_WORKOUT_ID + ")"
            + ")";

    //TABLE_SESSION_WORKOUT
    private static final String CREATE_TABLE_SESSION_WORKOUT = "CREATE TABLE " + TABLE_SESSION_WORKOUT
            + " ("
            + KEY_SESSION_ID + " INTEGER NOT NULL,"
            + KEY_WORKOUT_ID + " INTEGER NOT NULL,"
            + KEY_NOTE + " TEXT,"
            + KEY_START_TIME + " TEXT,"
            + KEY_END_TIME + " TEXT,"
            + " PRIMARY KEY (" + KEY_SESSION_ID + ", " + KEY_WORKOUT_ID + "),"
            + " FOREIGN KEY (" + KEY_SESSION_ID + ") REFERENCES " + TABLE_SCHEDULED_SESSION + " (" + KEY_SESSION_ID + "),"
            + " FOREIGN KEY (" + KEY_WORKOUT_ID + ") REFERENCES " + TABLE_WORKOUT + " (" + KEY_WORKOUT_ID + ")"
            + ")";

    //TABLE_SESSION_EXERCISE
    private static final String CREATE_TABLE_SESSION_EXERCISE = "CREATE TABLE " + TABLE_SESSION_EXERCISE
            + " ("
            + KEY_SESSION_ID + " INTEGER NOT NULL,"
            + KEY_WORKOUT_ID + " INTEGER NOT NULL,"
            + KEY_EXERCISE_ID + " INTEGER NOT NULL,"
            + KEY_NUMBER_OF_SETS + " INTEGER,"
            + KEY_DEFAULT_REPS + " INTEGER,"
            + KEY_END_TIME + " DATE,"
            + " UNIQUE (" + KEY_SESSION_ID + ", " + KEY_EXERCISE_ID + "),"
            + " FOREIGN KEY (" + KEY_SESSION_ID + ") REFERENCES " + TABLE_SCHEDULED_SESSION + " (" + KEY_SESSION_ID + "),"
            + " FOREIGN KEY (" + KEY_WORKOUT_ID + ") REFERENCES " + TABLE_WORKOUT + " (" + KEY_WORKOUT_ID + "),"
            + " FOREIGN KEY (" + KEY_EXERCISE_ID + ") REFERENCES " + TABLE_EXERCISE + " (" + KEY_EXERCISE_ID + ")"
            + ")";

    //TABLE_SESSION_SET
    private static final String CREATE_TABLE_SESSION_SET = "CREATE TABLE " + TABLE_SESSION_SET
            + " ("
            + KEY_SESSION_ID + " INTEGER NOT NULL,"
            + KEY_EXERCISE_ID + " INTEGER NOT NULL,"
            + KEY_SET_NUMBER + " INTEGER NOT NULL,"
            + KEY_NOTE + " TEXT,"
            + KEY_REPS + " INTEGER,"
            + KEY_WEIGHT + " INTEGER,"
            + KEY_END_TIME + " DATE,"
            + " PRIMARY KEY (" + KEY_SESSION_ID + ", " + KEY_EXERCISE_ID + ", " + KEY_SET_NUMBER + "),"
            + " FOREIGN KEY (" + KEY_SESSION_ID + ") REFERENCES " + TABLE_SESSION_WORKOUT + " (" + KEY_SESSION_ID + "),"
            + " FOREIGN KEY (" + KEY_EXERCISE_ID + ") REFERENCES " + TABLE_EXERCISE + " (" + KEY_EXERCISE_ID + ")"
            + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*                                                                                              onCreate & onUpgrade                                                                          */
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_EXERCISE);
        db.execSQL(CREATE_TABLE_WORKOUT);
        db.execSQL(CREATE_TABLE_SCHEDULED_SESSION);
        db.execSQL(CREATE_TABLE_ROUTINE);
        db.execSQL(CREATE_TABLE_WORKOUT_EXERCISE);
        db.execSQL(CREATE_TABLE_ROUTINE_WORKOUT);
        db.execSQL(CREATE_TABLE_SESSION_WORKOUT);
        db.execSQL(CREATE_TABLE_SESSION_EXERCISE);
        db.execSQL(CREATE_TABLE_SESSION_SET);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(TABLE_EXERCISE);
        db.execSQL(TABLE_WORKOUT);
        db.execSQL(TABLE_SCHEDULED_SESSION);
        db.execSQL(TABLE_ROUTINE);
        db.execSQL(TABLE_WORKOUT_EXERCISE);
        db.execSQL(TABLE_ROUTINE_WORKOUT);
        db.execSQL(TABLE_SESSION_WORKOUT);
        db.execSQL(TABLE_SESSION_EXERCISE);
        db.execSQL(TABLE_SESSION_SET);
        onCreate(db);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*                                                                                              EXERCISE                                                                                      */
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public long createExercise(Exercise exercise) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, exercise.getName());
        values.put(KEY_MUSCLE_GROUP, exercise.getMuscleGroup());
        values.put(KEY_DESCRIPTION, exercise.getDescription());
        values.put(KEY_DEFAULT_SETS, exercise.getDefaultSets());
        values.put(KEY_DEFAULT_REPS, exercise.getDefaultReps());
        return db.insert(TABLE_EXERCISE, null, values);
    }

    public Exercise getExercise(long id) {
        SQLiteDatabase db = getReadableDatabase();
        Exercise exercise = new Exercise();
        String selectQuery = "SELECT * FROM " + TABLE_EXERCISE
                + " WHERE " + KEY_EXERCISE_ID + " = '" + id + "'";
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            exercise.setId(c.getInt(c.getColumnIndex(KEY_EXERCISE_ID)));
            exercise.setName(c.getString(c.getColumnIndex(KEY_NAME)));
            exercise.setMuscleGroup(c.getString(c.getColumnIndex(KEY_MUSCLE_GROUP)));
            exercise.setDescription(c.getString(c.getColumnIndex(KEY_DESCRIPTION)));
            exercise.setDefaultSets(c.getInt(c.getColumnIndex(KEY_DEFAULT_SETS)));
            exercise.setDefaultReps(c.getInt(c.getColumnIndex(KEY_DEFAULT_REPS)));
            exercise.setTimesPerformed(c.getInt(c.getColumnIndex(KEY_TIMES_PERFORMED)));
        } else exercise.setId(-1);
        c.close();
        return exercise;
    }

    public List<Exercise> getExercisesMuscleGroup(String muscleGroup) {
        SQLiteDatabase db = getReadableDatabase();
        List<Exercise> exercises = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_EXERCISE +
                " WHERE " + KEY_MUSCLE_GROUP + " = '" + muscleGroup + "'";
        Log.e(LOG, selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                Exercise exercise = new Exercise();
                exercise.setId(c.getInt(c.getColumnIndex(KEY_EXERCISE_ID)));
                exercise.setName(c.getString(c.getColumnIndex(KEY_NAME)));
                exercise.setMuscleGroup(c.getString(c.getColumnIndex(KEY_MUSCLE_GROUP)));
                exercise.setDescription(c.getString(c.getColumnIndex(KEY_DESCRIPTION)));
                exercise.setDefaultSets(c.getInt(c.getColumnIndex(KEY_DEFAULT_SETS)));
                exercise.setDefaultReps(c.getInt(c.getColumnIndex(KEY_DEFAULT_REPS)));
                exercise.setTimesPerformed(c.getInt(c.getColumnIndex(KEY_TIMES_PERFORMED)));
                exercises.add(exercise);
            } while (c.moveToNext());
        }
        c.close();
        return exercises;
    }


    public List<Exercise> getAllExercises() {
        SQLiteDatabase db = getReadableDatabase();
        List<Exercise> exercises = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_EXERCISE;
        Log.e("getAllExercises", selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                Exercise exercise = new Exercise();
                exercise.setId(c.getInt(c.getColumnIndex(KEY_EXERCISE_ID)));
                exercise.setName(c.getString(c.getColumnIndex(KEY_NAME)));
                exercise.setMuscleGroup(c.getString(c.getColumnIndex(KEY_MUSCLE_GROUP)));
                exercise.setDescription(c.getString(c.getColumnIndex(KEY_DESCRIPTION)));
                exercise.setDefaultSets(c.getInt(c.getColumnIndex(KEY_DEFAULT_SETS)));
                exercise.setDefaultReps(c.getInt(c.getColumnIndex(KEY_DEFAULT_REPS)));
                exercise.setTimesPerformed(c.getInt(c.getColumnIndex(KEY_TIMES_PERFORMED)));
                exercises.add(exercise);
            } while (c.moveToNext());
        }
        c.close();
        return exercises;
    }

    public long updateExerciseSets(long exerciseId, int defaultSets) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DEFAULT_SETS, defaultSets);
        return db.update(TABLE_EXERCISE, values, KEY_EXERCISE_ID + " = ?",
                new String[]{String.valueOf(exerciseId)});
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*                                                                                              WORKOUT                                                                                       */
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public long createWorkout(Workout workout) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, workout.getName());
        values.put(KEY_DESCRIPTION, workout.getDescription());
        values.put(KEY_CREATED_DATE, LoadDates.dateTimeToString(workout.getCreatedDate()));
        return db.insert(TABLE_WORKOUT, null, values);
    }

    public Workout getWorkout(String checkWorkoutName) {
        Workout workout = new Workout();
        String selectQuery = "SELECT * FROM " + TABLE_WORKOUT
                + " WHERE " + KEY_NAME + " = '" + checkWorkoutName + "'";
        Log.e("getWorkout", selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            workout.setId(c.getInt(c.getColumnIndex(KEY_WORKOUT_ID)));
            workout.setName(c.getString(c.getColumnIndex(KEY_NAME)));
            workout.setDescription(c.getString(c.getColumnIndex(KEY_DESCRIPTION)));
            workout.setCreatedDate(LoadDates.stringToDateTime(c.getString(c.getColumnIndex(KEY_DATE))));
        }
        c.close();
        return workout;
    }

    public Workout getWorkout(long workoutId) {
        Workout workout = new Workout();
        String selectQuery = "SELECT * FROM " + TABLE_WORKOUT
                + " WHERE " + KEY_WORKOUT_ID + " = '" + workoutId + "'";
        Log.e("getWorkout", selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            workout.setId(c.getInt(c.getColumnIndex(KEY_WORKOUT_ID)));
            workout.setName(c.getString(c.getColumnIndex(KEY_NAME)));
            workout.setDescription(c.getString(c.getColumnIndex(KEY_DESCRIPTION)));
            workout.setCreatedDate(LoadDates.stringToDateTime(c.getString(c.getColumnIndex(KEY_CREATED_DATE))));
        }
        c.close();
        return workout;
    }

    public List<Workout> getAllWorkouts() {
        List<Workout> workouts = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_WORKOUT;
        Log.e(LOG, selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                Workout workout = new Workout();
                workout.setId(c.getInt(c.getColumnIndex(KEY_WORKOUT_ID)));
                workout.setName(c.getString(c.getColumnIndex(KEY_NAME)));
                workout.setDescription(c.getString(c.getColumnIndex(KEY_DESCRIPTION)));
                workout.setCreatedDate(LoadDates.stringToDateTime(c.getString(c.getColumnIndex(KEY_CREATED_DATE))));
                workouts.add(workout);
            } while (c.moveToNext());
        }
        c.close();
        return workouts;
    }

    public long updateWorkout(Workout workout) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_WORKOUT_ID, workout.getId());
        values.put(KEY_NAME, workout.getName());
        values.put(KEY_DESCRIPTION, workout.getDescription());
        values.put(KEY_CREATED_DATE, LoadDates.dateTimeToString(workout.getCreatedDate()));
        values.put(KEY_TIMES_COMPLETED, workout.getTimesCompleted());

        return db.update(TABLE_WORKOUT, values, KEY_WORKOUT_ID + " = ?",
                new String[]{String.valueOf(workout.getId())});
    }

    public int getNumberOfWorkouts() {
        int numberOfWorkouts = -1;
        String selectQuery = "SELECT COUNT(*) AS Count FROM " + TABLE_WORKOUT;
        Log.e("getNumberOfWorkouts", selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) numberOfWorkouts = c.getInt(c.getColumnIndex("Count"));
        c.close();
        return numberOfWorkouts;
    }

    public long deleteWorkout(long workoutId) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_WORKOUT,
                KEY_WORKOUT_ID + " = ? ",
                new String[]{String.valueOf(workoutId)});
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*                                                                                              SCHEDULED_SESSION                                                                             */
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void createScheduledSession(ScheduledSession scheduledSession, SessionWorkout sessionWorkout,
                                       List<SessionExercise> sessionExerciseList) {
        // adds a row to Scheduled_Session table
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_WORKOUT_ID, scheduledSession.getWorkoutId());
        if (scheduledSession.getDate() != null) {
            values.put(KEY_DATE, LoadDates.localDateToString(scheduledSession.getDate()));
        }
        values.put(KEY_STATUS, scheduledSession.getStatus());
        long scheduledSessionRowNumber = db.insert(TABLE_SCHEDULED_SESSION, null, values);
        Log.e(TABLE_SCHEDULED_SESSION, "...row " + scheduledSessionRowNumber + " created");
        values.clear();

        // gets the Session Id to use in following code
        Log.e(TABLE_SCHEDULED_SESSION, "...fetching SessionId");
        final int sessionId = getScheduledSessionId(db, scheduledSession.getDate());
        if (sessionId == -1) {
            Log.e(TABLE_SCHEDULED_SESSION, "ERROR: SessionId wasn't found");
        }

        // adds a row to Session_Workout table
        values.put(KEY_SESSION_ID, sessionId);
        values.put(KEY_WORKOUT_ID, sessionWorkout.getWorkoutId());
        long sessionWorkoutRowNumber = db.insert(TABLE_SESSION_WORKOUT, null, values);
        Log.e(TABLE_SESSION_WORKOUT, "...row " + sessionWorkoutRowNumber + " created");
        values.clear();

        // adds rows to Session_Exercise table
        long sessionExerciseRowNumber;
        for (int i = 0; i < sessionExerciseList.size(); i++) {
            values.put(KEY_SESSION_ID, sessionId);
            values.put(KEY_WORKOUT_ID, sessionWorkout.getWorkoutId());
            values.put(KEY_EXERCISE_ID, sessionExerciseList.get(i).getExerciseId());
            values.put(KEY_NUMBER_OF_SETS, sessionExerciseList.get(i).getNumberOfSets());
            values.put(KEY_DEFAULT_REPS, sessionExerciseList.get(i).getDefaultReps());
            sessionExerciseRowNumber = db.insert(TABLE_SESSION_EXERCISE, null, values);
            Log.e(TABLE_SESSION_EXERCISE, "...row " + sessionExerciseRowNumber + " created");
            values.clear();

            // adds rows to Session_Set table
            long sessionSetRowNumber;
            for (int x = 0; x < sessionExerciseList.get(i).getNumberOfSets(); x++) {
                values.put(KEY_SESSION_ID, sessionId);
                values.put(KEY_EXERCISE_ID, sessionExerciseList.get(i).getExerciseId());
                values.put(KEY_SET_NUMBER, (x + 1));
                sessionSetRowNumber = db.insert(TABLE_SESSION_SET, null, values);
                Log.e(TABLE_SESSION_SET, "...row " + sessionSetRowNumber + " created");
                values.clear();
            }
        }
    }

    public int getScheduledSessionId(SQLiteDatabase db, LocalDate date) {
        String selectQuery = "SELECT " + KEY_SESSION_ID + " FROM " + TABLE_SCHEDULED_SESSION
                + " WHERE " + KEY_DATE + " = '" + LoadDates.localDateToString(date) + "'";
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            int sessionId = c.getInt(c.getColumnIndex(KEY_SESSION_ID));
            c.close();
            return sessionId;
        } else {
            c.close();
            return -1;
        }
    }

    public ScheduledSession getScheduledSession(LocalDate date) {
        SQLiteDatabase db = getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_SCHEDULED_SESSION
                + " WHERE " + KEY_DATE + " = '" + LoadDates.localDateToString(date) + "'";
        Cursor c = db.rawQuery(selectQuery, null);
        ScheduledSession scheduledSession = new ScheduledSession();
        if (c.moveToFirst()) {
            Log.e("getScheduledSession", "row found");
            scheduledSession.setSessionId(c.getInt(c.getColumnIndex(KEY_SESSION_ID)));
            scheduledSession.setWorkoutId(c.getInt(c.getColumnIndex(KEY_WORKOUT_ID)));
            scheduledSession.setDate(date);
            scheduledSession.setStatus(c.getString(c.getColumnIndex(KEY_STATUS)));
        }
        c.close();
        return scheduledSession;
    }/*

    // TODO
    public ScheduledSession getFullSchedule(LocalDate localDate) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_SCHEDULED_SESSION
                + " WHERE " + KEY_DATE + " = '" + LoadDates.localDateToString(localDate) + "'";
        Log.e("getFullSchedule", selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);
        ScheduledSession scheduledSession = new ScheduledSession();
        if (c.moveToFirst()) {
            scheduledSession.setDate(LoadDates.stringToLocalDate(c.getString(c.getColumnIndex(KEY_DATE))));
            scheduledSession.setWorkout(getWorkout(c.getInt(c.getColumnIndex(KEY_WORKOUT_ID))));
            scheduledSession.setStatus(c.getString(c.getColumnIndex(KEY_STATUS)));
        }
        c.close();
        return scheduledSession;
    }*/

/*
    public ScheduledSession getSchedule(LocalDate date) {
        String selectQuery = "SELECT * FROM " + TABLE_SCHEDULED_SESSION + " sc"
                + " INNER JOIN " + TABLE_WORKOUT
                + " wo ON sc." + KEY_WORKOUT_ID + "=wo." + KEY_WORKOUT_ID
                + " WHERE " + KEY_DATE + " = '" + LoadDates.localDateToString(date) + "'";
        Log.e("getSchedule", selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        ScheduledSession schedule = new ScheduledSession();
        if (c.moveToFirst()) {
            schedule.setDate(LoadDates.stringToLocalDate(c.getString(c.getColumnIndex(KEY_DATE))));
            schedule.setStatus(c.getString(c.getColumnIndex(KEY_STATUS)));

            Workout workout = new Workout();
            workout.setId(c.getInt(c.getColumnIndex(KEY_WORKOUT_ID)));
            workout.setName(c.getString(c.getColumnIndex(KEY_NAME)));
            workout.setDescription(c.getString(c.getColumnIndex(KEY_DESCRIPTION)));
            //workout.setCreatedDate(LoadDates.stringToDateTime(c.getString(c.getColumnIndex(KEY_DATE))));
            workout.setTimesCompleted(c.getInt(c.getColumnIndex(KEY_TIMES_COMPLETED)));

            schedule.setWorkout(workout);
        } else {
            Workout workout = new Workout();
            workout.setId(-1);
            schedule.setStatus("no workout");
            schedule.setWorkout(workout);
        }
        c.close();
        return schedule;
    }
*/

    public List<ScheduledSession> getMonthSchedules(int month) {
        String monthString;
        if (month < 10) {
            monthString = "0" + month;
        } else {
            monthString = "" + month;
        }

        String selectQuery = "SELECT * FROM " + TABLE_SCHEDULED_SESSION
                + " WHERE strftime('%m', " + KEY_DATE + ") = '" + monthString + "'";
        Log.e("getSchedules", selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        List<ScheduledSession> schedules = new ArrayList<>();
        if (c.moveToFirst()) {
            ScheduledSession currentScheduledSession;
            do {
                currentScheduledSession = new ScheduledSession();
                currentScheduledSession.setSessionId(c.getInt(c.getColumnIndex(KEY_SESSION_ID)));
                currentScheduledSession.setWorkoutId(c.getInt(c.getColumnIndex(KEY_WORKOUT_ID)));
                currentScheduledSession.setDate(LoadDates.stringToLocalDate(c.getString(c.getColumnIndex(KEY_DATE))));
                currentScheduledSession.setStatus(c.getString(c.getColumnIndex(KEY_STATUS)));
                schedules.add(currentScheduledSession);
            } while (c.moveToNext());
        }
        c.close();
        return schedules;
    }

    public void updateScheduledSession(ScheduledSession session){ // TODO
        SQLiteDatabase db = getWritableDatabase();
        final String[] args = {String.valueOf(session.getSessionId()),
                String.valueOf(session.getWorkoutId())};
        ContentValues values = new ContentValues();
        values.put(KEY_STATUS, session.getStatus());
        db.update(TABLE_SCHEDULED_SESSION, values, KEY_SESSION_ID + " = ? AND " +
                KEY_WORKOUT_ID + " = ?", args);
    }

    public void deleteScheduledSession(int sessionId) {
        SQLiteDatabase db = getWritableDatabase();
        String[] args = {String.valueOf(sessionId)};
        long rowsDeleted;

        rowsDeleted = db.delete(TABLE_SCHEDULED_SESSION, KEY_SESSION_ID + " = ? ", args);
        Log.e("deleteScheduledSession", rowsDeleted + " rows deleted from " + TABLE_SCHEDULED_SESSION);
        rowsDeleted = db.delete(TABLE_SESSION_WORKOUT, KEY_SESSION_ID + " = ? ", args);
        Log.e("deleteScheduledSession", rowsDeleted + " rows deleted from " + TABLE_SESSION_WORKOUT);
        rowsDeleted = db.delete(TABLE_SESSION_EXERCISE, KEY_SESSION_ID + " = ? ", args);
        Log.e("deleteScheduledSession", rowsDeleted + " rows deleted from " + TABLE_SESSION_EXERCISE);
        rowsDeleted = db.delete(TABLE_SESSION_SET, KEY_SESSION_ID + " = ? ", args);
        Log.e("deleteScheduledSession", rowsDeleted + " rows deleted from " + TABLE_SESSION_SET);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*                                                                                              ROUTINE                                                                                       */
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public long createRoutine(Routine routine) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, routine.getName());
        values.put(KEY_DESCRIPTION, routine.getDescription());
        //values.put(KEY_CREATED_DATE, LoadDates.dateTimeToString(routine.getCreatedDate())); TODO
        values.put(KEY_NUMBER_OF_DAYS, routine.getNumberOfDays());
        values.put(KEY_ICON_ID, routine.getIconId());
        values.put(KEY_IN_USE, routine.getInUse());
        return db.insert(TABLE_ROUTINE, null, values);
    }

    public int getNumberOfRoutines() {
        int numberOfRoutines;
        String selectQuery = "SELECT COUNT(*) AS Count FROM " + TABLE_ROUTINE;
        Log.e("getNumberOfRoutines", selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) numberOfRoutines = c.getInt(c.getColumnIndex("Count"));
        else numberOfRoutines = -1;
        c.close();
        return numberOfRoutines;
    }

    public Routine getRoutine(String routineName) {
        Routine routine = new Routine();
        String selectQuery = "SELECT * FROM " + TABLE_ROUTINE
                + " WHERE " + KEY_NAME + " = '" + routineName + "'";
        Log.e("getRoutine", selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            routine.setId(c.getInt(c.getColumnIndex(KEY_WORKOUT_ID)));
            routine.setName(c.getString(c.getColumnIndex(KEY_NAME)));
            routine.setDescription(c.getString(c.getColumnIndex(KEY_DESCRIPTION)));
            routine.setIconId(c.getInt(c.getColumnIndex(KEY_ICON_ID)));
            //routine.setCreatedDate(LoadDates.stringToDateTime(c.getString(c.getColumnIndex(KEY_DATE)))); TODO
            routine.setInUse(c.getString(c.getColumnIndex(KEY_IN_USE)));
        }
        c.close();
        return routine;
    }

    public List<Routine> getAllRoutines() {
        List<Routine> routines = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_ROUTINE;
        Log.e(LOG, selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        Log.e("getAllRoutines", DatabaseUtils.dumpCursorToString(c));
        if (c.moveToFirst()) {
            do {
                Routine routine = new Routine();
                routine.setId(c.getInt(c.getColumnIndex(KEY_ROUTINE_ID)));
                routine.setName(c.getString(c.getColumnIndex(KEY_NAME)));
                routine.setDescription(c.getString(c.getColumnIndex(KEY_DESCRIPTION)));
                //routine.setCreatedDate(LoadDates.stringToDateTime(c.getString(c.getColumnIndex(KEY_CREATED_DATE)))); TODO
                routine.setNumberOfDays(c.getInt(c.getColumnIndex(KEY_NUMBER_OF_DAYS)));
                routine.setIconId(c.getInt(c.getColumnIndex(KEY_ICON_ID)));
                routine.setInUse(c.getString(c.getColumnIndex(KEY_IN_USE)));
                routines.add(routine);
            } while (c.moveToNext());
        }
        c.close();
        return routines;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*                                                                                              WORKOUT_EXERCISE                                                                              */
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public long createWorkoutExercise(long workoutId, long exerciseId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_WORKOUT_ID, workoutId);
        values.put(KEY_EXERCISE_ID, exerciseId);
        return db.insert(TABLE_WORKOUT_EXERCISE, null, values);
    }

    public long createWorkoutExercise(long workoutId, long exerciseId, long sets, long reps) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_WORKOUT_ID, workoutId);
        values.put(KEY_EXERCISE_ID, exerciseId);
        values.put(KEY_GOAL_SETS, sets);
        values.put(KEY_GOAL_REPS, reps);
        return db.insert(TABLE_WORKOUT_EXERCISE, null, values);
    }

    public List<Exercise> getWorkoutExercises(long workoutId) {
        String selectQuery = "SELECT * FROM " + TABLE_WORKOUT_EXERCISE
                + " WE INNER JOIN " + TABLE_EXERCISE
                + " E ON WE." + KEY_EXERCISE_ID + "=E." + KEY_EXERCISE_ID
                + " WHERE " + KEY_WORKOUT_ID + " = '" + workoutId + "'";
        Log.e(LOG, selectQuery);
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        List<Exercise> exercises = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                Exercise exercise = new Exercise();
                exercise.setId(c.getInt(c.getColumnIndex(KEY_EXERCISE_ID)));
                exercise.setName(c.getString(c.getColumnIndex(KEY_NAME)));
                exercise.setMuscleGroup(c.getString(c.getColumnIndex(KEY_MUSCLE_GROUP)));
                exercise.setDescription(c.getString(c.getColumnIndex(KEY_DESCRIPTION)));
                exercise.setTimesPerformed(c.getInt(c.getColumnIndex(KEY_TIMES_PERFORMED)));

                int goalSets = c.getInt(c.getColumnIndex(KEY_GOAL_SETS));
                int goalReps = c.getInt(c.getColumnIndex(KEY_GOAL_REPS));
                int defaultSets = c.getInt(c.getColumnIndex(KEY_DEFAULT_SETS));
                int defaultReps = c.getInt(c.getColumnIndex(KEY_DEFAULT_REPS));

                if ((goalSets != defaultSets) && (goalSets != 0)) {
                    exercise.setDefaultSets(goalSets);
                } else {
                    exercise.setDefaultSets(defaultSets);
                }

                if ((goalReps != defaultReps) && (goalReps != 0)) {
                    exercise.setDefaultReps(goalReps);
                } else {
                    exercise.setDefaultReps(defaultReps);
                }


                exercises.add(exercise);
            } while (c.moveToNext());
        }
        c.close();
        return exercises;
    }

    public List<Integer> getWorkoutExerciseIds(long workoutId) {
        String selectQuery = "SELECT " + KEY_EXERCISE_ID + " FROM " + TABLE_WORKOUT_EXERCISE
                + " WHERE " + KEY_WORKOUT_ID + " = '" + workoutId + "'";
        Log.e(LOG, selectQuery);
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        List<Integer> exerciseIds = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                exerciseIds.add(c.getInt(c.getColumnIndex(KEY_EXERCISE_ID)));
            } while (c.moveToNext());
        }
        c.close();
        return exerciseIds;
    }

    public int getWorkoutExerciseNumberOf(long workoutId) {
        String selectQuery = "SELECT COUNT(*) AS Count FROM " + TABLE_WORKOUT_EXERCISE
                + " WHERE " + KEY_WORKOUT_ID + " = '" + workoutId + "'";
        Log.e("getWorkoutEx...Number", selectQuery);
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        int numberOfExercises = -1;
        if (c.moveToFirst()) {
            numberOfExercises = c.getInt(c.getColumnIndex("Count"));
        }
        c.close();
        return numberOfExercises;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*                                                                                              ROUTINE_WORKOUT                                                                               */
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public long createRoutineWorkout(long routineId, long workoutId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ROUTINE_ID, routineId);
        values.put(KEY_WORKOUT_ID, workoutId);
        return db.insert(TABLE_ROUTINE_WORKOUT, null, values);
    }

    public long createRoutineWorkout(long routineId, long workoutId, String day) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ROUTINE_ID, routineId);
        values.put(KEY_WORKOUT_ID, workoutId);
        values.put(KEY_DAY, day);
        return db.insert(TABLE_ROUTINE_WORKOUT, null, values);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*                                                                                              SESSION_WORKOUT                                                                               */
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*public long createSessionWorkout(ScheduledSession schedule) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_WORKOUT_ID, schedule.getWorkout().getId());
        return db.insert(TABLE_SESSION_WORKOUT, null, values);
    }*/

    public long createSessionWorkout(long workoutId, DateTime dateTime, DateTime startTime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_WORKOUT_ID, workoutId);
        values.put(KEY_DATE, LoadDates.dateTimeToString(dateTime));
        values.put(KEY_START_TIME, LoadDates.dateTimeToString(dateTime));
        return db.insert(TABLE_SESSION_WORKOUT, null, values);
    }

    public SessionWorkout getSessionWorkout(int workoutId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_SESSION_WORKOUT
                + " WHERE " + KEY_WORKOUT_ID + " = '" + workoutId + "'";
        Log.e(LOG, selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);

        SessionWorkout sessionWorkout = new SessionWorkout();
        if (c.moveToFirst()) {
            sessionWorkout.setSessionId(c.getInt(c.getColumnIndex(KEY_SESSION_ID)));
            sessionWorkout.setWorkoutId(c.getInt(c.getColumnIndex(KEY_WORKOUT_ID)));
            sessionWorkout.setNote(c.getString(c.getColumnIndex(KEY_NOTE)));
            if (c.getString(c.getColumnIndex(KEY_START_TIME)) != null)
                sessionWorkout.setStartTime(LoadDates.stringToDateTime
                        (c.getString(c.getColumnIndex(KEY_START_TIME))));
            sessionWorkout.setNote(c.getString(c.getColumnIndex(KEY_NOTE)));
            if (c.getString(c.getColumnIndex(KEY_END_TIME)) != null)
                sessionWorkout.setEndTime(LoadDates.stringToDateTime
                        (c.getString(c.getColumnIndex(KEY_END_TIME))));
        }
        c.close();
        return sessionWorkout;
    }

    public long getSessionWorkoutId(DateTime dateTime) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT " + KEY_SESSION_ID + " FROM " + TABLE_SESSION_WORKOUT
                + " WHERE " + KEY_DATE + " = '" + LoadDates.dateTimeToString(dateTime) + "'";
        Log.e(LOG, selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);
        long i = -1;
        if (c.moveToFirst()) {
            i = c.getInt(c.getColumnIndex(KEY_SESSION_ID));
        }
        c.close();
        return i;
    }

    public boolean getSessionWorkoutIsComplete(DateTime dateTime) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT " + KEY_IS_COMPLETED + " FROM " + TABLE_SESSION_WORKOUT
                + " WHERE " + KEY_DATE + " = '" + LoadDates.dateTimeToString(dateTime) + "'";
        Log.e(LOG, selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            String isComplete = c.getString(c.getColumnIndex(KEY_IS_COMPLETED));
            if (isComplete.equals("yes")) {
                c.close();
                return true;
            } else {
                c.close();
                return false;
            }
        } else {
            c.close();
            return false;
        }
    }

    public List<SessionWorkout> getCompletedSessionWorkouts() {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_SESSION_WORKOUT
                + " WHERE " + KEY_END_TIME + " IS NOT NULL";
        Log.e(LOG, selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);
        List<SessionWorkout> sessionWorkoutList = new ArrayList<>();
        SessionWorkout currentSessionWorkout;
        if (c.moveToFirst()) {
            do {
                currentSessionWorkout = new SessionWorkout();
                currentSessionWorkout.setSessionId(c.getInt(c.getColumnIndex(KEY_SESSION_ID)));
                currentSessionWorkout.setWorkoutId(c.getInt(c.getColumnIndex(KEY_WORKOUT_ID)));
                currentSessionWorkout.setNote(c.getString(c.getColumnIndex(KEY_NOTE)));
                currentSessionWorkout.setStartTime(LoadDates.stringToDateTime(
                        c.getString(c.getColumnIndex(KEY_START_TIME))));
                currentSessionWorkout.setEndTime((LoadDates.stringToDateTime(
                        c.getString(c.getColumnIndex(KEY_END_TIME)))));
                sessionWorkoutList.add(currentSessionWorkout);
            } while (c.moveToNext());
        }
        c.close();
        return sessionWorkoutList;
    }

    /*public List<SessionWorkout> getSessionWorkoutsForMonth(int month) {
        String monthString;
        if (month < 10) {
            monthString = "0" + month;
        } else {
            monthString = "" + month;
        }

        String selectQuery = "SELECT ss." + KEY_WORKOUT_ID +
                ", ss." + KEY_DATE +
                ", sw." + KEY_START_TIME +
                ", sw." + KEY_END_TIME
                "FROM " + TABLE_SESSION_WORKOUT +
                "INNER JOIN " + TABLE_SCHEDULED_SESSION +
                "ON " + TABLE_SESSION_WORKOUT + "." + KEY_WORKOUT_ID +
                " = " + TABLE_SCHEDULED_SESSION + "." + KEY_WORKOUT_ID +
                "WHERE strftime('%m', " + KEY_DATE + ") = '" + monthString + "'";
        Log.e("getSchedules", selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
    }*/

    public int getSessionWorkoutTimesPreformed(long workoutId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT COUNT(*) AS Count FROM " + TABLE_SESSION_WORKOUT
                + " WHERE " + KEY_WORKOUT_ID + " = '" + workoutId + "'";
        Log.e(LOG, selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);
        int preformed = 0;
        if (c.moveToFirst()) {
            preformed = c.getInt(c.getColumnIndex("Count"));
        }
        c.close();
        return preformed;
    }

    public void updateSessionWorkout(SessionWorkout sessionWorkout) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        String[] args = {String.valueOf(sessionWorkout.getSessionId()),
                String.valueOf(sessionWorkout.getWorkoutId())};
        if (sessionWorkout.getStartTime() != null) {
            values.put(KEY_START_TIME, LoadDates.dateTimeToString(sessionWorkout.getStartTime()));
        }
        if (sessionWorkout.getEndTime() != null) {
            values.put(KEY_END_TIME, LoadDates.dateTimeToString(sessionWorkout.getEndTime()));
        }
        db.update(TABLE_SESSION_WORKOUT, values, KEY_SESSION_ID + " = ? AND " + KEY_WORKOUT_ID + " = ?",
                args);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*                                                                                              SESSION_EXERCISE                                                                              */
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void createSessionExercises(int sessionId, List<Exercise> exercises) {
        List<SessionExercise> sessionExercises = new ArrayList<>();
        for (Exercise exercise : exercises) {
            SessionExercise sessionExercise = new SessionExercise();
            sessionExercise.setSessionId(sessionId);
            sessionExercise.setExerciseId(exercise.getId());
            sessionExercise.setNumberOfSets(exercise.getDefaultSets());
            sessionExercises.add(sessionExercise);
        }

        SQLiteDatabase db = this.getWritableDatabase();
        long log;
        for (int i = 0; i < sessionExercises.size(); i++) {
            ContentValues values = new ContentValues();
            SessionExercise sessionExercise = sessionExercises.get(i);
            values.put(KEY_SESSION_ID, sessionId);
            values.put(KEY_EXERCISE_ID, sessionExercise.getExerciseId());
            values.put(KEY_NUMBER_OF_SETS, sessionExercise.getNumberOfSets());

            log = db.insert(TABLE_SESSION_EXERCISE, null, values);
            Log.e("createSession", log + "");

            createSessionSets(db, sessionExercise, exercises.get(i).getDefaultReps());

        }
    }




    /*public SessionExercise getSessionWorkoutExercise(long sessionId, long exerciseId, int setNumber) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_SESSION_EXERCISE
                + " WHERE " + KEY_SESSION_ID + " = '" + sessionId + "' AND "
                + KEY_EXERCISE_ID + " = '" + exerciseId + "' AND "
                + KEY_SET_NUMBER + " = '" + setNumber + "'";
        Log.e(LOG, selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);
        Log.e(LOG, DatabaseUtils.dumpCursorToString(c));
        SessionExercise sessionExercise = new SessionExercise();
        if (c.moveToFirst()) {
            sessionExercise.setSessionId(sessionId);
            sessionExercise.setExerciseId(exerciseId);
            sessionExercise.setSetNumber(setNumber);
            sessionExercise.setRepsCompleted(c.getInt(c.getColumnIndex(KEY_REPS_COMPLETED)));
            sessionExercise.setWeightUsed(c.getInt(c.getColumnIndex(KEY_WEIGHT)));
        }
        c.close();
        return sessionExercise;
    }*/

    public long updateSessionExercise(SessionExercise sessionExercise) {
        SQLiteDatabase db = getWritableDatabase();
        final String[] args = {String.valueOf(sessionExercise.getSessionId()),
                String.valueOf(sessionExercise.getWorkoutId()),
                String.valueOf(sessionExercise.getExerciseId())};
        ContentValues values = new ContentValues();
        values.put(KEY_END_TIME, LoadDates.dateTimeToString(sessionExercise.getEndTime()));

        return db.update(TABLE_SESSION_EXERCISE, values, KEY_SESSION_ID + " = ? AND "
                        + KEY_WORKOUT_ID + " = ? AND " + KEY_EXERCISE_ID + " = ?",
                args);
    }

    public List<SessionExercise> getSessionExercises(int sessionId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_SESSION_EXERCISE
                + " WHERE " + KEY_SESSION_ID + " = '" + sessionId + "'";
        Log.e(LOG, selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);

        List<SessionExercise> sessionExercises = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                SessionExercise sessionExercise = new SessionExercise();
                sessionExercise.setSessionId(c.getInt(c.getColumnIndex(KEY_SESSION_ID)));
                sessionExercise.setWorkoutId(c.getInt(c.getColumnIndex(KEY_WORKOUT_ID)));
                sessionExercise.setExerciseId(c.getInt(c.getColumnIndex(KEY_EXERCISE_ID)));
                sessionExercise.setNumberOfSets(c.getInt(c.getColumnIndex(KEY_NUMBER_OF_SETS)));
                sessionExercise.setDefaultReps(c.getInt(c.getColumnIndex(KEY_DEFAULT_REPS)));
                if (c.getString(c.getColumnIndex(KEY_END_TIME)) != null) {
                    sessionExercise.setEndTime(LoadDates.stringToDateTime(c.getString(c.getColumnIndex(KEY_END_TIME))));
                }
                sessionExercises.add(sessionExercise);
            } while (c.moveToNext());
        }
        c.close();
        return sessionExercises;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*                                                                                              SESSION_SETS                                                                                  */
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void createSessionSets(SQLiteDatabase db, SessionExercise sessionExercise, int defaultReps) {
        for (int i = 0; i < sessionExercise.getNumberOfSets(); i++) {
            ContentValues values = new ContentValues();
            values.put(KEY_SESSION_ID, sessionExercise.getSessionId());
            values.put(KEY_EXERCISE_ID, sessionExercise.getExerciseId());
            values.put(KEY_SET_NUMBER, i + 1);
            //values.put(KEY_NOTE, );
            values.put(KEY_DEFAULT_REPS, defaultReps);
            //values.put(KEY_REPS, );
            //values.put(KEY_WEIGHT, );
            //values.put(KEY_END_TIME, );
            db.insert(TABLE_SESSION_SET, null, values);
        }
    }

/*
    public List<SessionSet> getSessionSets(SessionExercise sessionExercise, SQLiteDatabase db){
        String selectQuery = "SELECT * FROM " + TABLE_SESSION_SET
                + " WHERE " + KEY_SESSION_ID + " = '" + sessionExercise.getSessionId() + "' AND "
                + KEY_EXERCISE_ID + " = '" + sessionExercise.getExerciseId() + "'";
        Log.e("getSessionSets", selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);
        List<SessionSet> sessionSets = new ArrayList<>();
        if (c.moveToFirst()){
            do {
                SessionSet set = new SessionSet();
                set.setSessionId(c.getInt(c.getColumnIndex(KEY_SESSION_ID)));
                set.setExerciseId(c.getInt(c.getColumnIndex(KEY_EXERCISE_ID)));
                set.setSetNumber(c.getInt(c.getColumnIndex(KEY_SET_NUMBER)));
                set.setNote(c.getString(c.getColumnIndex(KEY_NOTE)));
                set.setDefaultReps(c.getInt(c.getColumnIndex(KEY_DEFAULT_REPS)));
                set.setActualReps(c.getInt(c.getColumnIndex(KEY_REPS)));
                set.setWeight(c.getInt(c.getColumnIndex(KEY_WEIGHT)));*/
/*
                if (c.getString(c.getColumnIndex(KEY_END_TIME)) != null){
                    set.setEndTime(LoadDates.stringToDateTime(c.getString(c.getColumnIndex(KEY_END_TIME))));
                }*//*

                sessionSets.add(set);
            } while (c.moveToNext());
        }
        c.close();
        return sessionSets;
    }
*/

    public List<SessionSet> getSessionSets(int exerciseId, int sessionId) {
        SQLiteDatabase db = getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_SESSION_SET
                + " WHERE " + KEY_SESSION_ID + " = '" + sessionId + "' AND "
                + KEY_EXERCISE_ID + " = '" + exerciseId + "'";
        Log.e("getSessionSets", selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);
        List<SessionSet> sessionSetList = new ArrayList<>();
        if (c.moveToFirst()) {
            SessionSet currentSessionSet;
            do {
                currentSessionSet = new SessionSet();
                currentSessionSet.setSessionId(c.getInt(c.getColumnIndex(KEY_SESSION_ID)));
                currentSessionSet.setExerciseId(c.getInt(c.getColumnIndex(KEY_EXERCISE_ID)));
                currentSessionSet.setSetNumber(c.getInt(c.getColumnIndex(KEY_SET_NUMBER)));
                currentSessionSet.setNote(c.getString(c.getColumnIndex(KEY_NOTE)));
                currentSessionSet.setReps(c.getInt(c.getColumnIndex(KEY_REPS)));
                currentSessionSet.setWeight(c.getInt(c.getColumnIndex(KEY_WEIGHT)));
                if (c.getString(c.getColumnIndex(KEY_END_TIME)) != null) {
                    currentSessionSet.setEndTime(LoadDates.stringToDateTime(
                            c.getString(c.getColumnIndex(KEY_END_TIME))));
                }
                sessionSetList.add(currentSessionSet);
            } while (c.moveToNext());
        }
        c.close();
        return sessionSetList;
    }

    public Map<Integer, List<SessionSet>> getSessionSets(List<Exercise> exercises, int sessionId) {
        SQLiteDatabase db = getReadableDatabase();

        Map<Integer, List<SessionSet>> sessionSetMap = new HashMap<>();
        for (Exercise currentExercise : exercises) {
            String selectQuery = "SELECT * FROM " + TABLE_SESSION_SET
                    + " WHERE " + KEY_SESSION_ID + " = '" + sessionId + "' AND "
                    + KEY_EXERCISE_ID + " = '" + currentExercise.getId() + "'";
            Log.e("getSessionSets", selectQuery);
            Cursor c = db.rawQuery(selectQuery, null);
            List<SessionSet> sessionSetList = new ArrayList<>();
            if (c.moveToFirst()) {
                SessionSet currentSessionSet;
                do {
                    currentSessionSet = new SessionSet();
                    currentSessionSet.setSessionId(c.getInt(c.getColumnIndex(KEY_SESSION_ID)));
                    currentSessionSet.setExerciseId(c.getInt(c.getColumnIndex(KEY_EXERCISE_ID)));
                    currentSessionSet.setSetNumber(c.getInt(c.getColumnIndex(KEY_SET_NUMBER)));
                    currentSessionSet.setNote(c.getString(c.getColumnIndex(KEY_NOTE)));
                    currentSessionSet.setReps(c.getInt(c.getColumnIndex(KEY_REPS)));
                    currentSessionSet.setWeight(c.getInt(c.getColumnIndex(KEY_WEIGHT)));
                    if (c.getString(c.getColumnIndex(KEY_END_TIME)) != null) {
                        currentSessionSet.setEndTime(LoadDates.stringToDateTime(
                                c.getString(c.getColumnIndex(KEY_END_TIME))));
                    }
                    sessionSetList.add(currentSessionSet);
                } while (c.moveToNext());
            }
            c.close();
            sessionSetMap.put(currentExercise.getId(), sessionSetList);
        }
        return sessionSetMap;
    }

    public long updateSessionSet(int sessionId, SessionSet sessionSet) {
        SQLiteDatabase db = getWritableDatabase();
        String[] args = {String.valueOf(sessionId),
                String.valueOf(sessionSet.getExerciseId()),
                String.valueOf(sessionSet.getSetNumber())};
        ContentValues values = new ContentValues();
        values.put(KEY_NOTE, sessionSet.getNote());
        values.put(KEY_REPS, sessionSet.getReps());
        values.put(KEY_WEIGHT, sessionSet.getWeight());
        values.put(KEY_END_TIME, LoadDates.dateTimeToString(sessionSet.getEndTime()));

        return db.update(TABLE_SESSION_SET, values, KEY_SESSION_ID + " = ? AND "
                        + KEY_EXERCISE_ID + " = ? AND " + KEY_SET_NUMBER + " = ?",
                args);
    }
}
