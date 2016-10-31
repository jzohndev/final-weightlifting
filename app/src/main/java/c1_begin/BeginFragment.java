package c1_begin;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jzohndev.no_bullshit_weightlifting_new.R;

import org.joda.time.LocalDate;

import java.util.Calendar;
import java.util.List;

import c0_all.MenuPager;
import data.Icons;
import data.IntentResolver;
import data.LoadDates;
import database.DatabaseHelper;
import database.Exercise;
import database.Schedule;
import database.SessionWorkout;
import database.SessionWorkoutExercise;

public class BeginFragment extends Fragment {
    private final int NO_WORKOUT_SCHEDULED = 0;
    private final int WORKOUT_SCHEDULED = 1;
    private final int WORKOUT_COMPLETE = 2;

    private RelativeLayout mRelativeLayout;
    private BeginWorkoutHelper mHelper;
    private int mWorkoutScheduleStatus;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View mView = inflater.inflate(R.layout.fragment_begin, container, false);
        mRelativeLayout = (RelativeLayout) mView.findViewById(R.id.relative_layout);

        resolveIfWorkoutIsScheduled();

        switch (mWorkoutScheduleStatus) {
            case (WORKOUT_SCHEDULED):
                initBeginWorkoutView();
                break;

            case (NO_WORKOUT_SCHEDULED):
                initNoWorkoutView();
                break;

            case (WORKOUT_COMPLETE):
                initWorkoutIsCompleteView();
                break;
        }
        return mView;
    }

    private void resolveIfWorkoutIsScheduled(){
        final DatabaseHelper db = new DatabaseHelper(getContext());
        final Schedule todaySchedule = db.getFullSchedule(LocalDate.now());
        long workoutId = todaySchedule.getWorkoutId();

        if (workoutId == -1) {
            mWorkoutScheduleStatus = NO_WORKOUT_SCHEDULED;
        } else {
            mWorkoutScheduleStatus = WORKOUT_SCHEDULED;

            mHelper = BeginWorkoutHelper.getInstance();
            mHelper.setWorkoutId(workoutId);

            final long sessionId = db.getSessionWorkoutId(LoadDates.getTodayDate());
            if (sessionId == -1){
                final Calendar calendar = Calendar.getInstance();
                 mHelper.setSessionId(db.createSessionWorkout(workoutId,
                        LoadDates.getTodayDate(),
                        calendar.getTime()));
            } else {
                mHelper.setExercises(db.getWorkoutExercises(mHelper.getWorkoutId()));
                if (mHelper.isDone()){
                    mWorkoutScheduleStatus = WORKOUT_COMPLETE;
                } else {
                    mHelper.setSessionId(sessionId);
                }
            }
        }
    }

    private void initNoWorkoutView(){
        final LayoutInflater inflater = LayoutInflater.from(getContext());
        final View defaultView = inflater.inflate(R.layout.no_workout_today, null);

        mRelativeLayout.removeAllViews();
        mRelativeLayout.addView(defaultView);
    }

    private void initBeginWorkoutView(){
        final LayoutInflater inflater = LayoutInflater.from(getContext());
        final View workoutView = inflater.inflate(R.layout.begin_schedule, null);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        final BeginScheduleAdapter adapter = new BeginScheduleAdapter();
        final RecyclerView recyclerView = (RecyclerView) workoutView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        mRelativeLayout.removeAllViews();
        mRelativeLayout.addView(workoutView);
    }

    private void initWorkoutIsCompleteView(){
        final IntentResolver resolver = IntentResolver.getInstance();
        final DatabaseHelper db = new DatabaseHelper(getContext());
        SessionWorkout tempSessionWorkout = db.getSessionWorkout(LoadDates.getTodayDate());

        if (tempSessionWorkout.getEndTime() == null){
            for (int i = 0; i < mHelper.getExercisesSize(); i++){
                List<SessionWorkoutExercise> tempSession = mHelper
                        .getExerciseSession(mHelper.getExerciseByPosition(i).getId());
                for (SessionWorkoutExercise session : tempSession){
                    db.createSessionWorkoutExercise(session);
                }
            }


            final Calendar calendar = Calendar.getInstance();
            db.updateSessionWorkout(LoadDates.getTodayDate(), calendar.getTime());
            resolver.setIntent("BeginFragment", "BeginFragment", 8);
            startActivity(new Intent(getActivity(), MenuPager.class));
            // TODO goto history
        }
        // TODO dialog
    }

    // Adapter
    public class BeginScheduleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        private final int EXERCISE_NOT_STARTED = 0;
        private final int EXERCISE_IN_PROGRESS = 1;
        private final int EXERCISE_COMPLETE = 2;

        public BeginScheduleAdapter(){
            if (mHelper.getSessionWorkout() == null){
                initializeDataSet();
            }
        }

        private void initializeDataSet(){
            // Set session workout
            mHelper.setSessionWorkout(new SessionWorkout(mHelper.getSessionId(),
                    LoadDates.getTodayDate(),mHelper.getWorkoutId()));

            final DatabaseHelper db = new DatabaseHelper(getContext());
            mHelper.setExercises(db.getWorkoutExercises(mHelper.getWorkoutId()));
        }

        private void exerciseSelected(Exercise selectedExercise){
            mHelper.setSelectedExercise(selectedExercise);
            IntentResolver resolver = IntentResolver.getInstance();
            resolver.setIntent("BeginFragment", "BeginFragment", -1);
            startActivity(new Intent(getActivity(), BeginExerciseSelected.class));
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v;
            switch (viewType){
                case (EXERCISE_NOT_STARTED):
                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.begin_exercise_card, parent, false);
                    return new ExerciseViewHolder(v);
                case (EXERCISE_IN_PROGRESS):
                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.begin_exercise_card, parent, false);
                    return new ExerciseViewHolder(v);
                case(EXERCISE_COMPLETE):
                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.begin_exercise_complete_card, parent, false);
                    return new ExerciseCompleteViewHolder(v);
            }
            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            final Exercise currentExercise = mHelper.getExerciseByPosition(position);

            try {
                if (holder instanceof ExerciseViewHolder){
                    ExerciseViewHolder eholder = (ExerciseViewHolder) holder;
                    eholder.icon.setImageResource
                            (Icons.getMuscleGroupIcon(currentExercise.getMuscleGroup()));
                    eholder.name.setText(currentExercise.getName());
                    eholder.completedSets.setText(String.valueOf(
                            mHelper.getExerciseSessionSize(currentExercise.getId())));
                    eholder.totalSets.setText(String.valueOf(currentExercise.getDefaultSets()));

                    eholder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            exerciseSelected(currentExercise);
                        }
                    });
                } else if (holder instanceof ExerciseCompleteViewHolder){
                    ExerciseCompleteViewHolder eholder = (ExerciseCompleteViewHolder) holder;
                    eholder.icon.setImageResource
                            (Icons.getMuscleGroupIcon(currentExercise.getMuscleGroup()));
                    eholder.name.setText(currentExercise.getName());
                    eholder.completedSets.setText(String.valueOf(currentExercise.getDefaultSets()));
                    eholder.totalSets.setText(String.valueOf(currentExercise.getDefaultSets()));

                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        public int getItemCount() {
            return mHelper.getExercisesSize();
        }

        @Override
        public int getItemViewType(int position) {
            final Exercise currentExercise = mHelper.getExerciseByPosition(position);
            final int completedSets = mHelper.getExerciseSessionSize(currentExercise.getId());
            final int totalSets = currentExercise.getDefaultSets();

            if (completedSets == 0){
                return EXERCISE_NOT_STARTED;
            } else if (completedSets < totalSets){
                return EXERCISE_IN_PROGRESS;
            } else if (completedSets == totalSets) {
                return EXERCISE_COMPLETE;
            }
            return -1;
        }
    }

    // View Holder
    public class ExerciseViewHolder extends RecyclerView.ViewHolder {
        protected ImageView icon;
        protected TextView name;
        protected TextView completedSets;
        protected TextView totalSets;

        public ExerciseViewHolder(View v) {
            super(v);
            icon = (ImageView) v.findViewById(R.id.icon_image_view);
            name = (TextView) v.findViewById(R.id.exercise_name_text_view);
            completedSets = (TextView) v.findViewById(R.id.completed_sets_text_view);
            totalSets = (TextView) v.findViewById(R.id.total_sets_text_view);
        }
    }

    // View Holder
    public class ExerciseCompleteViewHolder extends RecyclerView.ViewHolder {
        protected ImageView icon;
        protected TextView name;
        protected TextView completedSets;
        protected TextView totalSets;

        public ExerciseCompleteViewHolder(View v) {
            super(v);
            icon = (ImageView) v.findViewById(R.id.icon_image_view);
            name = (TextView) v.findViewById(R.id.exercise_name_text_view);
            completedSets = (TextView) v.findViewById(R.id.completed_sets_text_view);
            totalSets = (TextView) v.findViewById(R.id.total_sets_text_view);
        }
    }
}