package com.sample.drawer.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.sample.drawer.fragments.schedule.ItemDayFragment;
import com.sample.drawer.database.HelperFactory;

import java.sql.SQLException;

/**
 * Created by admin on 3/23/2016.
 */
public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter implements ViewPager.OnPageChangeListener {

    private static final String LOG_TAG = "ViewPagerLoop";
    private static final int NUM_PAGES = 14;
    private FragmentManager manager;
    private ViewPager viewPager;

    private long weeksCount;
    private int currentCycle;
    private int maxCycle;
    private int currentPosition;

    public ScreenSlidePagerAdapter(FragmentManager fragmentManager, ViewPager pager) {
        super(fragmentManager);
        manager = fragmentManager;
        viewPager = pager;
        currentCycle = 1;
        try {
            weeksCount = HelperFactory.getHelper().getWeekDAO().countOf();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        maxCycle = ((int)weeksCount + 1) / 2;
        currentPosition = 1;
    }

    @Override
    public Fragment getItem(int position) {
        return ItemDayFragment.newInstance(getCurrentDay());
    }

    @Override
    public int getCount() {
        return NUM_PAGES + 2;
    }

    @Override
    public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        currentPosition = position;
        if (position == 0) {
            Log.d(LOG_TAG, "Swiped before first page, looping and resetting to last page.");
            viewPager.setCurrentItem(NUM_PAGES, false);
            currentCycle = currentCycle == 1 ? maxCycle : currentCycle - 1;
        } else if (position == NUM_PAGES + 1) {
            Log.d(LOG_TAG, "Swiped beyond last page, looping and resetting to first page.");
            viewPager.setCurrentItem(1, false);
            currentCycle = currentCycle == maxCycle ? 1 : currentCycle + 1;
        }
        Log.d(LOG_TAG, "PageSelector " + position);
    }

    @Override
    public void onPageScrollStateChanged(final int state) {
    }

    public int getCurrentDay(){
        return (currentCycle -1)*NUM_PAGES + currentPosition;
    }
    public int getCurrentWeek(){
        return (currentCycle - 1)*2 + (currentPosition < 8 ? 1 : 2);
    }
}
