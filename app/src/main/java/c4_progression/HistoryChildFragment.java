package c4_progression;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jzohndev.no_bullshit_weightlifting_new.R;

import java.util.List;

import data.IntentResolver;
import data.LoadDates;
import database.DatabaseHelper;
import database.SessionWorkout;
import database.Workout;

public class HistoryChildFragment extends Fragment {
    private View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_history_child, container, false);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        final HistoryFragmentAdapter adapter = new HistoryFragmentAdapter();

        final RecyclerView recyclerView = (RecyclerView) mView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return mView;
    }

    // Adapter
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public class HistoryFragmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private List<SessionWorkout> completedSessionWorkouts;
        private DatabaseHelper db;

        public HistoryFragmentAdapter(){
            db = new DatabaseHelper(getContext());
            completedSessionWorkouts = db.getCompletedSessionWorkouts();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View v = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.history_item_card, parent, false);
            return new HistoryViewHolder(v);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            final SessionWorkout currentSessionWorkout = completedSessionWorkouts.get(position);
            final Workout currentWorkout = db.getWorkout
                    (currentSessionWorkout.getWorkoutId());
            final int numberOfExercises = db.getWorkoutExerciseNumberOf
                    (currentSessionWorkout.getWorkoutId());
            HistoryViewHolder eHolder = (HistoryViewHolder) holder;
            eHolder.workoutName.setText(currentWorkout.getName());
            //eHolder.date.setText(LoadDates.dateToString(currentSessionWorkout.getDate()));
            eHolder.exercises.setText(String.valueOf(numberOfExercises));
            eHolder.startTime.setText(LoadDates.dateTimeToString(currentSessionWorkout.getStartTime()));
            eHolder.endTime.setText(LoadDates.dateTimeToString(currentSessionWorkout.getEndTime()));

            eHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IntentResolver resolver = IntentResolver.getInstance();
                    resolver.setIntent("HistoryChildFragment", "HistoryChildFragment", -1, currentSessionWorkout);
                    startActivity(new Intent(getActivity(), HistorySelected.class));
                }
            });
        }

        @Override
        public int getItemCount() {
            return completedSessionWorkouts.size();
        }
    }

    //ViewHolder
    public class HistoryViewHolder extends RecyclerView.ViewHolder{
        protected TextView workoutName;
        protected TextView date;
        protected TextView exercises;
        protected TextView startTime;
        protected TextView endTime;

        public HistoryViewHolder(View v) {
            super(v);
            workoutName = (TextView) v.findViewById(R.id.workout_name_text_view);
            date = (TextView) v.findViewById(R.id.date_text_view);
            exercises = (TextView) v.findViewById(R.id.exercise_number_text_view);
            startTime = (TextView) v.findViewById(R.id.start_time_text_view);
            endTime = (TextView) v.findViewById(R.id.end_time_text_view);
        }
    }

}
