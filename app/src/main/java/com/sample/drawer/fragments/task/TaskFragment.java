package com.sample.drawer.fragments.task;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sample.drawer.R;
import com.sample.drawer.activity.ShowTaskActivity;
import com.sample.drawer.adapter.TaskRecyclerViewAdapter;
import com.sample.drawer.model.Task;

import java.util.ArrayList;


public class TaskFragment extends Fragment {

    private RecyclerView.Adapter recyclerViewAdapter;

    public static TaskFragment newIntent() {
        return new TaskFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.task_fragment, container, false);
        final RecyclerView taskRecyclerView = (RecyclerView) view.findViewById(R.id.task_recycler);
        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        taskRecyclerView.setLayoutManager(layoutManager);

        recyclerViewAdapter = new TaskRecyclerViewAdapter(getDataTask());
        taskRecyclerView.setAdapter(recyclerViewAdapter);
        return view;
    }

    private ArrayList<Task> getDataTask() {
        ArrayList<Task> result = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Task task = new Task("deadline", "lesson", "information information information information information");
            result.add(i, task);
        }
        return result;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((TaskRecyclerViewAdapter) recyclerViewAdapter).setOnItemClickListener(
                new TaskRecyclerViewAdapter.TaskClickListener() {
                    @Override
                    public void onItemClick(int position, View v) {
                        Task task = ((TaskRecyclerViewAdapter) recyclerViewAdapter).getItemTask(position);
                        Intent intent = ShowTaskActivity.newIntent(getContext(), task.getDeadline(), task.getLesson(), task.getInfo());
                        startActivity(intent);
                    }
                });
    }
}
