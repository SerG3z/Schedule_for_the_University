package com.sample.drawer.fragments.task;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sample.drawer.R;
import com.sample.drawer.activity.NewTaskActivity;
import com.sample.drawer.activity.ShowTaskActivity;
import com.sample.drawer.adapter.TaskRecyclerViewAdapter;
import com.sample.drawer.model.Task;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class TaskFragment extends Fragment {

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

        recyclerViewAdapter = new TaskRecyclerViewAdapter(getDataTask());
        taskRecyclerView.setAdapter(recyclerViewAdapter);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.info_tasks);

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

    @OnClick(R.id.add_task_button_create)
    public void clickButtonCreate() {
        Toast.makeText(getContext(), "click create", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getContext(), NewTaskActivity.class);
        startActivity(intent);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
