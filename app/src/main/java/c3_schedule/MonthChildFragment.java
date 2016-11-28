package c3_schedule;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jzohndev.no_bullshit_weightlifting_new.R;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import data.LoadDates;
import database.DatabaseHelper;
import database.Schedule;

public class MonthChildFragment extends Fragment {
    private DatabaseHelper db;
    private List<Schedule> mSchedules;
    private Map<Date, Drawable> mScheduleDrawableMap;


    private Map<Date, Drawable> mDayColors;


    private View mView;
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

        getSchedules();
        initCaldroidCalendarWidget();
        assignSchedulesToCalendar();

        // Selected Date
        final TextView workoutName = (TextView) mView.findViewById(R.id.workout_name_text_view);
        final CaldroidListener listener = new CaldroidListener() {
            @Override
            public void onSelectDate(Date input, View view) {
                LocalDate localDate = new LocalDate(input);
                Schedule selected = db.getSchedule(localDate);

                if (selected.getWorkout().getId() != -1){
                    workoutName.setText(db.getWorkout(selected.getWorkout().getId()).getName());
                } else {
                    workoutName.setText("No workout");
                }
            }
        };
        caldroid.setCaldroidListener(listener);

        return mView;
    }

    private void getSchedules() {
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

    private void assignSchedulesToCalendar() {
        Drawable green = new ColorDrawable(getResources().getColor(R.color.green));
        Drawable red = new ColorDrawable(getResources().getColor(R.color.caldroid_light_red));
        Drawable blue = new ColorDrawable(getResources().getColor(R.color.googleBlue));
        LocalDate today = LocalDate.now();
        mScheduleDrawableMap = new HashMap<>();

        for (Schedule currentSchedule : mSchedules) {
            Date currentDate = currentSchedule.getDate().toDate();

            if (currentSchedule.getStatus().equals("Complete")) {
                mScheduleDrawableMap.put(currentDate, blue);
            } else {
                if (today.compareTo(currentSchedule.getDate()) == 0) {
                    mScheduleDrawableMap.put(currentDate, red);
                } else {
                    mScheduleDrawableMap.put(currentDate, green);
                }
            }
        }

        caldroid.setBackgroundDrawableForDates(mScheduleDrawableMap);

       /* for (Schedule currentSchedule : mSchedules){
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
