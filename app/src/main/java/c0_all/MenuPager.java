package c0_all;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.jzohndev.no_bullshit_weightlifting_new.R;

import c1_begin.BeginFragment;
import data.IntentResolver;
import data.LoadDates;
import data.LoadExercises;
import data.LoadSchedules;
import data.LoadWorkouts;
import data.PreLoader;
import c2_manage.ManageFragment;
import c4_progression.ProgressionFragment;
import c3_schedule.ScheduleFragment;

/**
 * Created by big1 on 8/8/2016.
 */
public class MenuPager extends FragmentActivity implements ViewPager.OnPageChangeListener {
    private final int BEGIN_FRAGMENT = 0;
    private final int MANAGE_FRAGMENT = 1;
    private final int SCHEDULE_FRAGMENT = 2;
    private final int PROGRESSION_FRAGMENT = 3;

    private final int[] ICONS = {
            R.drawable.lift_selector,
            R.drawable.add_weight_selector,
            R.drawable.schedule_selector,
            R.drawable.graph_selector
    };
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initAppData();
        setContentView(R.layout.activity_menu_pager);

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setAdapter(new MenuPagerAdapter(getSupportFragmentManager()));

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(mViewPager);
        TabLayout.Tab tabLift = tabLayout.getTabAt(0);
        tabLift.setIcon(ICONS[0]);
        TabLayout.Tab tabAddWeight = tabLayout.getTabAt(1);
        tabAddWeight.setIcon(ICONS[1]);
        TabLayout.Tab tabSchedule = tabLayout.getTabAt(2);
        tabSchedule.setIcon(ICONS[2]);
        TabLayout.Tab tabProgression = tabLayout.getTabAt(3);
        tabProgression.setIcon(ICONS[3]);

        resolveCallingPurpose();
    }

    private void resolveCallingPurpose() {
        final IntentResolver resolver = IntentResolver.getInstance();
        if (resolver.getOrgFrom() != null) {
            switch (resolver.getOrgFrom()) {
                case ("TodayChildFragment"):
                    mViewPager.setCurrentItem(SCHEDULE_FRAGMENT);
                    break;

                case ("WorkoutsChildFragment"):
                    mViewPager.setCurrentItem(MANAGE_FRAGMENT);
                    break;

                case ("WeekChildFragment"):
                    mViewPager.setCurrentItem(SCHEDULE_FRAGMENT);
                    break;

                case ("BeginFragment"):
                    if (resolver.getPurpose() == 8) {
                        mViewPager.setCurrentItem(PROGRESSION_FRAGMENT);
                    } else {
                        mViewPager.setCurrentItem(BEGIN_FRAGMENT);
                    }
                    break;

                default:
                    mViewPager.setCurrentItem(SCHEDULE_FRAGMENT);
                    break;
            }
        }
    }

    private void initAppData() {
        LoadDates d = new LoadDates();
        PreLoader p = new PreLoader(this);
        LoadExercises e = new LoadExercises(this);
        LoadWorkouts w = new LoadWorkouts(this);
        LoadSchedules s = new LoadSchedules(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView title = (TextView) toolbar.findViewById(R.id.page_name_text_view);

        switch (position) {
            case (BEGIN_FRAGMENT):
                title.setText("Begin");
                break;
            case (MANAGE_FRAGMENT):
                title.setText("Manage");
                break;
            case (SCHEDULE_FRAGMENT):
                title.setText("Schedule");
                break;
            case (PROGRESSION_FRAGMENT):
                title.setText("Progression");
                break;

        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}

class MenuPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 4;

    public MenuPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case (0):
                return new BeginFragment();
            case (1):
                return new ManageFragment();
            case (2):
                return new ScheduleFragment();
            case (3):
                return new ProgressionFragment();
            default:
                return null;
        }
    }
}
