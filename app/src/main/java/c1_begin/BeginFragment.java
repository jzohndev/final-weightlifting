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
import com.mikhaellopez.hfrecyclerview.HFRecyclerView;

import c0_all.UpdateFabListener;
import data.Icons;
import database.DatabaseHelper;
import database.Exercise;

public class BeginFragment extends Fragment{
    private RelativeLayout mRelativeLayout;
    private ScheduleHelper mScheduleHelper;
    private UpdateFabListener mListener;

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
        final View mView = inflater.inflate(R.layout.layout_begin_no_exercise, container, false);
        mRelativeLayout = (RelativeLayout) mView.findViewById(R.id.relativelayout);

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
        final View defaultView = inflater.inflate(R.layout.view_no_workout_today, null);

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
    public class BeginScheduleAdapter extends HFRecyclerView<Exercise> {

        BeginScheduleAdapter() {
            super(mScheduleHelper.getWorkoutExercises(), true, true);
        }

        @Override
        public int getItemCount() {
            // header and footer = + 2
            return mScheduleHelper.getSessionExercises().size() + 2;
        }

        @Override
        protected RecyclerView.ViewHolder getItemView(LayoutInflater inflater, ViewGroup parent) {
            return new ExerciseViewHolder(inflater.inflate(R.layout.item_exercise_card, parent, false));
        }

        @Override
        protected RecyclerView.ViewHolder getHeaderView(LayoutInflater inflater, ViewGroup parent) {
            if (mScheduleHelper.getSessionWorkout().getStartTime() == null){
                return new HeaderViewHolder(inflater.inflate(R.layout.view_begin_header, parent, false));
            } else {
                return new HeaderViewHolder(inflater.inflate(R.layout.view_begin_no_header, parent, false));
            }
        }

        @Override
        protected RecyclerView.ViewHolder getFooterView(LayoutInflater inflater, ViewGroup parent) {
            return new FooterViewHolder(inflater.inflate(R.layout.view_footer_space, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int i) {

            if (holder instanceof ExerciseViewHolder) {
                final int position = (i - 1);
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
            }
            /* else if (holder instanceof FooterViewHolder){

            } else if (holder instanceof HeaderViewHolder){

            }*/



        }
    }

    // View Holder
    public class ExerciseViewHolder extends RecyclerView.ViewHolder {
        protected ImageView icon;
        protected TextView name;
        protected TextView completedSets;
        protected TextView totalSets;

        ExerciseViewHolder(View v) {
            super(v);
            icon = (ImageView) v.findViewById(R.id.exercise_icon_imageview);
            name = (TextView) v.findViewById(R.id.exercise_name_textview);
            completedSets = (TextView) v.findViewById(R.id.completed_sets_textview);
            totalSets = (TextView) v.findViewById(R.id.total_sets_text_view);
        }
    }

    protected class HeaderViewHolder extends RecyclerView.ViewHolder {

        HeaderViewHolder(View v) {
            super(v);
        }
    }

    protected class FooterViewHolder extends RecyclerView.ViewHolder {

        FooterViewHolder(View v) {
            super(v);
        }
    }
}
