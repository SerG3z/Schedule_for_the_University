package com.sample.drawer.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.sample.drawer.R;
import com.sample.drawer.adapter.MyRecyclerViewAdapter;
import com.sample.drawer.decoration.DividerItemDecoration;
import com.sample.drawer.model.Data;

import java.util.ArrayList;

/**
 * Created by admin on 3/23/2016.
 */
public class AddNewSchedule extends AppCompatActivity {
    private final static String RETURN_RECORD_KEY = "week";
    private static String LOG_TAG = "RecyclerViewActivity";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public static Intent newIntent(Context context, Data item) {
        Intent intent = new Intent(context, AddNewSchedule.class);
        intent.putExtra(RETURN_RECORD_KEY, (Parcelable) item);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_day);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
//        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        mAdapter = new MyRecyclerViewAdapter(getDataSet());
        mRecyclerView.setAdapter(mAdapter);
        RecyclerView.ItemDecoration itemDecoration =
                new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);


        // Code to Add an item with default animation
        //((MyRecyclerViewAdapter) mAdapter).addItem(obj, index);

        // Code to remove an item with default animation
        //((MyRecyclerViewAdapter) mAdapter).deleteItem(index);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarForTabs);
        setSupportActionBar(toolbar);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        TabLayout tabs = (TabLayout) findViewById(R.id.tabsWeek);
        tabs.addTab(tabs.newTab().setText("Tab 1"));
        tabs.addTab(tabs.newTab().setText("Tab 2"));
        tabs.addTab(tabs.newTab().setText("Tab 3"));
        tabs.addTab(tabs.newTab().setText("Tab 4"));
        tabs.addTab(tabs.newTab().setText("Tab 5"));
        tabs.addTab(tabs.newTab().setText("Tab 6"));
        tabs.addTab(tabs.newTab().setText("Tab 7"));

        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private Data initializateIntent() {
        Data item = null;
        Intent intent = getIntent();
        if (intent != null) {
            item = intent.getParcelableExtra(RETURN_RECORD_KEY);
        }
        return item;
    }


    @Override
    protected void onResume() {
        super.onResume();
        ((MyRecyclerViewAdapter) mAdapter).setOnItemClickListener(
                new MyRecyclerViewAdapter.MyClickListener() {
                    @Override
                    public void onItemClick(int position, View v) {
                        Intent intent = new Intent(getBaseContext(), NewLessonActivity.class);
                        startActivity(intent);
                        Log.i(LOG_TAG, " Clicked on Item " + position);
                    }
                });
    }

    //    получить все данные с базы
    private ArrayList<Data> getDataSet() {
        ArrayList results = new ArrayList<Data>();
        for (int index = 0; index < 10; index++) {
            Data obj = new Data("time ", "type ", "nameLesson", "fioTeacher", "numberAuditory", "typeWeek");
            if (initializateIntent() != null) {
                results.add(index, initializateIntent());
            } else {
                results.add(index, obj);
            }
        }
        return results;
    }
}
