package c3_schedule;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jzohndev.no_bullshit_weightlifting_new.R;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import database.DatabaseHelper;
import database.ScheduledSession;
import database.SessionWorkout;

public class MonthChildFragment extends Fragment {
    private View mView;
    private DatabaseHelper db;

    private List<SessionWorkout> mSessionWorkouts;
    private Map<Date, Drawable> mWorkoutsDrawableMap;
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private List<ScheduledSession> mSchedules;
    private Map<Date, Drawable> mScheduleDrawableMap;

    private Map<Date, Drawable> mDayColors;


    private CaldroidFragment caldroid;

    private List<Date> mMonthDates;
    private Map<Date, Long> mMonthSchedules;
    private Map<Date, Drawable> mDayColor;

    public MonthChildFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_month_child, container, false);
        db = new DatabaseHelper(getActivity());

        getSessionWorkouts();
        initCaldroidCalendarWidget();
        assignSchedulesToCalendar();

        // Selected Date
        final TextView workoutName = (TextView) mView.findViewById(R.id.workout_name_textview);
        final CaldroidListener listener = new CaldroidListener() {
            @Override
            public void onSelectDate(Date input, View view) {
                LocalDate localDate = new LocalDate(input);
                ScheduledSession selected = db.getScheduledSession(localDate);

                if (selected.getWorkoutId() != -1) {
                    workoutName.setText(db.getWorkout(selected.getWorkoutId()).getName());
                } else {
                    workoutName.setText("No workout");
                }
            }
        };
        caldroid.setCaldroidListener(listener);

        return mView;
    }

    private void getSessionWorkouts() {
        final DateTime now = new DateTime();
        mSchedules = db.getMonthSchedules(now.getMonthOfYear());
    }

    private void initCaldroidCalendarWidget() {
        caldroid = new CaldroidFragment();

        Calendar calendar = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt(CaldroidFragment.MONTH, calendar.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, calendar.get(Calendar.YEAR));
        args.putInt(CaldroidFragment.THEME_RESOURCE, R.style.CaldroidEnhancedDefault);
        caldroid.setArguments(args);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.caldroid_layout, caldroid).commit();
    }

    private void assignSchedulesToCalendar() { // TODO deal with this drawable mess
        final Drawable green = new ColorDrawable(getResources().getColor(R.color.green));
        final Drawable red = new ColorDrawable(getResources().getColor(R.color.caldroid_light_red));
        final Drawable blue = new ColorDrawable(getResources().getColor(R.color.googleBlue));
        final LocalDate today = LocalDate.now();
        mScheduleDrawableMap = new HashMap<>();

        // populate map with a color assigned to each date for Caldroid
        Date currentDate;
        int dateComparer;
        for (ScheduledSession currentSchedule : mSchedules) {
            currentDate = currentSchedule.getDate().toDate();
            if (currentSchedule.getStatus().equals("Complete")) {
                mScheduleDrawableMap.put(currentDate, green);
            } else {
                dateComparer = currentSchedule.getDate().compareTo(today);
                if (dateComparer < 0) { // PAST workout
                    mScheduleDrawableMap.put(currentDate, red);
                } else { // FUTURE/PRESENT workout
                    mScheduleDrawableMap.put(currentDate, blue);
                }
            }
        }

        caldroid.setBackgroundDrawableForDates(mScheduleDrawableMap);

       /* for (ScheduledSession currentSchedule : mSchedules){
            Date currentDate = java.sql.Date.valueOf(currentSchedule.getDate().toString());
            if (currentSchedule.getCompleted().contains("Yes")){
                // Completed: True, Upcoming: False
                mDayColor.put(currentDate, green);
            } else {
                if (currentSchedule.getDate().isBefore(today)){
                    // Completed: False, Upcoming: False
                    mDayColor.put(currentDate, red);
                } else {
                    // Completed: False, Upcoming: True
                    mDayColor.put(currentDate, blue);
                }
            }
        }
*/





       /* for (Map.Entry<Date, Long> scheduleDate : mMonthSchedules.entrySet()) {
            if (scheduleDate.getValue() != -1) {
                Date currentDate = scheduleDate.getKey();

                if (currentDate.compareTo(todayDate) > 0) {
                    mDayColor.put(scheduleDate.getKey(), green);
                } else if (currentDate.compareTo(todayDate) == 0) {
                    mDayColor.put(scheduleDate.getKey(), green);
                } else if (currentDate.compareTo(todayDate) < 0) {
                    mDayColor.put(scheduleDate.getKey(), blue);
                }
            } else {
                mDayColor.put(scheduleDate.getKey(), null);
            }
        }*/

    }
}
