package com.sample.drawer.fragments.schedule;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;
import com.sample.drawer.R;
import com.sample.drawer.adapter.MyRecyclerViewAdapter;
import com.sample.drawer.decoration.DividerItemDecoration;
import com.sample.drawer.model.Data;

import java.util.ArrayList;
import java.util.Random;


public class ItemDayFragment extends Fragment {

    static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerViewHeader headerVeek;


    int backColor;

    public static ItemDayFragment newInstance(int page) {
        ItemDayFragment scheduleFragment = new ItemDayFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
        scheduleFragment.setArguments(arguments);
        return scheduleFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Random random = new Random();
        backColor = Color.argb(150, random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.show_day_schedule, null);

        headerVeek = (RecyclerViewHeader) view.findViewById(R.id.headerItemWeek);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        //        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);


        mAdapter = new MyRecyclerViewAdapter(getDataSet());
        mRecyclerView.setAdapter(mAdapter);
        RecyclerView.ItemDecoration itemDecoration =
                new DividerItemDecoration(this.getActivity(), LinearLayoutManager.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);
        mRecyclerView.setBackgroundColor(backColor);

        headerVeek.attachTo(mRecyclerView, true);
        return view;
    }

    private ArrayList<Data> getDataSet() {
        ArrayList results = new ArrayList<Data>();
        for (int index = 0; index < 10; index++) {
            Data obj = new Data("time ", "type ", "nameLesson", "fioTeacher", "numberAuditory", "typeWeek");
            results.add(index, obj);
        }
        return results;
    }



}
