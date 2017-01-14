package c0_all;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by big1 on 8/8/2016.
 */
public class MenuViewPager extends ViewPager {

    public MenuViewPager(Context context) {
        super(context);
    }

    @Override
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        if (v != this && v instanceof ViewPager){
            return true;
        }
        return super.canScroll(v, checkV, dx, x, y);
    }
}
