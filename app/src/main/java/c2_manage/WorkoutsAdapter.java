package c2_manage;

import android.content.Context;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jzohndev.no_bullshit_weightlifting_new.R;

import org.joda.time.format.DateTimeFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import data.Icons;
import database.DatabaseHelper;
import database.Exercise;
import database.Workout;

import static org.joda.time.format.DateTimeFormat.forPattern;

/**
 * Created by big1 on 7/23/2016.
 */
public class WorkoutsAdapter extends RecyclerView.Adapter<WorkoutsAdapter.WorkoutViewHolder> {

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

    public WorkoutsAdapter(Context context) {
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

    @Override
    public WorkoutViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.workout_card, parent, false);
        return new WorkoutViewHolder(v);
    }

    @Override
    public void onBindViewHolder(WorkoutViewHolder holder, int position) {
        final Workout currentWorkout = mWorkouts.get(position);
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

        // 4. Date
        DateTimeFormatter formatter = forPattern("yyyy-MM-dd HH:mm:ss");
        String createdDate = formatter.print(currentWorkout.getCreatedDate());
        holder.vCreatedDate.setText(createdDate);
    }

    @Override
    public int getItemCount() {
        return mWorkouts.size();
    }

    // Class
    public class WorkoutViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected ImageView dot1, dot2, dot3, dot4;
        protected TextView vName;
        protected TextView vExercises;
        protected TextView vCreatedDate;
        protected Workout workout;
        protected ImageView overflowMenu;

        public WorkoutViewHolder(View v) {
            super(v);
            dot1 = (ImageView) v.findViewById(R.id.dot_exercise_1);
            dot2 = (ImageView) v.findViewById(R.id.dot_exercise_2);
            dot3 = (ImageView) v.findViewById(R.id.dot_exercise_3);
            dot4 = (ImageView) v.findViewById(R.id.dot_exercise_4);

            vName = (TextView) v.findViewById(R.id.workout_name_text_view);
            vExercises = (TextView) v.findViewById(R.id.exercises_number_textv);
            vCreatedDate = (TextView) v.findViewById(R.id.date_textv);

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
}
