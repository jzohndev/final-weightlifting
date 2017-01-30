package c3_schedule;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jzohndev.no_bullshit_weightlifting_new.R;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

import data.IntentResolver;
import data.LoadDates;
import database.DatabaseHelper;
import database.ScheduledSession;
import database.Workout;
import workout.WorkoutList;

import static org.joda.time.format.DateTimeFormat.forPattern;

public class WeekChildFragment extends Fragment {

    public WeekChildFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View mView = inflater.inflate(R.layout.fragment_week_child, container, false);

        final View header = createWeekOfHeader(inflater);
        final View footer = inflater.inflate(R.layout.view_footer_space, null);

        final WeekWorkoutsAdapter adapter = new WeekWorkoutsAdapter();
        final ListView listView = (ListView) mView.findViewById(R.id.list_view);
        listView.addHeaderView(header);
        listView.addFooterView(footer);
        listView.setAdapter(adapter);

        return mView;
    }

    private View createWeekOfHeader(LayoutInflater inflater) {
        final View weekOfHeader = inflater.inflate(R.layout.view_week_week_of_date_textview, null);
        final TextView weekOfTextView = (TextView) weekOfHeader.findViewById(R.id.week_of_text_view);

        LocalDate firstDayOfWeek = LocalDate.now().withDayOfWeek(1);
        LocalDate lastDayOfWeek = LocalDate.now().withDayOfWeek(7);
        DateTimeFormatter firstDayFormatter = forPattern("MMMM d");
        DateTimeFormatter lastDayFormatter = forPattern("d");

        String weekOfString;
        // if ending day of week falls in the proceeding month
        if (lastDayOfWeek.getMonthOfYear() != firstDayOfWeek.getMonthOfYear()) {
            weekOfString = firstDayFormatter.print(firstDayOfWeek)
                    + " - " + firstDayFormatter.print(lastDayOfWeek);
        }
        // if beginning/ending day of week fall in the same month
        else {
            weekOfString = firstDayFormatter.print(firstDayOfWeek)
                    + " - " + lastDayFormatter.print(lastDayOfWeek);
        }
        weekOfTextView.setText(weekOfString);

        return weekOfHeader;
    }

    // Class
    public class WeekWorkoutsAdapter extends BaseAdapter {
        private final int WORKOUT = 0;
        private final int NO_WORKOUT = 1;

        private String[] mDaysOfWeek = {"MON", "TUE", "WED", "THUR", "FRI", "SAT", "SUN"};
        private List<Workout> mWeekWorkouts;
        private List<ScheduledSession> mWeekScheduledSessions;
        private List<LocalDate> mWeekDates;

        public WeekWorkoutsAdapter() {
            mWeekScheduledSessions = new ArrayList<>();
            mWeekWorkouts = new ArrayList<>();
            updateDataSet();
        }

        private void updateDataSet() {
            final DatabaseHelper db = new DatabaseHelper(getContext());
            mWeekDates = LoadDates.getWeekDates();
            mWeekWorkouts.removeAll(mWeekWorkouts);

            for (LocalDate currentDate : mWeekDates) {
                final ScheduledSession currentSchedule = db.getScheduledSession(currentDate);
                int workoutId = currentSchedule.getWorkoutId();
                mWeekScheduledSessions.add(currentSchedule);
                mWeekWorkouts.add(db.getWorkout(workoutId));
            }
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mWeekWorkouts.size();
        }

        @Override
        public Object getItem(int position) {
            return mWeekWorkouts.get(position);
        }

        @Override
        public long getItemId(int position) {
            return mWeekWorkouts.get(position).getId();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            final int pos = position;

            switch (getItemViewType(position)) {
                case (WORKOUT):
                    WorkoutViewHolder workoutViewHolder;

                    if (convertView == null || convertView.getTag() instanceof NoWorkoutViewHolder) {
                        LayoutInflater inflater = (getActivity()).getLayoutInflater();
                        convertView = inflater.inflate(R.layout.item_week_card_workout, parent, false);

                        workoutViewHolder = new WorkoutViewHolder();
                        workoutViewHolder.day = (TextView) convertView.findViewById(R.id.day_of_week_text_view);
                        workoutViewHolder.name = (TextView) convertView.findViewById(R.id.workout_name_textview);
                        workoutViewHolder.remove = (ImageView) convertView.findViewById(R.id.remove_image_view);

                        convertView.setTag(workoutViewHolder);
                    } else {
                        workoutViewHolder = (WorkoutViewHolder) convertView.getTag();
                    }

                    Workout currentWorkout = mWeekWorkouts.get(position);

                    workoutViewHolder.day.setText(mDaysOfWeek[pos]);
                    workoutViewHolder.name.setText(currentWorkout.getName());
                    workoutViewHolder.remove.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new DatabaseHelper(getContext())
                                    .deleteScheduledSession(mWeekScheduledSessions.get(pos).getSessionId());
                            updateDataSet();
                        }
                    });
                    break;
                case (NO_WORKOUT):
                    NoWorkoutViewHolder noWorkoutViewHolder;

                    if (convertView == null || convertView.getTag() instanceof WorkoutViewHolder) {
                        LayoutInflater inflater = (getActivity()).getLayoutInflater();
                        convertView = inflater.inflate(R.layout.item_week_card_no_workout, parent, false);

                        noWorkoutViewHolder = new NoWorkoutViewHolder();
                        noWorkoutViewHolder.day = (TextView) convertView.findViewById(R.id.day_of_week_text_view);
                        noWorkoutViewHolder.layout = (RelativeLayout) convertView.findViewById(R.id.relativelayout);

                        convertView.setTag(noWorkoutViewHolder);
                    } else {
                        noWorkoutViewHolder = (NoWorkoutViewHolder) convertView.getTag();
                    }

                    noWorkoutViewHolder.day.setText(mDaysOfWeek[pos]);
                    noWorkoutViewHolder.layout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            IntentResolver resolver = IntentResolver.getInstance();
                            resolver.setIntent("WeekChildFragment", "WeekChildFragment", -1, mWeekDates.get(pos));
                            startActivity(new Intent(getActivity(), WorkoutList.class));
                        }
                    });
                    break;
            }

            return convertView;
        }

        @Override
        public int getItemViewType(int position) {
            if (mWeekWorkouts.get(position).getId() == -1) {
                return NO_WORKOUT;
            }
            return WORKOUT;
        }
    }

    // Class
    public class WorkoutViewHolder {
        protected TextView day;
        protected TextView name;
        protected ImageView remove;
    }

    // Class
    public class NoWorkoutViewHolder {
        protected RelativeLayout layout;
        protected TextView day;
    }
}
