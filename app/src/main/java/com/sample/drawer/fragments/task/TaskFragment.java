package com.sample.drawer.fragments.task;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sample.drawer.R;
import com.sample.drawer.activity.NewTaskActivity;
import com.sample.drawer.activity.ShowTaskActivity;
import com.sample.drawer.adapter.TaskRecyclerViewAdapter;
import com.sample.drawer.database.HelperFactory;
import com.sample.drawer.database.Task;
import com.sample.drawer.database.loader.OrmLiteQueryForAllOrderByLoader;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class TaskFragment extends Fragment {

    private static final int LOADER_TASKS = 1;

    @Bind(R.id.task_recycler)
    RecyclerView taskRecyclerView;
    private RecyclerView.Adapter recyclerViewAdapter;

    public static TaskFragment newIntent() {
        return new TaskFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.task_fragment, container, false);
        ButterKnife.bind(this, view);
        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        taskRecyclerView.setLayoutManager(layoutManager);

        recyclerViewAdapter = new TaskRecyclerViewAdapter(new ArrayList<Task>());
        taskRecyclerView.setAdapter(recyclerViewAdapter);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.info_tasks);

        return view;
    }


    private void loadTasks(){
        getActivity().getLoaderManager().initLoader((new Random()).nextInt(), null, new LoaderManager.LoaderCallbacks<List<Task>>() {
            @Override
            public Loader<List<Task>> onCreateLoader(int id, Bundle args) {
                return new OrmLiteQueryForAllOrderByLoader<>(getContext(),
                        HelperFactory.getHelper().getTaskDAO(),Task.FIELD_DAY);
            }

            @Override
            public void onLoadFinished(Loader<List<Task>> loader, List<Task> data) {
                if (data != null){
                    ((TaskRecyclerViewAdapter)recyclerViewAdapter).replaceData(data);
                }
            }

            @Override
            public void onLoaderReset(Loader<List<Task>> loader) {
            }
        });
    }

    @OnClick(R.id.add_task_button_create)
    public void clickButtonCreate() {
        Intent intent = new Intent(getContext(), NewTaskActivity.class);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadTasks();
        ((TaskRecyclerViewAdapter) recyclerViewAdapter).setOnItemClickListener(
                new TaskRecyclerViewAdapter.TaskClickListener() {
                    @Override
                    public void onItemClick(int position, View v) {
                        Task task = ((TaskRecyclerViewAdapter) recyclerViewAdapter).
                                getItemTask(position);
                        String deadline = task.getTargetDay() == null ? "" : task.getTargetDay().
                                toString();
                        String lesson = task.getSubject() == null ? "" : task.getSubject().
                                getSubject().toString();
                        Intent intent = ShowTaskActivity.newIntent(getContext(), task.getId(), deadline, lesson,
                                task.getTask());
                        startActivity(intent);
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
