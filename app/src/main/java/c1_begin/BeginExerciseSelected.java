package c1_begin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.jzohndev.no_bullshit_weightlifting_new.R;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import c0_all.MenuPager;
import data.IntentResolver;
import database.SessionExercise;
import database.SessionSet;

/**
 * Created by big1 on 8/3/2016.
 */
public class BeginExerciseSelected extends Activity {
    private static final int EXERCISE_NOT_STARTED = 0;
    private static final int EXERCISE_IN_PROGRESS = 1;
    private static final int EXERCISE_COMPLETED = 2;
    private int exerciseProgress;

    private int currentExerciseSet = 1;

    private BeginWorkoutHelper mHelper;
    private List<SessionExercise> mExerciseSession;
    private ListView mListView;




    private Spinner spnReps, spnWeight;
    private ImageView acceptButton;
    private TextView exerciseNameTextView, setNumberTextView;

    ///////
    private ScheduleHelper scheduleHelper;
    private int exercisePosition;
    private List<SessionSet> sessionSets;
    //
    private BeginSetAdapter mAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_begin_exercise_selected);

        scheduleHelper = ScheduleHelper.getInstance();
        exercisePosition = getIntent().getExtras().getInt("exerciseId");
        sessionSets = scheduleHelper.getSessionSets(exercisePosition);

        initLayout();


        /*mHelper = BeginWorkoutHelper.getInstance();
        getExerciseProgress();
        createUI();*/

        /*
        switch (exerciseProgress) {
            case (EXERCISE_NOT_STARTED):
                createUI();
                break;
            case (EXERCISE_IN_PROGRESS):
                createUI();
                break;
            case (EXERCISE_COMPLETED):
                createUI();
                break;
        }*/
    }

    /*private void getExerciseProgress() {
        final Exercise selectedExercise = mHelper.getSelectedExercise();
        final int setsCompleted = mHelper.getExerciseSessionSize(selectedExercise.getId());
        final int totalSets = selectedExercise.getDefaultSets();

        if (setsCompleted == 0) {
            exerciseProgress = EXERCISE_NOT_STARTED;
        } else if (setsCompleted == totalSets) {
            exerciseProgress = EXERCISE_COMPLETED;

        } else if (setsCompleted < totalSets) {
            exerciseProgress = EXERCISE_IN_PROGRESS;
            currentExerciseSet = setsCompleted + 1;
        }
    }*/

    ////////////////////////////////////////////////////////////////////////////////////////////////
    private void initLayout() {

        final View addSetFooter = getLayoutInflater().inflate(R.layout.begin_add_set, null);
        addSetFooter.setTag("endOfList");
        addSetFooter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // mAdapter.addSet(); TODO
            }
        });

        mAdapter = new BeginSetAdapter();
        mListView = (ListView) findViewById(R.id.list_view);
        mListView.addFooterView(addSetFooter);
        mListView.setAdapter(mAdapter);

        createActionBar();
        createTimerBar();
        createExerciseHeader();
        createWorkingSetControlBox();
    }

    private void createActionBar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.time_toolbar);
        final ImageView upButton = (ImageView) toolbar.findViewById(R.id.up_button_image_view);
        upButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishExercise();
            }
        });
        // Actionbar
        //Workout Timer
    }

    private void createTimerBar() {
        // Minimized Toolbar
        // Maximized Toolbar
    }

    private void createExerciseHeader() {
        exerciseNameTextView = (TextView) findViewById(R.id.exercise_name_text_view);
        exerciseNameTextView.setText(mHelper.getSelectedExercise().getName());
    }

    private void createWorkingSetControlBox() {
        setNumberTextView = (TextView) findViewById(R.id.set_number_text_view);

        acceptButton = (ImageView) findViewById(R.id.accept_imagev);
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                completeSet();
            }
        });

        ArrayAdapter<CharSequence> repsAdapter =
                ArrayAdapter.createFromResource(this, R.array.reps_array,
                        R.layout.text_view_begin_selected);
        repsAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> weightAdapter =
                ArrayAdapter.createFromResource(this, R.array.weight_array,
                        R.layout.text_view_begin_selected);
        weightAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        spnReps = (Spinner) findViewById(R.id.reps_spinner);
        spnReps.setAdapter(repsAdapter);
        spnReps.setSelection(0);

        spnWeight = (Spinner) findViewById(R.id.weight_spinner);
        spnWeight.setAdapter(weightAdapter);
        spnWeight.setSelection(0);

    }
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private void completeSet() {
        final Calendar calendar = Calendar.getInstance();

        long sessionId = mHelper.getSessionId();
        long exerciseId = mHelper.getSelectedExercise().getId();
        int repsCompleted = Integer.parseInt((String) spnReps.getSelectedItem());
        int weightUsed = Integer.parseInt((String) spnWeight.getSelectedItem());
        Date endTime = calendar.getTime();

        SessionExercise sessionExercise = new SessionExercise(
                sessionId, exerciseId, currentExerciseSet,
                repsCompleted, weightUsed, null, endTime);

        mHelper.addSessionExercise(exerciseId, sessionExercise);
        currentExerciseSet++;
        notifyViewsSetChanged();
        mAdapter.notifyDataSetChanged();
    }

    private void notifyViewsSetChanged() {
        if (mListView.getChildAt(currentExerciseSet - 1).getTag() == "endOfList") {
            finishExercise();
        } else {
            if (currentExerciseSet != 1) {
                List<SessionExercise> tempSessionExercise =
                        mHelper.getExerciseSession(mHelper.getSelectedExercise().getId());
                spnReps.setSelection(tempSessionExercise.
                        get(currentExerciseSet - 2).getRepsCompleted());
                spnWeight.setSelection(tempSessionExercise.
                        get(currentExerciseSet - 2).getWeightUsed());
            } else {
                spnReps.setSelection(0);
                spnWeight.setSelection(0);
            }
            setNumberTextView.setText(String.valueOf(currentExerciseSet));
        }
    }

    private void finishExercise() {
        IntentResolver resolver = IntentResolver.getInstance();
        resolver.setIntent("BeginExerciseSelected", "BeginFragment", -1);
        startActivity(new Intent(BeginExerciseSelected.this, MenuPager.class));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public class BeginSetAdapter extends BaseAdapter {

        /*public void addSet() {
            final long exerciseId = mHelper.getCurrentExercise().getId();
            final int defaultSets = (int) mHelper.getCurrentExercise().getDefaultSets();

            mHelper.getCurrentExercise().setDefaultSets(defaultSets + 1); // updates current exercise
            mExerciseSession.add(new SessionExercise(mHelper.getSessionId(),
                    mHelper.getWorkout().getId(),
                    defaultSets + 1)); // adds new session
            final DatabaseHelper db = new DatabaseHelper(getApplicationContext());
            db.updateExerciseSets(exerciseId, defaultSets + 1); // updates default sets in exercise list
            mHelper.updateExercise(exerciseId, defaultSets + 1); // updates the exercise in the singleton
            notifyDataSetChanged();
        }*/

       /* public void updateDataSet() {
            if (mExerciseSessions != null) {
                mExerciseSessions.clear();
            }
            for (int i = 0; i < mCurrentExercise.getDefaultSets(); i++) {
                mExerciseSessions.add(new SessionExercise(mSessionId, mWorkoutId, (i + 1), -1, -1));
            }
        }*/

        @Override
        public int getCount() {
            return sessionSets.size();
        }

        @Override
        public Object getItem(int i) {
            return sessionSets.get(i);
        }

        @Override
        public long getItemId(int i) {
            return sessionSets.get(i).getSetNumber();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            final SessionSet currentSet = (SessionSet) getItem(position);

            if (currentSet.getEndTime() != null){ // set completed
                convertView = getLayoutInflater().inflate(R.layout.begin_set_complete_item, null);
                CompletedSetViewHolder cHolder = new CompletedSetViewHolder();
                cHolder.setNumber = (TextView) convertView.findViewById(R.id.set_number_text_view);
                cHolder.reps = (TextView) convertView.findViewById(R.id.reps_number_text_view);
                cHolder.weight = (TextView) convertView.findViewById(R.id.weight_number_text_view);
                cHolder.setNumber.setText(currentSet.getSetNumber());
                cHolder.reps.setText(currentSet.getActualReps());
                cHolder.weight.setText(currentSet.getWeight());
                convertView.setTag(cHolder);
            } else { // set incomplete or not started
                convertView = getLayoutInflater().inflate(R.layout.begin_set_incomplete_item, null);
                IncompleteSetViewHolder iHolder = new IncompleteSetViewHolder();
                iHolder.setNumber = (TextView) convertView.findViewById(R.id.set_number_text_view);
                iHolder.reps = (TextView) convertView.findViewById(R.id.reps_number_text_view);
                iHolder.setNumber.setText(currentSet.getSetNumber());
                iHolder.reps.setText(currentSet.getDefaultReps());
                convertView.setTag(iHolder); // TODO THE LAST THING I DID 11/28/2016
            }
/*
            switch (setProgress) {
                case (SET_COMPLETE):
                    convertView = getLayoutInflater().inflate(R.layout.begin_set_complete_item, null);

                    List<SessionExercise> tempSessionExercise =
                            mHelper.getExerciseSession(mHelper.getSelectedExercise().getId());
                    SessionExercise tempSession = tempSessionExercise.get(position);

                    TextView setss = (TextView) convertView.findViewById(R.id.set_number_complete_textv);
                    setss.setText(String.valueOf(position + 1));
                    TextView reps = (TextView) convertView.findViewById(R.id.reps_number_text_view);
                    reps.setText(String.valueOf(tempSession.getRepsCompleted())); // TODO
                    TextView weightss = (TextView) convertView.findViewById(R.id.weight_number_text_view);
                    weightss.setText(String.valueOf(tempSession.getWeightUsed())); // TODO

                    break;
                case (SET_SELECTED):
                    convertView = getLayoutInflater().inflate(R.layout.begin_set_incomplete_item, null);

                    TextView set = (TextView) convertView.findViewById(R.id.set_text_view);
                    set.setTextAppearance(getApplicationContext(), R.style.GreenTextSubheading);
                    TextView sets = (TextView) convertView.findViewById(R.id.set_number_text_view);
                    sets.setText(String.valueOf(position + 1));
                    sets.setTextAppearance(getApplicationContext(), R.style.GreenTextSubheading);
                    TextView repss = (TextView) convertView.findViewById(R.id.reps_number_text_view);
                    repss.setText(String.valueOf(mHelper.getSelectedExercise().getDefaultReps()));
                    break;
                default:
                    convertView = getLayoutInflater().inflate(R.layout.begin_set_incomplete_item, null);

                    TextView setsss = (TextView) convertView.findViewById(R.id.set_number_text_view);
                    setsss.setText(String.valueOf(position + 1));
                    TextView repsss = (TextView) convertView.findViewById(R.id.reps_number_text_view);
                    repsss.setText(String.valueOf(mHelper.getSelectedExercise().getDefaultReps()));
                    break;
            }*/
            return convertView;
        }
    }

    static class CompletedSetViewHolder {
        protected TextView setNumber;
        protected TextView reps;
        protected TextView weight;
    }

    static class IncompleteSetViewHolder {
        protected TextView setNumber;
        protected TextView reps;
    }
}
