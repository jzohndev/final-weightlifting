package exercise;

import android.media.Image;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jzohndev.no_bullshit_weightlifting_new.R;

import data.IntentResolver;
import database.Exercise;

/**
 * Created by jzohn on 2/1/2017.
 */

public class ExerciseInfoPager extends FragmentActivity {
    private final int ABOUT_FRAGMENT = 0;
    private final int PERSONAL_FRAGMENT = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_exercise_information_pager);
        resolveIntent();

        final ImageView upButton = (ImageView) findViewById(R.id.up_button_image_view);
        upButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new ExerciseInfoAdapter(getSupportFragmentManager()));

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
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
        tabLayout.getTabAt(0).setText("ABOUT");
        tabLayout.getTabAt(1).setText("PERSONAL");
    }

    private void resolveIntent(){
        IntentResolver resolver = IntentResolver.getInstance();
        final Exercise exercise = resolver.getExercise();
        final TextView exerciseTextView = (TextView) findViewById(R.id.exercise_name_textview);
        exerciseTextView.setText(exercise.getName());
    }

    class ExerciseInfoAdapter extends FragmentStatePagerAdapter {
        protected int PAGE_COUNT = 2;

        ExerciseInfoAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case (0):
                    return new ExerciseAboutFragment();
                case (1):
                    return new ExercisePersonalFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }
    }
}
