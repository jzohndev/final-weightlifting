package c4_progression;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jzohndev.no_bullshit_weightlifting_new.R;

import java.util.List;
import java.util.Map;

import data.IntentResolver;
import data.LoadDates;
import database.DatabaseHelper;
import database.Exercise;
import database.SessionExercise;
import database.SessionWorkout;

/**
 * Created by big1 on 8/31/2016.
 */
public class HistorySelected extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_selected);

        final HistorySelectedAdapter adapter = new HistorySelectedAdapter();

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    // Adapter
    public class HistorySelectedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private final int MAIN_CONTENT = 0;
        private final int EXERCISE = 1;

        private SessionWorkout mSessionWorkout;
        private List<Integer> mWorkoutExerciseIds;
        private List<Exercise> mExercises;
        private Map<Integer, List<SessionExercise>> mSessionExerciseSets;

        public HistorySelectedAdapter() {
            final IntentResolver resolver = IntentResolver.getInstance();
            final DatabaseHelper db = new DatabaseHelper(getApplicationContext());

            mSessionWorkout = resolver.getSessionWorkout();
            mWorkoutExerciseIds = db.getWorkoutExerciseIds(mSessionWorkout.getWorkoutId());
            mExercises = db.getWorkoutExercises(mSessionWorkout.getWorkoutId());
            mSessionExerciseSets = new ArrayMap<>();
            for (int i = 0; i < mWorkoutExerciseIds.size(); i++) {
                int exerciseId = mWorkoutExerciseIds.get(i);
                int sessionId = mSessionWorkout.getSessionId();
                List<SessionExercise> tempSession = db.getSessionExercises(sessionId);
                mSessionExerciseSets.put(exerciseId, tempSession);
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v;
            if (viewType == MAIN_CONTENT) {
                v = LayoutInflater.from(getApplicationContext())
                        .inflate(R.layout.history_selected_main_content, parent, false);
                return new MainContentViewHolder(v);
            }
            v = LayoutInflater.from(getApplicationContext())
                    .inflate(R.layout.history_select_exercise_layout, parent, false);
            return new ExerciseLayoutViewHolder(v);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof MainContentViewHolder) {
                MainContentViewHolder eHolder = (MainContentViewHolder) holder;
                DatabaseHelper db = new DatabaseHelper(getApplicationContext());
                eHolder.workoutName.setText(db.getWorkout(mSessionWorkout.getWorkoutId())
                        .getName());
                eHolder.workoutDescription.setText(mSessionWorkout.getNote());
                eHolder.startTime.setText(LoadDates.dateTimeToString(
                        mSessionWorkout.getStartTime()));
                eHolder.endTime.setText(LoadDates.dateTimeToString(
                        mSessionWorkout.getEndTime()));
                eHolder.routineName.setText("none"); // TODO
                eHolder.numOfExercises.setText(String.valueOf(db.getSessionWorkoutTimesPreformed
                        (mSessionWorkout.getWorkoutId())));

            } else if (holder instanceof ExerciseLayoutViewHolder) {
                ExerciseLayoutViewHolder mHolder = (ExerciseLayoutViewHolder) holder;

                // Exercise Title
                View exerciseTitleView = getLayoutInflater().inflate(R.layout.history_selected_exercise_name, null);

                TextView exerciseTitle = (TextView) exerciseTitleView.findViewById(R.id.exercise_name_text_view);
                exerciseTitle.setText(mExercises.get(position-1).getName());
                mHolder.linearLayout.addView(exerciseTitleView);


                List<SessionExercise> currentExerciseSession = mSessionExerciseSets.get(mWorkoutExerciseIds.get(position - 1));
                for (int i = 0; i < currentExerciseSession.size(); i++) {
                    // Set Item
                    SessionExercise currentSessionSet = currentExerciseSession.get(i);
                    View setItemView = getLayoutInflater().inflate(R.layout.history_selected_set_item, null);

                    TextView setNumber = (TextView) setItemView.findViewById(R.id.set_number_text_view);
                    TextView reps = (TextView) setItemView.findViewById(R.id.reps_text_view);
                    TextView weight = (TextView) setItemView.findViewById(R.id.weight_text_view);
                    TextView weightUnit = (TextView) setItemView.findViewById(R.id.weight_unit_name_text_view);
                    TextView time = (TextView) setItemView.findViewById(R.id.set_time_stamp_text_view);

                    //setNumber.setText(String.valueOf(currentSessionSet.getSetNumber()));
                    //reps.setText(String.valueOf(currentSessionSet.getRepsCompleted()));
                    //weight.setText(String.valueOf(currentSessionSet.getWeightUsed()));
                    weightUnit.setText("lb"); // TODO
                    time.setText(LoadDates.dateTimeToString(currentSessionSet.getEndTime()));
                    mHolder.linearLayout.addView(setItemView);

                    /*if (currentSessionSet.getNote() != null) {
                        //Set Item Note
                        final View noteLayout = getLayoutInflater()
                                .inflate(R.layout.history_selected_note_text_view, null);
                        TextView note = (TextView) noteLayout.findViewById(R.id.note_text_view);
                        note.setText(currentSessionSet.getNote());
                        mHolder.linearLayout.addView(noteLayout);
                    }*/
                }
            }
        }

        @Override
        public int getItemCount() {
            return (mExercises.size() + 1);
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0) {
                return MAIN_CONTENT;
            } else {
                return EXERCISE;
            }
        }
    }

    public class MainContentViewHolder extends RecyclerView.ViewHolder {
        protected TextView workoutName;
        protected TextView workoutDescription;
        protected TextView startTime;
        protected TextView endTime;
        protected TextView routineName;
        protected TextView numOfExercises;

        public MainContentViewHolder(View v) {
            super(v);
            workoutName = (TextView) v.findViewById(R.id.workout_name_text_view);
            workoutDescription = (TextView) v.findViewById(R.id.description_text_view);
            startTime = (TextView) v.findViewById(R.id.start_time_text_view);
            endTime = (TextView) v.findViewById(R.id.end_time_text_view);
            routineName = (TextView) v.findViewById(R.id.routine_name_text_view);
            numOfExercises = (TextView) v.findViewById(R.id.number_of_exercises_text_view);
        }
    }

    public class ExerciseLayoutViewHolder extends RecyclerView.ViewHolder {
        protected LinearLayout linearLayout;

        public ExerciseLayoutViewHolder(View v) {
            super(v);
            linearLayout = (LinearLayout) v.findViewById(R.id.linear_layout);
        }
    }
/*
    class ViewHolder{
        protected TextView setNumber;
        protected TextView reps;
        protected TextView weight;
        protected TextView weightUnit;
        protected TextView time;
    }

    public class SetItemViewHolder extends RecyclerView.ViewHolder {
        protected TextView setNumber;
        protected TextView reps;
        protected TextView weight;
        protected TextView weightUnit;
        protected TextView time;

        public SetItemViewHolder(View v) {
            super(v);
            setNumber = (TextView) v.findViewById(R.id.set_number_text_view);
            reps = (TextView) v.findViewById(R.id.reps_text_view);
            weight = (TextView) v.findViewById(R.id.weight_text_view);
            weightUnit = (TextView) v.findViewById(R.id.weight_unit_name_text_view);
            time = (TextView) v.findViewById(R.id.set_time_stamp_text_view);
        }
    }*/
}
