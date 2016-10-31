package c3_schedule;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jzohndev.no_bullshit_weightlifting_new.R;

import data.IntentResolver;

/**
 * Created by big1 on 8/8/2016.
 */
public class ScheduleFragment extends Fragment {
    private final int TODAY_FRAGMENT = 0;
    private final int WEEK_FRAGMENT = 1;
    private final int MONTH_FRAGMENT = 2;
    private View mView;
    private ViewPager mViewPager;
    private IntentResolver mResolver;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_schedule, container, false);
        mResolver = IntentResolver.getInstance();

        mViewPager = (ViewPager) mView.findViewById(R.id.view_pager);
        mViewPager.setAdapter(new SchedulePagerAdapter(getChildFragmentManager()));

        final TabLayout tabLayout = (TabLayout) mView.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(mViewPager);

        tabLayout.getTabAt(0).setText("TODAY");
        tabLayout.getTabAt(1).setText("WEEK");
        tabLayout.getTabAt(2).setText("MONTH");

        resolveCallingPurpose();

        return mView;
    }

    private void resolveCallingPurpose(){
        mResolver = IntentResolver.getInstance();
        if (mResolver.getOrgFrom() != null){
            switch (mResolver.getOrgFrom()){
                case ("TodayChildFragment"):
                    mViewPager.setCurrentItem(TODAY_FRAGMENT);
                    break;
                case ("WeekChildFragment"):
                    mViewPager.setCurrentItem(WEEK_FRAGMENT);
                    break;
                case ("MonthChildFragment"):
                    mViewPager.setCurrentItem(MONTH_FRAGMENT);
                    break;
            }
        }
    }
}

class SchedulePagerAdapter extends FragmentStatePagerAdapter {
    final int PAGE_COUNT = 3;

    public SchedulePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case (0):
                return new TodayChildFragment();
            case (1):
                return new WeekChildFragment();
            case (2):
                return new MonthChildFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
