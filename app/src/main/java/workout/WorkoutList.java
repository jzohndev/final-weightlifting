package workout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.jzohndev.no_bullshit_weightlifting_new.R;

import org.joda.time.LocalDate;

import java.util.Date;

import data.IntentResolver;
import c0_all.MenuPager;

import data.LoadDates;
import database.DatabaseHelper;
import database.Schedule;
import database.Workout;
import c2_manage.WorkoutsAdapter;

/**
 * Created by big1 on 7/23/2016.
 */
public class WorkoutList extends Activity implements WorkoutsAdapter.WorkoutOnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_workouts);

        final WorkoutsAdapter adapter = new WorkoutsAdapter(this);
        adapter.setOnItemClickListener(this);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


        /*adapter.setOnItemClickListener(new WorkoutsAdapter.WorkoutOnItemClickListener() {
            @Override
            public void onItemClick(Workout workout, View v) {
                Intent i;
                DatabaseHelper db;
                Log.e("adapter.setOnItemC..", String.valueOf(workout.getId()));
                switch (fadfsd.orgFrom) {
                    case ("RoutineBuilder"):
                        RoutineBuilderHelper.addWorkout(workout);
                        i = new Intent(WorkoutList.this, RoutineBuilder.class);
                        objective = new Bundle();
                        objective.putString("from", "WorkoutList");
                        objective.putString("orgFrom", "RoutineBuilder");
                        objective.putLong("purpose", -1);
                        i.putExtras(objective);
                        startActivity(i);
                        break;
                    case ("TodayFragment"):
                        db = new DatabaseHelper(getApplicationContext());
                        long test = db.createSchedule(LoadDates.getTodayDate(), workout.getId());
                        Log.e("TodayFrag", String.valueOf(test));
                        i = new Intent(WorkoutList.this, Schedule.class);
                        objective = new Bundle();
                        objective.putString("from", "WorkoutList");
                        objective.putString("orgFrom", "TodayFragment");
                        objective.putLong("purpose", -1);
                        i.putExtras(objective);
                        startActivity(i);
                        break;
                    case ("WeekFragment"):
                        db = new DatabaseHelper(getApplicationContext());
                        Bundle extras = getIntent().getExtras();
                        String selectedDate = extras.getString("date");
                        Log.e("WorkoutList WeekFragmen", "selectedDate: " + selectedDate);
                        long scheduleAddedRow = db.createSchedule(LoadDates.stringToDate(selectedDate), workout.getId());
                        Log.e("WorkoutList WeekFragmen", "Schedule created at row: " + String.valueOf(scheduleAddedRow));
                        i = new Intent(WorkoutList.this, Schedule.class);
                        objective = new Bundle();
                        objective.putString("from", "WorkoutList");
                        objective.putString("orgFrom", "WeekFragment");
                        objective.putLong("purpose", -1);
                        i.putExtras(objective);
                        startActivity(i);
                        break;

                }

            }
        });*/
    }

    @Override
    public void onItemClick(Workout workout, View v) {
        final IntentResolver resolver = IntentResolver.getInstance();
        final DatabaseHelper db = new DatabaseHelper(this);

        Schedule schedule;
        LocalDate resolverDate;
        LocalDate date;

        switch (resolver.getFrom()) {
            case ("TodayChildFragment"):
                resolverDate = resolver.getDate();
                date = new LocalDate(resolverDate);
                schedule = new Schedule(date, workout);
                db.createSchedule(schedule);
                resolver.setIntent("WorkoutList", "TodayChildFragment", -1);
                startActivity(new Intent(WorkoutList.this, MenuPager.class));
                break;
            case ("WeekChildFragment"):
                resolverDate = resolver.getDate();
                date = new LocalDate(resolverDate);
                schedule = new Schedule(date, workout);
                db.createSchedule(schedule);
                resolver.setIntent("WorkoutList", "WeekChildFragment", -1);
                startActivity(new Intent(WorkoutList.this, MenuPager.class));
                break;
        }
    }
}
