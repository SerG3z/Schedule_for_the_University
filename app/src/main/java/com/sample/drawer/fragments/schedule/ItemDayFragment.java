package com.sample.drawer.fragments.schedule;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLitePreparedQueryLoader;
import com.sample.drawer.R;
import com.sample.drawer.adapter.ScheduleRecyclerViewAdapter;
import com.sample.drawer.database.Day;
import com.sample.drawer.database.HelperFactory;
import com.sample.drawer.decoration.DividerItemDecoration;
import com.sample.drawer.database.Period;
import com.sample.drawer.database.ScheduleDBHelper;
import com.sample.drawer.database.loader.OrmLiteQueryForIdLoader;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;


public class ItemDayFragment extends Fragment {

    @Bind(R.id.headerTextView) TextView headerTextView;
    @Bind(R.id.headerItemWeek) RecyclerViewHeader headerWeek;
    @Bind(R.id.recyclerView) RecyclerView recyclerView;

    static final String ARGUMENT_DAY = "arg_page_number";
    static final String ARGUMENT_HEADER_STRING = "header";
    static final String KEY_DAY = "day";
    static final int LOADER_DAY = 1000;
    private int day;

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
        getActivity().getLoaderManager().initLoader(dayOfSemester, bundle, new LoaderManager.LoaderCallbacks<List<Period>>() {
            @Override
            public Loader<List<Period>> onCreateLoader(int id, Bundle args) {
                ScheduleDBHelper helper = OpenHelperManager
                        .getHelper(getContext(), ScheduleDBHelper.class);
                int day = args.getInt(KEY_DAY);
                try {
                    return new OrmLitePreparedQueryLoader<>(getContext(), helper.getPeriodDAO(),
                            helper.getPeriodDAO().getPeriodsByDay(day));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public void onLoadFinished(Loader<List<Period>> loader, List<Period> data) {
                RecyclerView.Adapter mAdapter = new ScheduleRecyclerViewAdapter(data, getContext());
                recyclerView.setAdapter(mAdapter);
            }
            @Override
            public void onLoaderReset(Loader<List<Period>> loader) {}
        });
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        day = getArguments().getInt(ARGUMENT_DAY);
//        Random random = new Random();
//        backColor = Color.argb(50, random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.show_day_schedule, null);
        ButterKnife.bind(this, view);

        fillPageWithLessons(day);
        //        mRecyclerView.setHasFixedSize(true);
        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);

        RecyclerView.ItemDecoration itemDecoration =
                new DividerItemDecoration(this.getActivity(), LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
//        recyclerView.setBackgroundColor(backColor);

        // Code to Add an item with default animation
        //(() mAdapter).addItem(obj, index);

        // Code to remove an item with default animation
        //(() mAdapter).deleteItem(index);

        headerTextView.setText("");
        headerWeek.attachTo(recyclerView, true);
        setCalendarTextView();
        return view;
    }


    private void setCalendarTextView() {
        Bundle bundle = new Bundle();
        bundle.putInt(ARGUMENT_DAY, day);
        if (getActivity() == null){
            return;
        }
        getActivity().getLoaderManager().initLoader(LOADER_DAY + day, bundle, new LoaderManager.LoaderCallbacks<List<Day>>() {
            @Override
            public Loader<List<Day>> onCreateLoader(int id, Bundle args) {
                int dayID = args.getInt(ARGUMENT_DAY);
                return new OrmLiteQueryForIdLoader<Day, Integer>(getContext(),
                        HelperFactory.getHelper().getDayDAO(), dayID);
            }

            @Override
            public void onLoadFinished(Loader<List<Day>> loader, List<Day> data) {
                if (!data.isEmpty() && data.get(0) != null) {
                    Day d = data.get(0);
                    Calendar myCal = new GregorianCalendar();
                    myCal.setTime(d.getDate());
                    if (!isAdded()){
                        return;
                    }
                    String[] daysOfWeek = getResources().getStringArray(R.array.day_week_list);
                    String s = String.format("%02d.%02d, %s", myCal.get(Calendar.DAY_OF_MONTH),
                            (myCal.get(Calendar.MONTH) + 1), daysOfWeek[d.getDayOfWeek() - 1]);
                    headerTextView.setText(s);
                }
            }

            @Override
            public void onLoaderReset(Loader<List<Day>> loader) {
            }
        });
    }

}
