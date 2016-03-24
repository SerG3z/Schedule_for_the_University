package com.sample.drawer.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.sample.drawer.fragments.schedule.ItemDayFragment;

/**
 * Created by admin on 3/23/2016.
 */
public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter implements ViewPager.OnPageChangeListener {

    private static final String LOG_TAG = "ViewPagerLoop";
    private static final int NUM_PAGES = 7;
    FragmentManager manager;
    ViewPager viewPager;

    public ScreenSlidePagerAdapter(FragmentManager fm, ViewPager pager) {
        super(fm);
        manager = fm;
        viewPager = pager;

    }

    @Override
    public Fragment getItem(int position) {
        return ItemDayFragment.newInstance(position);
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
        Log.d(LOG_TAG, "PageSelector");
        if (position == 0) {
            viewPager.setCurrentItem(NUM_PAGES, false);
            Log.d(LOG_TAG, "Swiped before first page, looping and resetting to last page.");
        } else if (position == NUM_PAGES + 1) {
            viewPager.setCurrentItem(1, false);
            Log.d(LOG_TAG, "Swiped beyond last page, looping and resetting to first page.");
        }
    }

    @Override
    public void onPageScrollStateChanged(final int state) {
    }
}
