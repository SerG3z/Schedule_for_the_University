package com.sample.drawer.activity;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLitePreparedQueryLoader;
import com.sample.drawer.R;
import com.sample.drawer.adapter.ScheduleRecyclerViewAdapter;
import com.sample.drawer.decoration.DividerItemDecoration;
import com.sample.drawer.decoration.FloatingActionButton;
import com.sample.drawer.database.Period;
import com.sample.drawer.database.ScheduleDBHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 3/23/2016.
 */
public class AddNewSchedule extends AppCompatActivity {
    private static final  String RETURN_RECORD_KEY = "week";
    private static final  String DAY_OF_WEEK_KEY = "dayOfWeek";
    private static String LOG_TAG = "RecyclerViewActivity";

    private RecyclerView.Adapter mAdapter;
    private FloatingActionButton fabButton;

    @Bind(R.id.recycler) RecyclerView mRecyclerView;

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
        ButterKnife.bind(this);

//        mRecyclerView.setHasFixedSize(true);
        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        RecyclerView.ItemDecoration itemDecoration =
                new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);

        mAdapter = new ScheduleRecyclerViewAdapter(new ArrayList<Period>());
        mRecyclerView.setAdapter(mAdapter);

        // Code to Add an item with default animation
        //(() ).addItem(obj, index);

        // Code to remove an item with default animation
        //(() ).deleteItem(index);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarForTabs);
        setSupportActionBar(toolbar);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        tabs = (TabLayout) findViewById(R.id.tabsWeek);
        fillTabLayout();
        fillTabWithLessons(tabs.getSelectedTabPosition() + 1);

        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                fillTabWithLessons(tab.getPosition() + 1);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        fabButton = new FloatingActionButton.Builder(this)
                .withDrawable(ContextCompat.getDrawable(getBaseContext(),R.drawable.ic_add_black_18dp))
                .withButtonColor(ContextCompat.getColor(getBaseContext(), R.color.my_accent))
                .withGravity(Gravity.BOTTOM | Gravity.RIGHT)
                .withMargins(0, 0, 16, 16)
                .create();
        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = NewLessonActivity.newAddIntent(AddNewSchedule.this,
                        tabs.getSelectedTabPosition() + 1);
                fabButton.hideFloatingActionButton();
                startActivity(intent);
            }
        });

    }

    private void fillTabLayout() {
        String[] arrayWeek = getResources().getStringArray(R.array.day_week_list);
        for (final String anArrayWeek : arrayWeek) {
            tabs.addTab(tabs.newTab().setText(anArrayWeek));
        }
    }


    public void setAdapterOnClickListener(){
        ((ScheduleRecyclerViewAdapter) mAdapter).setOnItemClickListener(
                new ScheduleRecyclerViewAdapter.ScheduleClickListener() {
                    @Override
                    public void onItemClick(int position, View v) {
                        Intent intent = NewLessonActivity.newEditIntent(getBaseContext(),
                                tabs.getSelectedTabPosition()+1, (int)mAdapter.getItemId(position));
                        startActivity(intent);
                        Log.i(LOG_TAG, " Clicked on Item " + position);
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mAdapter != null){
            setAdapterOnClickListener();
        }
        fabButton.showFloatingActionButton();
    }

    //    заполнить вкладку расписанием выбранного дня
    private void fillTabWithLessons(final int dayOfWeek) {
        Bundle bundle = new Bundle();
        bundle.putInt(DAY_OF_WEEK_KEY, dayOfWeek);
        getLoaderManager().initLoader((new Random()).nextInt(), bundle, new LoaderManager.LoaderCallbacks<List<Period>>() {
            @Override
            public Loader<List<Period>> onCreateLoader(int id, Bundle args) {
                ScheduleDBHelper helper = OpenHelperManager
                        .getHelper(getApplicationContext(), ScheduleDBHelper.class);
                int day = args.getInt(DAY_OF_WEEK_KEY);
                try {
                    return new OrmLitePreparedQueryLoader<>(getBaseContext(),
                            helper.getPeriodDAO(), helper.getPeriodDAO().getPeriodsByDayOfWeek(day));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public void onLoadFinished(Loader<List<Period>> loader, List<Period> data) {
                if (data != null) {
                    ((ScheduleRecyclerViewAdapter) mAdapter).replaceData(data);
                }
            }
            @Override
            public void onLoaderReset(Loader<List<Period>> loader) {
            }
        });
    }
}
