package c4_progression;

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

/**
 * Created by big1 on 8/8/2016.
 */
public class ProgressionFragment extends Fragment {
    private View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_progression, container, false);
        final ViewPager viewPager = (ViewPager) mView.findViewById(R.id.view_pager);
        viewPager.setAdapter(new SchedulePagerAdapter(getChildFragmentManager()));

        final TabLayout tabLayout = (TabLayout) mView.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setText("HISTORY");
        tabLayout.getTabAt(1).setText("EXERCISES");
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
                return new HistoryChildFragment();
            case (1):
                return new ExercisesChildFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}