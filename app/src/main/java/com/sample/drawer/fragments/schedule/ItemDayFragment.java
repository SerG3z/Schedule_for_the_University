package com.sample.drawer.fragments.schedule;

import android.app.LoaderManager;
import android.content.Loader;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.sample.drawer.R;
import com.sample.drawer.adapter.MyRecyclerViewAdapter;
import com.sample.drawer.decoration.DividerItemDecoration;
import com.sample.drawer.database.Period;
import com.sample.drawer.database.ScheduleDBHelper;
import com.sample.drawer.database.OrmLiteQueryForIdLoader;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;


public class ItemDayFragment extends Fragment {

    @Bind(R.id.headerTextView) TextView headerTextView;
    @Bind(R.id.headerItemWeek) RecyclerViewHeader headerWeek;
    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;

    static final String ARGUMENT_DAY = "arg_page_number";
    static final String KEY_DAY = "day";
    static final int LOADER_DAY = 10;
    int backColor;

    public static ItemDayFragment newInstance(int page) {
        ItemDayFragment scheduleFragment = new ItemDayFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_DAY, page);
        scheduleFragment.setArguments(arguments);
        return scheduleFragment;
    }

    //TODO: убрать
    public static ArrayList<Period> getLessons() {
        ArrayList<Period> results = new ArrayList<>();
        //TODO: вернуть заглушку
        /*for (int index = 0; index < 10; index++) {
            Period period = new Period.Builder(new Subject("Предмет"),
                    new PeriodTime("Начало","Конец"),true, true).build();
            results.add(index, period);
        }*/
        return results;
    }

    private void fillPageWithLessons(int dayOfSemester) {
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_DAY, dayOfSemester);
        getActivity().getLoaderManager().initLoader(LOADER_DAY, bundle, new LoaderManager.LoaderCallbacks<List<Period>>() {
            @Override
            public Loader<List<Period>> onCreateLoader(int id, Bundle args) {
                ScheduleDBHelper helper = OpenHelperManager
                        .getHelper(getContext(), ScheduleDBHelper.class);
                int day = args.getInt(KEY_DAY);
                return new OrmLiteQueryForIdLoader<Period, Integer>(getContext(),
                        helper.getPeriodDAO(), day);
            }
            @Override
            public void onLoadFinished(Loader<List<Period>> loader, List<Period> data) {
                if (data != null && !data.isEmpty()) {
                    RecyclerView.Adapter mAdapter = new MyRecyclerViewAdapter(data);
                    mRecyclerView.setAdapter(mAdapter);
                }
            }
            @Override
            public void onLoaderReset(Loader<List<Period>> loader) {}
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Random random = new Random();
        backColor = Color.argb(50, random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.show_day_schedule, null);
        ButterKnife.bind(this, view);

        fillPageWithLessons(getArguments().getInt(ARGUMENT_DAY));
        //        mRecyclerView.setHasFixedSize(true);
        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        RecyclerView.ItemDecoration itemDecoration =
                new DividerItemDecoration(this.getActivity(), LinearLayoutManager.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);
        mRecyclerView.setBackgroundColor(backColor);

        // Code to Add an item with default animation
        //((MyRecyclerViewAdapter) mAdapter).addItem(obj, index);

        // Code to remove an item with default animation
        //((MyRecyclerViewAdapter) mAdapter).deleteItem(index);

        headerTextView.setText(setCalendarTextView());
        headerWeek.attachTo(mRecyclerView, true);
        return view;
    }

    private String setCalendarTextView() {
        Calendar calendar = Calendar.getInstance();
        String[] dayWeek = getResources().getStringArray(R.array.day_week_list);
        String date = dayWeek[calendar.get(calendar.DAY_OF_WEEK) - 1];
        return date;
    }

}
