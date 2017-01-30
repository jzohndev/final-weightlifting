package c2_manage;

import android.content.Context;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jzohndev.no_bullshit_weightlifting_new.R;

import java.util.List;
import java.util.Map;

import data.Icons;
import database.DatabaseHelper;
import database.Exercise;
import database.Workout;

/**
 * Created by big1 on 7/23/2016.
 */
public class ListOfWorkoutsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<Workout> mWorkouts;
    private DatabaseHelper db;
    private Map<Integer, Integer> mNumberOfWorkoutExercises;
    private WorkoutOnItemClickListener listener;

    public interface WorkoutOnItemClickListener {
        void onItemClick(Workout workout, View v);
    }

    public void setOnItemClickListener(final WorkoutOnItemClickListener listener) {
        this.listener = listener;
    }

    public ListOfWorkoutsAdapter(Context context) {
        this.mContext = context;
        updateDataSet();

    }

    public void updateDataSet() {
        if (db == null) {
            db = new DatabaseHelper(mContext);
        }
        mWorkouts = db.getAllWorkouts();
        initializeNumberOfWorkoutExercises();
        notifyDataSetChanged();
    }

    private void initializeNumberOfWorkoutExercises() {
        mNumberOfWorkoutExercises = new ArrayMap<>();
        List<Integer> exerciseIds;
        for (Workout workout : mWorkouts) {
            exerciseIds = db.getWorkoutExerciseIds(workout.getId());
            mNumberOfWorkoutExercises.put(workout.getId(), exerciseIds.size());
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // + 1 for footer space
    @Override
    public int getItemCount() {
        return mWorkouts.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position >= mWorkouts.size()) {
            return 2;
        }
        return 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 1) {
            return new WorkoutViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_workout_card, parent, false));
        } else { // footer
            return new FooterViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.view_footer_space, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder theHolder, int position) {
        if (theHolder instanceof WorkoutViewHolder){
            final Workout currentWorkout = mWorkouts.get(position);
            WorkoutViewHolder holder = (WorkoutViewHolder) theHolder;
            holder.workout = currentWorkout;
            List<Exercise> currentExercises = db.getWorkoutExercises(currentWorkout.getId());

            // 1. Icon
            List<Integer> dotsIcons = Icons.getWorkoutDots(currentExercises);
            holder.dot1.setImageResource(dotsIcons.get(0));
            holder.dot2.setImageResource(dotsIcons.get(1));
            holder.dot3.setImageResource(dotsIcons.get(2));
            holder.dot4.setImageResource(dotsIcons.get(3));

            // 2. Name
            String name = currentWorkout.getName();
            holder.vName.setText(name);

            // 3. Number of Exercises
            String exercises = String.valueOf
                    (mNumberOfWorkoutExercises.get(currentWorkout.getId()));
            holder.vExercises.setText(exercises);
        }
    }

    // Class
    public class WorkoutViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected ImageView dot1, dot2, dot3, dot4;
        protected TextView vName;
        protected TextView vExercises;
        protected Workout workout;
        protected ImageView overflowMenu;

        public WorkoutViewHolder(View v) {
            super(v);
            dot1 = (ImageView) v.findViewById(R.id.dot_exercise_1);
            dot2 = (ImageView) v.findViewById(R.id.dot_exercise_2);
            dot3 = (ImageView) v.findViewById(R.id.dot_exercise_3);
            dot4 = (ImageView) v.findViewById(R.id.dot_exercise_4);

            vName = (TextView) v.findViewById(R.id.workout_name_textview);
            vExercises = (TextView) v.findViewById(R.id.exercises_number_textview);

            overflowMenu = (ImageView) v.findViewById(R.id.overflow_menu_image_view);
            overflowMenu.setTag("overflow");
            overflowMenu.setOnClickListener(this);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (listener != null) {
                listener.onItemClick(workout, view);
            }
        }
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View v) {
            super(v);
        }
    }
}
