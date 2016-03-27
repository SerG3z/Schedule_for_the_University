package com.sample.drawer.activity;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.sample.drawer.R;
import com.sample.drawer.adapter.MyRecyclerViewAdapter;
import com.sample.drawer.decoration.DividerItemDecoration;
import com.sample.drawer.model.Data;
import com.sample.drawer.scheduleDataBase.Period;
import com.sample.drawer.scheduleDataBase.ScheduleDBHelper;
import com.sample.drawer.utils.LoaderIdManager;
import com.sample.drawer.utils.OrmLiteQueryForIdLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 3/23/2016.
 */
public class AddNewSchedule extends AppCompatActivity {
    private static final  String RETURN_RECORD_KEY = "week";
    private static final  String PERIOD_ID_KEY = "periodId";
    private static final  int LOADER_PERIOD_BY_ID = 1;
    private static String LOG_TAG = "RecyclerViewActivity";
    private RecyclerView.Adapter mAdapter;

    TabLayout tabs;

    public static Intent newIntent(Context context, int newPeriodId) {
        Intent intent = new Intent(context, AddNewSchedule.class);
        intent.putExtra(RETURN_RECORD_KEY, newPeriodId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_day);

        final RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
//        mRecyclerView.setHasFixedSize(true);
        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
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

        tabs = (TabLayout) findViewById(R.id.tabsWeek);
        fillTabLayout();

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

    private void fillTabLayout() {
        String[] arrayWeek = getResources().getStringArray(R.array.day_week_list);
        for (final String anArrayWeek : arrayWeek) {
            tabs.addTab(tabs.newTab().setText(anArrayWeek));
        }
    }

    private Period initializeIntent() {
        final Period[] period = {null};
        Intent intent = getIntent();
        int id = -1;
        if (intent != null) {
            id = intent.getIntExtra(RETURN_RECORD_KEY, -1);
        }
        if (id != -1){
            Bundle bundle = new Bundle();
            bundle.putInt(PERIOD_ID_KEY, id);
            getLoaderManager().initLoader(LOADER_PERIOD_BY_ID, null, new LoaderManager.LoaderCallbacks<List<Period>>() {
                @Override
                public Loader<List<Period>> onCreateLoader(int id, Bundle args) {
                    ScheduleDBHelper helper = OpenHelperManager
                            .getHelper(getApplicationContext(), ScheduleDBHelper.class);
                    return new OrmLiteQueryForIdLoader<>(getBaseContext(),
                            helper.getPeriodDAO(), args.getInt(PERIOD_ID_KEY));
                }
                @Override
                public void onLoadFinished(Loader<List<Period>> loader, List<Period> data) {
                    if (!data.isEmpty())
                        period[0] = data.get(0);
                }
                @Override
                public void onLoaderReset(Loader<List<Period>> loader) {}
            });
        }
        return period[0];
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

    //    получить расписание дня (абстрактное)
    private ArrayList<Data> getDataSet() {
        ArrayList<Data> results = new ArrayList<>();
        for (int index = 0; index < 10; index++) {
            Data obj = new Data("time ", "type ", "nameLesson", "fioTeacher", "numberAuditory", "typeWeek");
            /*if (initializeIntent() != null) {
                //results.add(index, initializeIntent());
            } else {
                results.add(index, obj);
            }*/
            results.add(index, obj);
        }
        return results;
    }
}
