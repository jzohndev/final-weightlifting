package c1_begin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.jzohndev.no_bullshit_weightlifting_new.R;

import org.joda.time.DateTime;

import java.util.List;

import c0_all.MenuPager;
import data.Icons;
import data.IntentResolver;
import database.DatabaseHelper;
import database.Exercise;
import database.SessionExercise;
import database.SessionSet;

/**
 * Created by big1 on 8/3/2016.
 */
public class BeginExerciseSelected extends Activity {

    private BeginSetAdapter mAdapter;
    private ListView mListView;
    private Spinner spnReps, spnWeight;
    private ImageView exerciseIcon, acceptButton;
    private TextView exerciseNameTextView, setNumberTextView;
    private int currentExerciseSet = 1;
    private DatabaseHelper db;
    private ScheduleHelper scheduleHelper;
    private int exerciseId;
    private Exercise exercise;
    private SessionExercise sessionExercise;
    private List<SessionSet> sessionSets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_begin_exercise_selected);

        db = new DatabaseHelper(getApplicationContext());

        scheduleHelper = ScheduleHelper.getInstance();
        exerciseId = getIntent().getExtras().getInt("exerciseId");
        exercise = db.getExercise(exerciseId);
        sessionExercise = scheduleHelper.getSessionExercise(exerciseId);
        sessionSets = scheduleHelper.getSessionSets(exerciseId);

        final int completedExerciseSets = scheduleHelper.getExerciseCompletedSets(exerciseId);
        if (completedExerciseSets == sessionExercise.getNumberOfSets()) {
            currentExerciseSet = completedExerciseSets;
        } else {
            currentExerciseSet = (completedExerciseSets + 1);
        }

        initLayout();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    private void initLayout() {
        final View addSetFooter = getLayoutInflater().inflate(R.layout.item_add_set, null);
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
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // TODO updateCurrentSetView();
            }
        });

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
                completeExercise();
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
        exerciseIcon = (ImageView) findViewById(R.id.exercise_icon_image_view);
        exerciseNameTextView = (TextView) findViewById(R.id.exercise_name_text_view);

        exerciseIcon.setImageResource(Icons.getMuscleGroupIcon(exercise.getMuscleGroup()));
        exerciseNameTextView.setText(exercise.getName());
    }

    private void createWorkingSetControlBox() {
        setNumberTextView = (TextView) findViewById(R.id.set_number_text_view);
        //acceptButton = (ImageView) findViewById(R.id.accept_imagev);
        final ArrayAdapter<CharSequence> repsAdapter =
                ArrayAdapter.createFromResource(this, R.array.reps_array,
                        R.layout.text_view_begin_selected);
        final ArrayAdapter<CharSequence> weightAdapter =
                ArrayAdapter.createFromResource(this, R.array.weight_array,
                        R.layout.text_view_begin_selected);


        setNumberTextView.setText(String.valueOf(currentExerciseSet));
      /*  acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                completeSet();
            }
        });*/
        repsAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        weightAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        //spnReps = (Spinner) findViewById(R.id.reps_spinner);
        //spnReps.setAdapter(repsAdapter);
        //spnReps.setSelection(0);

        //spnWeight = (Spinner) findViewById(R.id.weight_spinner);
        //spnWeight.setAdapter(weightAdapter);
        //spnWeight.setSelection(0);

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    private void completeSet() {
        // create SessionSet with completed set data
        SessionSet completedSessionSet = sessionSets.get(currentExerciseSet - 1);
        // completedSessionSet.setNote(); TODO
        completedSessionSet.setReps(Integer.valueOf((String) spnReps.getSelectedItem()));
        completedSessionSet.setWeight(Integer.valueOf((String) spnWeight.getSelectedItem()));
        completedSessionSet.setEndTime(DateTime.now());

        // update the session sets for the exercise to reflect
        // the completed set and reassign it to be used
        scheduleHelper.updateSessionSet(exerciseId, (currentExerciseSet - 1), completedSessionSet);
        sessionSets = scheduleHelper.getSessionSets(exerciseId);

        // update the session set in the database
        new DatabaseHelper(getApplicationContext()).updateSessionSet(
                scheduleHelper.getSchedule().getSessionId(), completedSessionSet);

        currentExerciseSet++;
        // determine if
        if ((currentExerciseSet - 1) == sessionExercise.getNumberOfSets()) {
            // exercise is complete
            sessionExercise.setEndTime(DateTime.now());
            new DatabaseHelper(getApplicationContext()).updateSessionExercise(sessionExercise);
            scheduleHelper.updateSessionExercisesDataSet(getApplicationContext());
            completeExercise();
        }

        updateCurrentSetView();
        mAdapter.notifyDataSetChanged(); // TODO last done 12/15/2016
    }

    private void updateCurrentSetView() {


        // exercise not complete
        spnReps.setSelection(0);
        spnWeight.setSelection(0);
        setNumberTextView.setText(String.valueOf(currentExerciseSet));

    }

    private void completeExercise() {

        IntentResolver resolver = IntentResolver.getInstance();
        resolver.setIntent("BeginExerciseSelected", "BeginFragment", -1);
        startActivity(new Intent(BeginExerciseSelected.this, MenuPager.class));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public class BeginSetAdapter extends BaseAdapter {
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

            if (currentSet.getEndTime() != null) { // set completed
                convertView = getLayoutInflater().inflate(R.layout.item_begin_exercise_set_complete, null);
                CompletedSetViewHolder cHolder = new CompletedSetViewHolder();
                cHolder.setNumber = (TextView) convertView.findViewById(R.id.set_number_text_view);
                cHolder.reps = (TextView) convertView.findViewById(R.id.reps_number_text_view);
                cHolder.weight = (TextView) convertView.findViewById(R.id.weight_number_text_view);
                cHolder.setNumber.setText(String.valueOf(currentSet.getSetNumber()));
                cHolder.reps.setText(String.valueOf(currentSet.getReps()));
                cHolder.weight.setText(String.valueOf(currentSet.getWeight()));
                convertView.setTag(cHolder);
            } else { // set incomplete or not started
                convertView = getLayoutInflater().inflate(R.layout.item_begin_exercise_set_incomplete, null);
                IncompleteSetViewHolder iHolder = new IncompleteSetViewHolder();
                iHolder.setNumber = (TextView) convertView.findViewById(R.id.set_number_text_view);
                iHolder.reps = (TextView) convertView.findViewById(R.id.reps_number_text_view);
                iHolder.setNumber.setText(String.valueOf(currentSet.getSetNumber()));
                iHolder.reps.setText(String.valueOf(sessionExercise.getDefaultReps()));
                convertView.setTag(iHolder); // TODO THE LAST THING I DID 11/28/2016
            }
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
