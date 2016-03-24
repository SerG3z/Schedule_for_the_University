package com.sample.drawer.fragments.schedule;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sample.drawer.R;
import com.sample.drawer.adapter.ScreenSlidePagerAdapter;
import com.sample.drawer.fragments.animation.ZoomOutPageTransformer;

/**
 * Created by admin on 3/18/2016.
 */
public class ScheduleViewPagerFragment extends Fragment {

    private ViewPager viewPager;
    private ScreenSlidePagerAdapter slidePagerAdapter;

    public ScheduleViewPagerFragment() {
    }

    public static ScheduleViewPagerFragment newIntent() {
        ScheduleViewPagerFragment viewPagerFragment = new ScheduleViewPagerFragment();
        return viewPagerFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_screen_slide, null);
        viewPager = (ViewPager) view.findViewById(R.id.pager);
        slidePagerAdapter = new ScreenSlidePagerAdapter(getFragmentManager(), viewPager);
        viewPager.setAdapter(slidePagerAdapter);
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        viewPager.addOnPageChangeListener(slidePagerAdapter);
        viewPager.setCurrentItem(1, false);
        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

}
