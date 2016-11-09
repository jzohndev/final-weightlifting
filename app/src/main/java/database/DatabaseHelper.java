package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.util.ArrayMap;
import android.util.Log;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.Date;
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
    private static final String TABLE_EXERCISE = "EXERCISE";
    private static final String TABLE_WORKOUT = "WORKOUT";
    private static final String TABLE_SCHEDULE = "SCHEDULE";
    private static final String TABLE_ROUTINE = "ROUTINE";
    private static final String TABLE_WORKOUT_EXERCISE = "WORKOUT_EXERCISE";
    private static final String TABLE_ROUTINE_WORKOUT = "ROUTINE_WORKOUT";
    private static final String TABLE_SESSION_WORKOUT = "SESSION_WORKOUT";
    private static final String TABLE_SESSION_EXERCISE = "SESSION_EXERCISE";
    private static final String TABLE_SESSION_SET = "SESSION_SET";

    // Column Names
    private static final String KEY_WORKOUT_ID = "WorkoutId";
    private static final String KEY_EXERCISE_ID = "ExerciseId";
    private static final String KEY_ROUTINE_ID = "RoutineId";
    private static final String KEY_SESSION_ID = "SessionId";
    private static final String KEY_NAME = "Name";
    private static final String KEY_MUSCLE_GROUP = "MuscleGroup";
    private static final String KEY_DESCRIPTION = "Description";
    private static final String KEY_ICON_ID = "IconId";
    private static final String KEY_DEFAULT_SETS = "DefaultSets";
    private static final String KEY_DEFAULT_REPS = "DefaultReps";
    private static final String KEY_TIMES_PERFORMED = "TimesPreformed";
    private static final String KEY_CREATED_DATE = "CreatedDate";
    private static final String KEY_IN_USE = "InUse";
    private static final String KEY_DAY = "Day";
    private static final String KEY_SET_NUMBER = "SetNumber";
    private static final String KEY_REPS_COMPLETED = "RepsCompleted";
    private static final String KEY_DATE = "Date";
    private static final String KEY_GOAL_SETS = "GoalSets";
    private static final String KEY_GOAL_REPS = "GoalReps";
    private static final String KEY_NUMBER_OF_DAYS = "NumberOfDays";
    private static final String KEY_WEIGHT = "Weight";
    private static final String KEY_IS_COMPLETED = "IsCompleted";
    private static final String KEY_TIMES_COMPLETED = "TimesCompleted";
    private static final String KEY_START_TIME = "StartTime";
    private static final String KEY_NOTE = "Note";
    private static final String KEY_END_TIME = "EndTime";
    private static final String KEY_WEIGHT_USED = "WeightUsed";
    private static final String KEY_COMPLETED = "Completed";

    private static final String KEY_STATUS = "Status";

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

    //TABLE_SCHEDULE
    private static final String CREATE_TABLE_SCHEDULE = "CREATE TABLE " + TABLE_SCHEDULE
            + " ("
            + "ID INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_DATE + " DATE,"
            + KEY_WORKOUT_ID + " INTEGER,"
            + KEY_COMPLETED + " TEXT," // TODO REMOVE
            + KEY_STATUS + " TEXT,"
            + " FOREIGN KEY (" + KEY_WORKOUT_ID + ") REFERENCES " + TABLE_WORKOUT + " (" + KEY_WORKOUT_ID + ")"
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

    //TABLE_SESSION_WORKOUT
    private static final String CREATE_TABLE_SESSION_WORKOUT = "CREATE TABLE " + TABLE_SESSION_WORKOUT
            + " ("
            + KEY_SESSION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_DATE + " DATE NOT NULL,"
            + KEY_WORKOUT_ID + " INTEGER NOT NULL,"
            + KEY_START_TIME + " TEXT,"
            + KEY_NOTE + " TEXT,"
            + KEY_END_TIME + " TEXT,"
            + " UNIQUE (" + KEY_SESSION_ID + ", " + KEY_DATE + ", " + KEY_WORKOUT_ID + ")"
            + ")";

    //TABLE_SESSION_EXERCISE
    private static final String CREATE_TABLE_SESSION_EXERCISE = "CREATE TABLE " + TABLE_SESSION_EXERCISE
            + " ("
            + KEY_SESSION_ID + " INTEGER NOT NULL,"
            + KEY_EXERCISE_ID + " INTEGER NOT NULL,"
            + KEY_NUMBER_OF_SETS + " INTEGER NOT NULL,"
            + KEY_REPS_COMPLETED + " INTEGER,"
            + KEY_WEIGHT_USED + " INTEGER,"
            + KEY_NOTE + " TEXT,"
            + KEY_END_TIME + " TEXT,"
            + " PRIMARY KEY (" + KEY_SESSION_ID + ", " + KEY_EXERCISE_ID + ", " + KEY_SET_NUMBER + "),"
            + " FOREIGN KEY (" + KEY_SESSION_ID + ") REFERENCES " + TABLE_SESSION_WORKOUT + " (" + KEY_SESSION_ID + "),"
            + " FOREIGN KEY (" + KEY_EXERCISE_ID + ") REFERENCES " + TABLE_EXERCISE + " (" + KEY_EXERCISE_ID + ")"
            + ")";

    //TABLE_SESSION_SET
    private static final String CREATE_TABLE_SESSION_SET = "CREATE TABLE " + TABLE_SESSION_SET
            + " ("
            + KEY_SESSION_ID + " INTEGER NOT NULL,"
            + KEY_EXERCISE_ID + " INTEGER NOT NULL,"
            + KEY_SET_NUMBER + " INTEGER NOT NULL,"
            + KEY_NOTE + " TEXT,"// TODO
            + KEY_DEFAULT_REPS + " INTEGER,"
            + KEY_ACTUAL_REPS + " INTEGER,"
            + KEY_WEIGHT + " INTEGER,"
            + // TODO

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
        db.execSQL(CREATE_TABLE_SCHEDULE);
        db.execSQL(CREATE_TABLE_ROUTINE);
        db.execSQL(CREATE_TABLE_WORKOUT_EXERCISE);
        db.execSQL(CREATE_TABLE_ROUTINE_WORKOUT);
        db.execSQL(CREATE_TABLE_SESSION_WORKOUT);
        db.execSQL(CREATE_TABLE_SESSION_EXERCISE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(TABLE_EXERCISE);
        db.execSQL(TABLE_WORKOUT);
        db.execSQL(TABLE_SCHEDULE);
        db.execSQL(TABLE_ROUTINE);
        db.execSQL(TABLE_WORKOUT_EXERCISE);
        db.execSQL(TABLE_ROUTINE_WORKOUT);
        db.execSQL(TABLE_SESSION_WORKOUT);
        db.execSQL(TABLE_SESSION_EXERCISE);
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
        long i = db.insert(TABLE_EXERCISE, null, values);
        return i;
    }

    public Exercise getExercise(long id) {
        SQLiteDatabase db = getReadableDatabase();
        Exercise exercise = new Exercise();
        String selectQuery = "SELECT * FROM " + TABLE_EXERCISE
                + " WHERE " + KEY_EXERCISE_ID + " = '" + id + "'";
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            exercise.setId(c.getLong(c.getColumnIndex(KEY_EXERCISE_ID)));
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
                exercise.setId(c.getLong(c.getColumnIndex(KEY_EXERCISE_ID)));
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
                exercise.setId(c.getLong(c.getColumnIndex(KEY_EXERCISE_ID)));
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
        long i = db.update(TABLE_EXERCISE, values, KEY_EXERCISE_ID + " = ?",
                new String[]{String.valueOf(exerciseId)});
        return i;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*                                                                                              WORKOUT                                                                                       */
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public long createWorkout(Workout workout) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, workout.getName());
        values.put(KEY_DESCRIPTION, workout.getDescription());
        values.put(KEY_CREATED_DATE, LoadDates.dateToString(workout.getCreatedDate()));
        long i = db.insert(TABLE_WORKOUT, null, values);
        return i;
    }

    public Workout getWorkout(String checkWorkoutName) {
        Workout workout = new Workout();
        String selectQuery = "SELECT * FROM " + TABLE_WORKOUT
                + " WHERE " + KEY_NAME + " = '" + checkWorkoutName + "'";
        Log.e("getWorkout", selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            workout.setId(c.getLong(c.getColumnIndex(KEY_WORKOUT_ID)));
            workout.setName(c.getString(c.getColumnIndex(KEY_NAME)));
            workout.setDescription(c.getString(c.getColumnIndex(KEY_DESCRIPTION)));
            workout.setIconId(c.getLong(c.getColumnIndex(KEY_ICON_ID)));
            workout.setCreatedDate(LoadDates.stringToDate(c.getString(c.getColumnIndex(KEY_DATE))));
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
            workout.setId(c.getLong(c.getColumnIndex(KEY_WORKOUT_ID)));
            workout.setName(c.getString(c.getColumnIndex(KEY_NAME)));
            workout.setDescription(c.getString(c.getColumnIndex(KEY_DESCRIPTION)));
            workout.setIconId(c.getLong(c.getColumnIndex(KEY_ICON_ID)));
            workout.setCreatedDate(LoadDates.stringToDate(c.getString(c.getColumnIndex(KEY_CREATED_DATE))));
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
                workout.setId(c.getLong(c.getColumnIndex(KEY_WORKOUT_ID)));
                workout.setName(c.getString(c.getColumnIndex(KEY_NAME)));
                workout.setDescription(c.getString(c.getColumnIndex(KEY_DESCRIPTION)));
                workout.setCreatedDate(LoadDates.stringToDate(c.getString(c.getColumnIndex(KEY_CREATED_DATE))));
                workout.setIconId(c.getLong(c.getColumnIndex(KEY_ICON_ID)));
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
        values.put(KEY_CREATED_DATE, LoadDates.dateToString(workout.getCreatedDate()));
        values.put(KEY_ICON_ID, workout.getIconId());
        values.put(KEY_TIMES_COMPLETED, workout.getTimesCompleted());

        long row = db.update(TABLE_WORKOUT, values, KEY_WORKOUT_ID + " = ?",
                new String[]{String.valueOf(workout.getId())});

        return row;
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
    /*                                                                                              SCHEDULE                                                                                      */
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public long createSchedule(Schedule schedule) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DATE, LoadDates.dateToString(schedule.getDate().toDate()));
        values.put(KEY_WORKOUT_ID, schedule.getWorkoutId());
        values.put(KEY_COMPLETED, schedule.getCompleted());
        long i = db.insert(TABLE_SCHEDULE, null, values);
        return i;
    }

    // TODO
    public Schedule getFullSchedule(LocalDate localDate) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_SCHEDULE
                + " WHERE " + KEY_DATE + " = '" + LoadDates.localDateToString(localDate) + "'";
        Log.e("getFullSchedule", selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);
        Schedule schedule = new Schedule();
        if (c.moveToFirst()) {
            schedule.setDate(LoadDates.stringToLocalDate(c.getString(c.getColumnIndex(KEY_DATE))));
            schedule.setWorkout(getWorkout(c.getLong(c.getColumnIndex(KEY_WORKOUT_ID))));
            schedule.setStatus(c.getString(c.getColumnIndex(KEY_STATUS)));
        }
        c.close();
        return schedule;
    }

    public Schedule getSchedule(LocalDate date) {
        String selectQuery = "SELECT * FROM " + TABLE_SCHEDULE + " sc"
                + " INNER JOIN " + TABLE_WORKOUT
                + " wo ON sc." + KEY_WORKOUT_ID + "=wo." + KEY_WORKOUT_ID
                + " WHERE " + KEY_DATE + " = '" + LoadDates.localDateToString(date) + "'";
        Log.e("getSchedule", selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        Schedule schedule = new Schedule();
        if (c.moveToFirst()) {
            schedule.setDate(LoadDates.stringToLocalDate(c.getString(c.getColumnIndex(KEY_DATE))));
            schedule.setStatus(c.getString(c.getColumnIndex(KEY_STATUS)));

            Workout workout = new Workout();
            workout.setId(c.getInt(c.getColumnIndex(KEY_WORKOUT_ID)));
            workout.setName(c.getString(c.getColumnIndex(KEY_NAME)));
            workout.setDescription(c.getString(c.getColumnIndex(KEY_DESCRIPTION)));
            workout.setCreatedDate(LoadDates.stringToDateTime(c.getString(c.getColumnIndex(KEY_DATE))));
            workout.setTimesCompleted(c.getInt(c.getColumnIndex(KEY_TIMES_COMPLETED)));

            schedule.setWorkout(workout);
        } else {
            schedule.setStatus("no workout");
        }
        return schedule;
    }

    public List<Schedule> getMonthSchedules(int month) {
        String monthString;
        if (month < 10) {
            monthString = "0" + month;
        } else {
            monthString = "" + month;
        }

        String selectQuery = "SELECT * FROM " + TABLE_SCHEDULE
                + " WHERE strftime('%m', " + KEY_DATE + ") = '" + monthString + "'";
        Log.e("getSchedules", selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        List<Schedule> schedules = new ArrayList<>();
        if (c.moveToFirst()) {
            Schedule currentSchedule;
            do {
                currentSchedule = new Schedule();
                currentSchedule.setDate(LoadDates.stringToLocalDate(c.getString(c.getColumnIndex(KEY_DATE))));
                currentSchedule.setWorkoutId(c.getLong(c.getColumnIndex(KEY_WORKOUT_ID)));
                currentSchedule.setCompleted(c.getString(c.getColumnIndex(KEY_COMPLETED)));
                schedules.add(currentSchedule);
            } while (c.moveToNext());
        }
        c.close();
        return schedules;
    }

    public Map<Long, Date> getAllSchedules() {
        String selectQuery = "SELECT * FROM " + TABLE_SCHEDULE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        Map<Long, Date> schedules = new ArrayMap<>();
        if (c.moveToFirst()) {
            do {
                schedules.put(c.getLong(c.getColumnIndex(KEY_WORKOUT_ID)),
                        LoadDates.stringToDate(c.getString(c.getColumnIndex(KEY_DATE))));
            } while (c.moveToNext());
        }
        c.close();
        return schedules;
    }


    public long deleteSchedule(Date date) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_SCHEDULE,
                KEY_DATE + " = ? ",
                new String[]{String.valueOf(LoadDates.dateToString(date))});
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*                                                                                              ROUTINE                                                                                       */
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public long createRoutine(Routine routine) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, routine.getName());
        values.put(KEY_DESCRIPTION, routine.getDescription());
        values.put(KEY_CREATED_DATE, LoadDates.dateToString(routine.getCreatedDate()));
        values.put(KEY_NUMBER_OF_DAYS, routine.getNumberOfDays());
        values.put(KEY_ICON_ID, routine.getIconId());
        values.put(KEY_IN_USE, routine.getInUse());
        long i = db.insert(TABLE_ROUTINE, null, values);
        return i;
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
            routine.setId(c.getLong(c.getColumnIndex(KEY_WORKOUT_ID)));
            routine.setName(c.getString(c.getColumnIndex(KEY_NAME)));
            routine.setDescription(c.getString(c.getColumnIndex(KEY_DESCRIPTION)));
            routine.setIconId(c.getLong(c.getColumnIndex(KEY_ICON_ID)));
            routine.setCreatedDate(LoadDates.stringToDate(c.getString(c.getColumnIndex(KEY_DATE))));
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
                routine.setId(c.getLong(c.getColumnIndex(KEY_ROUTINE_ID)));
                routine.setName(c.getString(c.getColumnIndex(KEY_NAME)));
                routine.setDescription(c.getString(c.getColumnIndex(KEY_DESCRIPTION)));
                routine.setCreatedDate(LoadDates.stringToDate(c.getString(c.getColumnIndex(KEY_CREATED_DATE))));
                routine.setNumberOfDays(c.getInt(c.getColumnIndex(KEY_NUMBER_OF_DAYS)));
                routine.setIconId(c.getLong(c.getColumnIndex(KEY_ICON_ID)));
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
        long i = db.insert(TABLE_WORKOUT_EXERCISE, null, values);
        return i;
    }

    public long createWorkoutExercise(long workoutId, long exerciseId, long sets, long reps) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_WORKOUT_ID, workoutId);
        values.put(KEY_EXERCISE_ID, exerciseId);
        values.put(KEY_GOAL_SETS, sets);
        values.put(KEY_GOAL_REPS, reps);
        long i = db.insert(TABLE_WORKOUT_EXERCISE, null, values);
        return i;
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
                exercise.setId(c.getLong(c.getColumnIndex(KEY_EXERCISE_ID)));
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

    public List<Long> getWorkoutExerciseIds(long workoutId) {
        String selectQuery = "SELECT " + KEY_EXERCISE_ID + " FROM " + TABLE_WORKOUT_EXERCISE
                + " WHERE " + KEY_WORKOUT_ID + " = '" + workoutId + "'";
        Log.e(LOG, selectQuery);
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        List<Long> exerciseIds = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                exerciseIds.add(c.getLong(c.getColumnIndex(KEY_EXERCISE_ID)));
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
        if (c.moveToFirst()) return c.getInt(c.getColumnIndex("Count"));
        else return -1;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*                                                                                              ROUTINE_WORKOUT                                                                               */
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public long createRoutineWorkout(long routineId, long workoutId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ROUTINE_ID, routineId);
        values.put(KEY_WORKOUT_ID, workoutId);
        long i = db.insert(TABLE_ROUTINE_WORKOUT, null, values);
        return i;
    }

    public long createRoutineWorkout(long routineId, long workoutId, String day) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ROUTINE_ID, routineId);
        values.put(KEY_WORKOUT_ID, workoutId);
        values.put(KEY_DAY, day);
        long i = db.insert(TABLE_ROUTINE_WORKOUT, null, values);
        return i;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*                                                                                              SESSION_WORKOUT                                                                               */
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public long createSessionWorkout(Schedule schedule) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_WORKOUT_ID, schedule.getWorkout().getId());
        values.put(KEY_DATE, LoadDates.localDateToString(schedule.getDate()));
        long i = db.insert(TABLE_SESSION_WORKOUT, null, values);
        return i;
    }

    public long createSessionWorkout(long workoutId, Date date, Date startTime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_WORKOUT_ID, workoutId);
        values.put(KEY_DATE, LoadDates.dateToString(date));
        values.put(KEY_START_TIME, LoadDates.dateTimeToString(startTime));
        long i = db.insert(TABLE_SESSION_WORKOUT, null, values);
        return i;
    }

    public SessionWorkout getSessionWorkout(LocalDate date) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_SESSION_WORKOUT
                + " WHERE " + KEY_DATE + " = '" + LoadDates.localDateToString(date) + "'";
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

    public long getSessionWorkoutId(Date date) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT " + KEY_SESSION_ID + " FROM " + TABLE_SESSION_WORKOUT
                + " WHERE " + KEY_DATE + " = '" + LoadDates.dateToString(date) + "'";
        Log.e(LOG, selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);
        long i = -1;
        if (c.moveToFirst()) {
            i = c.getLong(c.getColumnIndex(KEY_SESSION_ID));
        }
        c.close();
        return i;
    }

    public boolean getSessionWorkoutIsComplete(Date date) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT " + KEY_IS_COMPLETED + " FROM " + TABLE_SESSION_WORKOUT
                + " WHERE " + KEY_DATE + " = '" + LoadDates.dateToString(date) + "'";
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
        Log.e(LOG, DatabaseUtils.dumpCursorToString(c));
        List<SessionWorkout> sessionWorkouts = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                SessionWorkout sessionWorkout = new SessionWorkout();
                sessionWorkout.setSessionId(c.getLong(c.getColumnIndex(KEY_SESSION_ID)));
                sessionWorkout.setDate(LoadDates.stringToDate(c.getString(c.getColumnIndex(KEY_DATE))));
                sessionWorkout.setWorkoutId(c.getLong(c.getColumnIndex(KEY_WORKOUT_ID)));
                sessionWorkout.setStartTime(LoadDates.stringToDateTime(c.getString(c.getColumnIndex(KEY_START_TIME))));
                sessionWorkout.setNote(c.getString(c.getColumnIndex(KEY_NOTE)));
                sessionWorkout.setEndTime((LoadDates.stringToDateTime(c.getString(c.getColumnIndex(KEY_END_TIME)))));
                sessionWorkouts.add(sessionWorkout);
            } while (c.moveToNext());
        }
        c.close();
        return sessionWorkouts;
    }

    public int getSessionWorkoutTimesPreformed(long workoutId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT COUNT(*) AS Count FROM " + TABLE_SESSION_WORKOUT
                + " WHERE " + KEY_WORKOUT_ID + " = '" + workoutId + "'";
        Log.e(LOG, selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            return c.getInt(c.getColumnIndex("Count"));
        }
        return 0;
    }

    public void updateSessionWorkout(Date date, Date endTime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_END_TIME, LoadDates.dateTimeToString(endTime));
        db.update(TABLE_SESSION_WORKOUT, values, KEY_DATE + " = ?",
                new String[]{String.valueOf(LoadDates.dateToString(date))});
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*                                                                                              SESSION_EXERCISE                                                                              */
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public long createSessionWorkoutExercise(long sessionId, long exerciseId, int setNumber,
                                             String isComplete, int numberOfRepsPreformed, int weight) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_SESSION_ID, sessionId);
        values.put(KEY_EXERCISE_ID, exerciseId);
        values.put(KEY_SET_NUMBER, setNumber);
        values.put(KEY_IS_COMPLETED, isComplete);
        values.put(KEY_REPS_COMPLETED, numberOfRepsPreformed);
        values.put(KEY_WEIGHT, weight);
        long i = db.insert(TABLE_SESSION_EXERCISE, null, values);
        return i;
    }

    public long createSessionWorkoutExercise(SessionExercise sessionExercise) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_SESSION_ID, (int) sessionExercise.getSessionId());
        values.put(KEY_EXERCISE_ID, (int) sessionExercise.getExerciseId());
        values.put(KEY_SET_NUMBER, sessionExercise.getSetNumber());
        values.put(KEY_REPS_COMPLETED, sessionExercise.getRepsCompleted());
        values.put(KEY_WEIGHT_USED, sessionExercise.getWeightUsed());
        values.put(KEY_NOTE, sessionExercise.getNote());
        values.put(KEY_END_TIME, LoadDates.dateTimeToString(sessionExercise.getEndTime()));
        long i = db.insert(TABLE_SESSION_EXERCISE, null, values);
        return i;
    }

    public void createSessionExercises(int sessionId, List<Exercise> exercises){
        SQLiteDatabase db = this.getWritableDatabase();



        List<SessionExercise> sessionExercises = new ArrayList<>();
        for (Exercise exercise : exercises){
            SessionExercise sessionExercise = new SessionExercise();
            sessionExercise.setSessionId(sessionId);
            sessionExercise.setExerciseId(exercise.getId());
            sessionExercise.setNumberOfSets(exercise.getDefaultSets());
        }

        int counter = // TODO DEFAULT REPS passed
        for (int i = 0; i < sessionExercises.size(); i++){
            ContentValues values = new ContentValues();
            SessionExercise sessionExercise = sessionExercises.get(i);
            values.put(KEY_SESSION_ID, sessionExercise.getSessionId());
            values.put(KEY_EXERCISE_ID, sessionExercise.getExerciseId());
            values.put(KEY_NUMBER_OF_SETS, sessionExercise.getNumberOfSets());

            db.insert(TABLE_SESSION_EXERCISE, null, values);

            db.createSessionSets(db, sessionExercise, exercises.get(i).getDefaultReps()); // TODO
        }
        for (SessionExercise sessionExercise : sessionExercises){
        }


    }

    public SessionExercise getSessionWorkoutExercise(long sessionId, long exerciseId, int setNumber) {
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
    }

    public List<SessionExercise> getSessionExercises(int sessionId, List<Exercise> workoutExercises) {
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
                sessionExercise.setExerciseId(c.getInt(c.getColumnIndex(KEY_EXERCISE_ID)));
                sessionExercise.setNumberOfSets(c.getInt(c.getColumnIndex(KEY_NUMBER_OF_SETS)));
                sessionExercise.setEndTime(LoadDates.stringToDateTime(c.getString(c.getColumnIndex(KEY_END_TIME))));

                sessionExercise.setExerciseSets(this.getSessionSets(sessionExercise, db));

                sessionExercises.add(sessionExercise);
            } while (c.moveToNext());
        }
        c.close();
        return sessionExercises;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*                                                                                              SESSION_SETS                                                                                  */
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void createSessionSets(SQLiteDatabase db, SessionExercise sessionExercise, int defaultReps){
        for (int i = 0; i < sessionExercise.getNumberOfSets(); i++){
            ContentValues values = new ContentValues();
            values.put(KEY_SESSION_ID, sessionExercise.getSessionId());
            values.put(KEY_EXERCISE_ID, sessionExercise.getExerciseId());
            values.put(KEY_SET_NUMBER, i+1);
            //values.put(KEY_NOTE, );
            values.put(KEY_DEFAULT_REPS, defaultReps);
            //values.put(KEY_ACTUAL_REPS, );
            //values.put(KEY_WEIGHT, );
            //values.put(KEY_END_TIME, );
            db.insert(TABLE_SESSION_SET, null, values);
        }
    }

    public long createSessionSet(){

    }

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
                set.setActualReps(c.getInt(c.getColumnIndex(KEY_ACTUAL_REPS)));
                set.setWeight(c.getInt(c.getColumnIndex(KEY_WEIGHT)));
                set.setEndTime(LoadDates.stringToDateTime(KEY_END_TIME));
                sessionSets.add(set);
            } while (c.moveToNext());
        }
        c.close();
        return sessionSets;
    }

    public SessionSet getSessionSet(){

    }
}
