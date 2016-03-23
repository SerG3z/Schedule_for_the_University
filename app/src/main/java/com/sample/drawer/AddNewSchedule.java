package com.sample.drawer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.sample.drawer.adapter.MyRecyclerViewAdapter;
import com.sample.drawer.decoration.DividerItemDecoration;
import com.sample.drawer.model.Data;

import java.util.ArrayList;

/**
 * Created by admin on 3/23/2016.
 */
public class AddNewSchedule extends ActionBarActivity {
    private static String LOG_TAG = "RecyclerViewActivity";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

//    private final static String TIME_KEY = "time";
//    private final static String TYPELESSON_KEY = "typeLesson";
//    private final static String NAME_KEY = "name";
//    private final static String FIO_KEY = "fio";
//    private final static String NUNMBER_AUDITORY_KEY = "auditory";
//    private final static String TYPEWEEK_KEY = "week";

    private final static String RETURN_RECORD_KEY = "week";


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
    }

    public static Intent newIntent(Context context, Data item) {
        Intent intent = new Intent(context, AddNewSchedule.class);
        intent.putExtra(RETURN_RECORD_KEY, (Parcelable) item);
        return intent;
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

    private ArrayList<Data> getDataSet() {
        ArrayList results = new ArrayList<Data>();
        for (int index = 0; index < 10; index++) {
            Data obj = new Data("time ", "type ", "nameLesson", "fioTeacher", "numberAuditory", "typeWeek");
            if (initializateIntent()!= null) {
                results.add(index, initializateIntent());
            }
            else {
                results.add(index, obj);
            }
        }
        return results;
    }
}
