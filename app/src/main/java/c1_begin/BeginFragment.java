package c1_begin;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jzohndev.no_bullshit_weightlifting_new.R;

import data.Icons;
import database.DatabaseHelper;
import database.Exercise;

public class BeginFragment extends Fragment {
    private RelativeLayout mRelativeLayout;
    private ScheduleHelper mScheduleHelper;

    // updates data set in case a workout was added/removed since last viewing
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.e("BeginFragment", "setUserVisibileHint");
        if (isVisibleToUser) {
            resolveSchedule();
            if (mScheduleHelper.checkWorkoutIsComplete()) {
                DialogFragment workoutCompleteDialog = new WorkoutCompleteDialogFragment();
                workoutCompleteDialog.show(getFragmentManager(), "workoutComplete");
            }
        }
    }

    private void resolveSchedule() {
        final DatabaseHelper db = new DatabaseHelper(getContext());
        mScheduleHelper = ScheduleHelper.getInstance();
        mScheduleHelper.initScheduleData(db);
        // if schedule exists
        if (mScheduleHelper.getSchedule().getSessionId() != -1) {
            mScheduleHelper.initSessionData(db);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e("BeginFragment", "onCreateView");
        final View mView = inflater.inflate(R.layout.fragment_begin, container, false);
        mRelativeLayout = (RelativeLayout) mView.findViewById(R.id.relative_layout);

        if (mScheduleHelper == null) {
            resolveSchedule();
        }

        if (mScheduleHelper.getSchedule().getSessionId() == -1) {
            createNoWorkoutTodayView();
        } else {
            createBeginWorkoutListView();
        }
        return mView;
    }

    private void createNoWorkoutTodayView() {
        final LayoutInflater inflater = LayoutInflater.from(getContext());
        final View defaultView = inflater.inflate(R.layout.no_workout_today, null);

        mRelativeLayout.removeAllViews();
        mRelativeLayout.addView(defaultView);
    }

    private void createBeginWorkoutListView() {
        final LayoutInflater inflater = LayoutInflater.from(getContext());
        final View beginWorkoutLayout = inflater.inflate(R.layout.layout_begin, null);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        final BeginScheduleAdapter adapter = new BeginScheduleAdapter();
        final RecyclerView recyclerView = (RecyclerView) beginWorkoutLayout.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        mRelativeLayout.removeAllViews();
        mRelativeLayout.addView(beginWorkoutLayout);
    }

    /*private void initWorkoutIsCompleteView(){
        final IntentResolver resolver = IntentResolver.getInstance();
        final DatabaseHelper db = new DatabaseHelper(getContext());
        SessionWorkout tempSessionWorkout = db.getSessionWorkout(LoadDates.getTodayDate());

        if (tempSessionWorkout.getEndTime() == null){
            for (int i = 0; i < mHelper.getExercisesSize(); i++){
                List<SessionExercise> tempSession = mHelper
                        .getExerciseSession(mHelper.getExerciseByPosition(i).getId());
                for (SessionExercise session : tempSession){
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
    }*/

    // Adapter
    public class BeginScheduleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        /*
        private final int EXERCISE_NOT_STARTED = 0;
        private final int EXERCISE_IN_PROGRESS = 1;
        private final int EXERCISE_COMPLETE = 2;*/

        BeginScheduleAdapter() {

            /*
            if (mHelper.getSessionWorkout() == null){
                initializeDataSet();
            }*/
        }

        @Override
        public int getItemCount() {
            return mScheduleHelper.getSessionExercises().size();
        }

        @Override
        public int getItemViewType(int position) {
            return 0;

            /*
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
            return -1;*/
        }
/*
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
        }*/

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exercise_card, parent, false);
            return new ExerciseViewHolder(v);



            /*
            switch (viewType){
                case (EXERCISE_NOT_STARTED):
                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exercise_card, parent, false);
                    return new ExerciseViewHolder(v);
                case (EXERCISE_IN_PROGRESS):
                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exercise_card, parent, false);
                    return new ExerciseViewHolder(v);
                case(EXERCISE_COMPLETE):
                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exercise_complete_card, parent, false);
                    return new ExerciseCompleteViewHolder(v);
            }*/
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int i) {
            // final SessionExercise sessionExercise = mScheduleHelper.getSessionExercises().get(position);
            final int position = i;
            final Exercise exercise = mScheduleHelper.getWorkoutExercises().get(position);
            final ExerciseViewHolder vHolder = (ExerciseViewHolder) holder;
            vHolder.icon.setImageResource(Icons.getMuscleGroupIcon(exercise.getMuscleGroup()));
            vHolder.name.setText(exercise.getName());
            final int completedSets = mScheduleHelper.getExerciseCompletedSets(exercise.getId());
            final int totalSets = mScheduleHelper.getExerciseTotalSets(position);
            vHolder.completedSets.setText(String.valueOf(completedSets));
            vHolder.totalSets.setText(String.valueOf(totalSets));
            vHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getActivity(), BeginExerciseSelected.class);
                    Bundle b = new Bundle();
                    b.putInt("exerciseId", exercise.getId());
                    i.putExtras(b);
                    mScheduleHelper.startSessionWorkout(getContext());
                    startActivity(i);
                }
            });

           /* try {
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
            }*/
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
            icon = (ImageView) v.findViewById(R.id.exercise_icon_image_view);
            name = (TextView) v.findViewById(R.id.exercise_name_text_view);
            completedSets = (TextView) v.findViewById(R.id.completed_sets_text_view);
            totalSets = (TextView) v.findViewById(R.id.total_sets_text_view);
        }
    }

    // View Holder
    /*public class ExerciseCompleteViewHolder extends RecyclerView.ViewHolder {
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
    }*/
}
