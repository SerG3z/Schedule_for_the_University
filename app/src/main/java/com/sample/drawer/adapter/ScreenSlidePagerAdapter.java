package com.sample.drawer.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.ViewGroup;

import com.sample.drawer.fragments.schedule.ItemDayFragment;
import com.sample.drawer.database.HelperFactory;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by admin on 3/23/2016.
 */
public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter implements ViewPager.OnPageChangeListener {

    private static final int NUM_PAGES = 366;

    private ViewPager viewPager;
    private int semBegin;
    int daysInSemester;

    public ScreenSlidePagerAdapter(FragmentManager fragmentManager, ViewPager pager) {
        super(fragmentManager);
        viewPager = pager;
        try {
            daysInSemester = (int)HelperFactory.getHelper().getDayDAO().countOf();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public Fragment getItem(int position) {
        return ItemDayFragment.newInstance((daysInSemester+(position - semBegin + 1))%daysInSemester);
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }

    @Override
    public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {

    }

    public void setSemBegin(Date semesterBegin){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(semesterBegin);
        semBegin = calendar.get(Calendar.DAY_OF_YEAR);
        if (viewPager != null){
            viewPager.setCurrentItem(Calendar.getInstance().get(Calendar.DAY_OF_YEAR),true);
        }
    }

    @Override
    public void onPageScrollStateChanged(final int state) {
    }
//

}
