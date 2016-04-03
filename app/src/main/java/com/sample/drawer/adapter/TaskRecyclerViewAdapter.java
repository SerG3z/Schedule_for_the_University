package com.sample.drawer.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sample.drawer.R;
import com.sample.drawer.model.Task;

import java.util.ArrayList;

/**
 * Created by admin on 3/26/2016.
 */
public class TaskRecyclerViewAdapter extends RecyclerView.Adapter<TaskRecyclerViewAdapter.TaskObjectHolder> {

    private TaskClickListener taskClickListener;
    private ArrayList<Task> dataTask = new ArrayList<>();

    public TaskRecyclerViewAdapter(final ArrayList<Task> dataTask) {
        this.dataTask.addAll(dataTask);
    }

    public void setOnItemClickListener(TaskClickListener taskClickListener) {
        this.taskClickListener = taskClickListener;
    }
    @Override
    public TaskObjectHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        TaskObjectHolder holder;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        holder = new TaskObjectHolder(view, taskClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(final TaskRecyclerViewAdapter.TaskObjectHolder holder, final int position) {
        holder.deadline.setText(dataTask.get(position).getDeadline());
        holder.lesson.setText(dataTask.get(position).getLesson());
        holder.info.setText(dataTask.get(position).getInfo());
    }

    @Override
    public int getItemCount() {
        return dataTask.size();
    }

    public Task getItemTask(int position) {
        return dataTask.get(position);
    }

    public interface TaskClickListener {
        public void onItemClick(int position, View v);
    }

    public class TaskObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView deadline;
        TextView lesson;
        TextView info;

        private TaskClickListener taskClickListener;

        public TaskObjectHolder(final View itemView, TaskClickListener taskClickListener) {
            super(itemView);
            this.taskClickListener = taskClickListener;
            deadline = (TextView) itemView.findViewById(R.id.task_item_deadline);
            lesson = (TextView) itemView.findViewById(R.id.task_item_lesson);
            info = (TextView) itemView.findViewById(R.id.task_item_info);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(final View view) {
            taskClickListener.onItemClick(getPosition(), view);
        }
    }

}
