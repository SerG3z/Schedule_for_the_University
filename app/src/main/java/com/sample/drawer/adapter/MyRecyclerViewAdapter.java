package com.sample.drawer.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sample.drawer.R;
import com.sample.drawer.model.Data;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 3/23/2016.
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private static MyClickListener myClickListener;
    private ArrayList<Data> mDataset;

    public MyRecyclerViewAdapter(ArrayList<Data> myDataset) {
        mDataset = myDataset;
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lesson, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.time.setText(mDataset.get(position).getTime());
        holder.typeLesson.setText(mDataset.get(position).getTypeLesson());
        holder.nameLesson.setText(mDataset.get(position).getNameLesson());
        holder.fioTeacher.setText(mDataset.get(position).getFioTeacher());
        holder.numberAuditory.setText(mDataset.get(position).getNumberAuditory());
    }

    public void addItem(Data dataObj, int index) {
        mDataset.add(dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//        @Bind(R.id.item_time)
        TextView time;
//        @Bind(R.id.item_type_lesson)
        TextView typeLesson;
//        @Bind(R.id.item_name_lesson)
        TextView nameLesson;
//        @Bind(R.id.item_fio_teacher)
        TextView fioTeacher;
//        @Bind(R.id.item_number_auditory)
        TextView numberAuditory;

        public DataObjectHolder(View itemView) {
            super(itemView);
//            ButterKnife.bind(itemView);
            time = (TextView) itemView.findViewById(R.id.item_time);
            typeLesson = (TextView) itemView.findViewById(R.id.item_type_lesson);
            nameLesson = (TextView) itemView.findViewById(R.id.item_name_lesson);
            fioTeacher = (TextView) itemView.findViewById(R.id.item_fio_teacher);
            numberAuditory = (TextView) itemView.findViewById(R.id.item_number_auditory);

            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getPosition(), v);
        }
    }
}
