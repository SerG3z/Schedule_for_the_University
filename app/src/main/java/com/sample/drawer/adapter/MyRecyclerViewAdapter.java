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
import java.util.List;

/**
 * Created by admin on 3/23/2016.
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.BaseHolder> {
    private final static String LOG_TAG = "MyRecyclerViewAdapter";

    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_EMPTY = 1;
    private final ArrayList<Data> dataList = new ArrayList<>();
    private MyClickListener myClickListener;

    public MyRecyclerViewAdapter(ArrayList<Data> myDataset) {
        dataList.addAll(myDataset);
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseHolder holder;
        if (viewType == TYPE_NORMAL) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_lesson, parent, false);
            holder = new DataObjectHolder(view, myClickListener);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty_lesson, parent, false);
            holder = new EmptyObjectHolder(view, myClickListener);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseHolder holder, int position) {
        if (getItemViewType(position) == TYPE_NORMAL) {
            ((DataObjectHolder) holder).time.setText(dataList.get(position).getTime());
            ((DataObjectHolder) holder).typeLesson.setText(dataList.get(position).getTypeLesson());
            ((DataObjectHolder) holder).nameLesson.setText(dataList.get(position).getNameLesson());
            ((DataObjectHolder) holder).fioTeacher.setText(dataList.get(position).getFioTeacher());
            ((DataObjectHolder) holder).numberAuditory.setText(dataList.get(position).getNumberAuditory());
        } else {
            ((EmptyObjectHolder) holder).time.setText(dataList.get(position).getTime());
        }
    }

    public void addItem(Data dataObj, int index) {
        dataList.add(dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        dataList.remove(index);
        notifyItemRemoved(index);
    }

    public void replaceData(final List<Data> data) {
        dataList.clear();
        dataList.addAll(data);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public int getItemViewType(final int position) {
        return TYPE_NORMAL;
    }


    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }

    public abstract class BaseHolder extends RecyclerView.ViewHolder {
        public BaseHolder(final View itemView) {
            super(itemView);
        }
    }

    public class DataObjectHolder extends BaseHolder implements View.OnClickListener {
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

        private MyClickListener clickListener;

        public DataObjectHolder(View itemView, MyClickListener myClickListener) {
            super(itemView);
            clickListener = myClickListener;
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
        public void onClick(View view) {
            clickListener.onItemClick(getPosition(), view);
        }
    }

    public class EmptyObjectHolder extends BaseHolder implements View.OnClickListener {
        TextView time;
        private MyClickListener clickListener;

        public EmptyObjectHolder(final View itemView, MyClickListener myClickListener) {
            super(itemView);
            clickListener = myClickListener;
            time = (TextView) itemView.findViewById(R.id.item_time_empty);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(final View view) {
            clickListener.onItemClick(getPosition(), view);
        }
    }

}
