package com.sample.drawer.fragments.schedule;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j256.ormlite.android.apptools.OrmLitePreparedQueryLoader;
import com.sample.drawer.R;
import com.sample.drawer.adapter.ScreenSlidePagerAdapter;
import com.sample.drawer.database.Day;
import com.sample.drawer.database.HelperFactory;
import com.sample.drawer.database.loader.OrmLiteQueryForIdLoader;
import com.sample.drawer.fragments.animation.ReaderViewPagerTransformer;

import java.util.Calendar;
import java.util.List;

/**
 * Created by admin on 3/18/2016.
 */
public class ScheduleViewPagerFragment extends Fragment {

    private static final int LOADER_SEMESTER_BEGIN = 1;
    private ScreenSlidePagerAdapter slidePagerAdapter;
    private ViewPager viewPager;

    public ScheduleViewPagerFragment() {
    }

    public static ScheduleViewPagerFragment newIntent() {
        return new ScheduleViewPagerFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_screen_slide, null);
//        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.pager);
//        final ScreenSlidePagerAdapter slidePagerAdapter = new ScreenSlidePagerAdapter(getFragmentManager(), viewPager);
//        viewPager.setAdapter(slidePagerAdapter);
//        viewPager.setPageTransformer(true, new ReaderViewPagerTransformer(ReaderViewPagerTransformer.TransformType.FLOW));
//        viewPager.addOnPageChangeListener(slidePagerAdapter);
//        viewPager.setCurrentItem(75, false);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.info_schedule);
        viewPager = (ViewPager) view.findViewById(R.id.pager);
        slidePagerAdapter = new ScreenSlidePagerAdapter(getFragmentManager(), viewPager);
        findSemesterBegin();
        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void findSemesterBegin(){
        getActivity().getLoaderManager().initLoader(LOADER_SEMESTER_BEGIN, null, new LoaderManager.LoaderCallbacks<List<Day>>() {
            @Override
            public Loader<List<Day>> onCreateLoader(int id, Bundle args) {
                return new OrmLiteQueryForIdLoader<>(getContext(),HelperFactory.getHelper().getDayDAO(), 1);
            }

            @Override
            public void onLoadFinished(Loader<List<Day>> loader, List<Day> data) {
                if (data != null && data.size() > 0){
                    slidePagerAdapter.setSemBegin(data.get(0).getDate());
                }
                viewPager.setAdapter(slidePagerAdapter);
                viewPager.setPageTransformer(true, new ReaderViewPagerTransformer(ReaderViewPagerTransformer.TransformType.FLOW));
                viewPager.addOnPageChangeListener(slidePagerAdapter);
                Calendar calendar = Calendar.getInstance();
                viewPager.setCurrentItem(calendar.get(Calendar.DAY_OF_YEAR), false);
            }

            @Override
            public void onLoaderReset(Loader<List<Day>> loader) {

            }
        });
    }

}
