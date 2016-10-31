package c2_manage;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import routine.RoutineBuilder;
import workout.WorkoutBuilder;

/**
 * Created by big1 on 8/8/2016.
 */
public class ManageFragment extends Fragment {
    private final int WORKOUTS_FRAGMENT = 0;
    private final int ROUTINES_FRAGMENT = 1;
    private View mView;
    private IntentResolver mResolver;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_manage, container, false);
        mResolver = IntentResolver.getInstance();

        final ViewPager viewPager = (ViewPager) mView.findViewById(R.id.view_pager);
        viewPager.setAdapter(new SchedulePagerAdapter(getChildFragmentManager()));

        final TabLayout tabLayout = (TabLayout) mView.findViewById(R.id.tab_layout);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText("WORKOUTS");
        tabLayout.getTabAt(1).setText("ROUTINES");

        final FloatingActionButton fab =
                (FloatingActionButton) mView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (viewPager.getCurrentItem()){
                    case (WORKOUTS_FRAGMENT):
                        mResolver.setIntent("WorkoutsChildFragment", "ManageFragment", -1);
                        startActivity(new Intent(getActivity(), WorkoutBuilder.class));
                        break;
                    case (ROUTINES_FRAGMENT):
                        mResolver.setIntent("RoutinesChildFragment", "ManageFragment", -1);
                        startActivity(new Intent(getActivity(), RoutineBuilder.class));
                        break;
                }
            }
        });

        return mView;
    }
}

class SchedulePagerAdapter extends FragmentStatePagerAdapter {
    final int PAGE_COUNT = 2;

    public SchedulePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case (0):
                return new WorkoutsChildFragment();
            case (1):
                return new RoutinesChildFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}