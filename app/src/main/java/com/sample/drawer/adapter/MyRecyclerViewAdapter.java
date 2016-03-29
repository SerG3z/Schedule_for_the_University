package com.sample.drawer.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sample.drawer.R;
import com.sample.drawer.database.Period;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 3/23/2016.
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.BaseHolder> {
    private final static String LOG_TAG = "MyRecyclerViewAdapter";

    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_EMPTY = 1;
    private final ArrayList<Period> periodList = new ArrayList<>();
    private MyClickListener myClickListener;

    public MyRecyclerViewAdapter(List<Period> myDataset) {
        periodList.addAll(myDataset);
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
            holder = new PeriodHolder(view, myClickListener);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty_lesson, parent, false);
            holder = new EmptyObjectHolder(view, myClickListener);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseHolder holder, int position) {
        Period period = periodList.get(position);
        if (period == null){
            return;
        }
        //TODO: устранить проблемы с null
        if (period.getTime() == null || period.getType() == null || period.getSubject() == null ||
                period.getTeacher() == null || period.getClassroom() == null)
                    return;
        if (getItemViewType(position) == TYPE_NORMAL) {
            ((PeriodHolder) holder).time.setText(period.getTime().toString());
            ((PeriodHolder) holder).typeLesson.setText(period.getType().toString());
            ((PeriodHolder) holder).nameLesson.setText(period.getSubject().toString());
            ((PeriodHolder) holder).fioTeacher.setText(period.getTeacher().toString());
            ((PeriodHolder) holder).numberAuditory.setText(period.getClassroom().toString());
        } else {
            ((EmptyObjectHolder) holder).time.setText(period.getTime().toString());
        }
    }

    public void addItem(Period period, int index) {
        periodList.add(period);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        periodList.remove(index);
        notifyItemRemoved(index);
    }

    public void replaceData(final List<Period> periods) {
        periodList.clear();
        periodList.addAll(periods);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return periodList.size();
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

    public class PeriodHolder extends BaseHolder implements View.OnClickListener {
        @Bind(R.id.item_time) TextView time;
        @Bind(R.id.item_type_lesson) TextView typeLesson;
        @Bind(R.id.item_name_lesson) TextView nameLesson;
        @Bind(R.id.item_fio_teacher) TextView fioTeacher;
        @Bind(R.id.item_number_auditory) TextView numberAuditory;

        private MyClickListener clickListener;

        public PeriodHolder(View itemView, MyClickListener myClickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            clickListener = myClickListener;

            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(getPosition(), view);
        }
    }

    public class EmptyObjectHolder extends BaseHolder implements View.OnClickListener {
        @Bind(R.id.item_time) TextView time;

        private MyClickListener clickListener;

        public EmptyObjectHolder(final View itemView, MyClickListener myClickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            clickListener = myClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(final View view) {
            clickListener.onItemClick(getPosition(), view);
        }
    }

}