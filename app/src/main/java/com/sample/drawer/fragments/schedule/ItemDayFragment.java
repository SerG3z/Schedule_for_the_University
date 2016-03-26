package com.sample.drawer.fragments.schedule;

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
import com.sample.drawer.R;
import com.sample.drawer.adapter.ScheduleRecyclerViewAdapter;
import com.sample.drawer.decoration.DividerItemDecoration;
import com.sample.drawer.model.Data;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;


public class ItemDayFragment extends Fragment {

    static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";
    int backColor;

    public static ItemDayFragment newInstance(int page) {
        ItemDayFragment scheduleFragment = new ItemDayFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
        scheduleFragment.setArguments(arguments);
        return scheduleFragment;
    }

    public static ArrayList<Data> getDataSet() {
        ArrayList<Data> results = new ArrayList<>();
        for (int index = 0; index < 10; index++) {
            Data obj = new Data("time ", "type ", "nameLesson", "fioTeacher", "numberAuditory", "typeWeek");
            results.add(index, obj);
        }
        return results;
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

        final TextView headerTextView = (TextView) view.findViewById(R.id.headerTextView);
        final RecyclerViewHeader headerWeek = (RecyclerViewHeader) view.findViewById(R.id.headerItemWeek);

        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        //        mRecyclerView.setHasFixedSize(true);
        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);


        final RecyclerView.Adapter recyclerViewAdapter = new ScheduleRecyclerViewAdapter(getDataSet());

        recyclerView.setAdapter(recyclerViewAdapter);
        RecyclerView.ItemDecoration itemDecoration =
                new DividerItemDecoration(this.getActivity(), LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setBackgroundColor(backColor);

        // Code to Add an item with default animation
        //(() mAdapter).addItem(obj, index);

        // Code to remove an item with default animation
        //(() mAdapter).deleteItem(index);

        headerTextView.setText(setCalendarTextView());
        headerWeek.attachTo(recyclerView, true);
        return view;
    }

    private String setCalendarTextView() {
        Calendar calendar = Calendar.getInstance();
        String[] dayWeek = getResources().getStringArray(R.array.day_week_list);
        String date = dayWeek[calendar.get(calendar.DAY_OF_WEEK) - 2];
        return date;
    }


}
