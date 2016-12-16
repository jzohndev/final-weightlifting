package c3_schedule;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jzohndev.no_bullshit_weightlifting_new.R;

import org.joda.time.LocalDate;

import java.util.List;

import data.Icons;
import data.IntentResolver;
import database.DatabaseHelper;
import database.Exercise;
import database.ScheduledSession;
import database.SessionWorkout;
import database.Workout;
import workout.WorkoutList;

public class TodayChildFragment extends Fragment {
    private View mView;
    private RelativeLayout mRelativeLayout;

    private ScheduledSession mScheduleSession;
    private Workout mWorkout;
    private List<Exercise> mExerciseList;

    public TodayChildFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_today_child, container, false);
        mRelativeLayout = (RelativeLayout) mView.findViewById(R.id.relative_layout);

        if (getTodaySchedule()){
            initTodayView();
        } else {
            initNoWorkoutView();
        }

        return mView;
    }

    private boolean getTodaySchedule() {
        final DatabaseHelper db = new DatabaseHelper(getContext());
        mScheduleSession = db.getScheduledSession(LocalDate.now());

        if (mScheduleSession.getSessionId() != -1){
            mWorkout = db.getWorkout(mScheduleSession.getWorkoutId());
            mExerciseList = db.getWorkoutExercises(mScheduleSession.getWorkoutId());
        }

        return mScheduleSession.getSessionId() != -1;
    }


    private void initTodayView() {
        final FloatingActionButton fab = (FloatingActionButton) mView.findViewById(R.id.fab);
        fab.setVisibility(View.INVISIBLE);
        fab.setClickable(false);

        final LayoutInflater inflater = LayoutInflater.from(getContext());
        final View headerWorkoutName = inflater.inflate(R.layout.today_header, null);
        final View workoutView = inflater.inflate(R.layout.today_schedule, null);
        final View footerSpace = inflater.inflate(R.layout.today_footer, null);

        final TextView workoutName = (TextView) headerWorkoutName.findViewById(R.id.workout_name_text_view);
        workoutName.setText(mWorkout.getName());

        final TodayScheduleAdapter adapter = new TodayScheduleAdapter();
        final ListView listView = (ListView) workoutView.findViewById(R.id.list_view);
        listView.addHeaderView(headerWorkoutName);
        listView.addFooterView(footerSpace);
        listView.setAdapter(adapter);

        final ImageView overflowMenu = (ImageView) headerWorkoutName.findViewById(R.id.overflow_menu_image_view);
        overflowMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(getContext(), v);
                popupMenu.getMenuInflater()
                        .inflate(R.menu.begin_overflow_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case (R.id.remove):
                                new DatabaseHelper(getContext()).deleteScheduledSession(mScheduleSession.getSessionId());
                                Toast.makeText(getContext(), "Workout removed.", Toast.LENGTH_SHORT).show();
                                initNoWorkoutView();
                                break;
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
        mRelativeLayout.removeAllViews();
        mRelativeLayout.addView(workoutView);
    }

    private void initNoWorkoutView() {
        final FloatingActionButton fab = (FloatingActionButton) mView.findViewById(R.id.fab);
        fab.setVisibility(View.VISIBLE);
        fab.setClickable(true);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentResolver resolver = IntentResolver.getInstance();
                resolver.setIntent("TodayChildFragment", "TodayChildFragment", -1, LocalDate.now());
                startActivity(new Intent(getActivity(), WorkoutList.class));
            }
        });

        final LayoutInflater inflater = LayoutInflater.from(getContext());
        final View noWorkoutView = inflater.inflate(R.layout.no_workout_today, null);
        mRelativeLayout.removeAllViews();
        mRelativeLayout.addView(noWorkoutView);
    }

    // Adapter
    public class TodayScheduleAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mExerciseList.size();
        }

        @Override
        public Object getItem(int position) {
            return mExerciseList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return mExerciseList.get(position).getId();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater)
                        getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.exercise_list, null);
            }
            ImageView icon = (ImageView) convertView.findViewById(R.id.icon_image_view);

            icon.setImageResource(Icons.getMuscleGroupIcon(mExerciseList.get(position).getMuscleGroup()));
            TextView name = (TextView) convertView.findViewById(R.id.exercise_name_text_view);
            name.setText(mExerciseList.get(position).getName());
            TextView muscleGroup = (TextView) convertView.findViewById(R.id.muscle_group_text_view);
            muscleGroup.setText(mExerciseList.get(position).getMuscleGroup());
            return convertView;
        }
    }
}
