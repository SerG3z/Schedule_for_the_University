
package com.sample.drawer.adapter;

import android.content.Context;
import android.graphics.Color;
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
public class ScheduleRecyclerViewAdapter extends RecyclerView.Adapter<ScheduleRecyclerViewAdapter.BaseHolder> {
    private final static String LOG_TAG = "Schedule Adapter";

    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_EMPTY = 1;
    private final ArrayList<Period> periodList = new ArrayList<>();
    private ScheduleClickListener myClickListener;
    private Context context;

    public ScheduleRecyclerViewAdapter(List<Period> myDataset, Context context) {
        periodList.addAll(myDataset);
        this.context = context;
    }

    public void setOnItemClickListener(ScheduleClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseHolder holder;
//        if (viewType == TYPE_NORMAL) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_lesson, parent, false);
            holder = new PeriodHolder(view, myClickListener);
//        } else {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty_lesson, parent, false);
//            holder = new EmptyObjectHolder(view, myClickListener);
//        }
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseHolder holder, int position) {
        if (!(periodList.get(position) instanceof Period)){
            return;
        }
        Period period = periodList.get(position);
        if (period == null){
            return;
        }
        if (getItemViewType(position) == TYPE_NORMAL) {
            ((PeriodHolder) holder).time.setText(period.getTime().toString());

            final String typeLesson = String.valueOf(period.getType());
            ((PeriodHolder) holder).typeLesson.setText(typeLesson);

            setColorTextViewTypeLesson(((PeriodHolder) holder), typeLesson);

            ((PeriodHolder) holder).nameLesson.setText(period.getSubject().toString());
            ((PeriodHolder) holder).fioTeacher.setText(period.getTeacher().toString());
            ((PeriodHolder) holder).numberAuditory.setText(period.getClassroom().toString());
        } else {
            ((EmptyObjectHolder) holder).time.setText(period.getTime().toString());
        }
    }

    private void setColorTextViewTypeLesson(PeriodHolder holder, String typeLesson) {
        String[] typeLessonArray = context.getResources().getStringArray(R.array.type_lesson_list);
        if (typeLesson.equals(typeLessonArray[0])){
            holder.typeLesson.setBackgroundColor(context.getResources().getColor(R.color.type_lesson_lecture));
        } else {
            if (typeLesson.equals(typeLessonArray[1])){
                holder.typeLesson.setBackgroundColor(context.getResources().getColor(R.color.type_lesson_practice));
            } else {
                if (typeLesson.equals(typeLessonArray[2])){
                    holder.typeLesson.setBackgroundColor(context.getResources().getColor(R.color.type_lesson_laboratory));
                } else {
                    if (typeLesson.equals(typeLessonArray[3])){
                        holder.typeLesson.setBackgroundColor(context.getResources().getColor(R.color.type_lesson_exam));
                    } else {
                        holder.typeLesson.setBackgroundColor(context.getResources().getColor(R.color.type_lesson_default));
                    }
                }
            }
        }
    }

    @Override
    public long getItemId(int position) {
        return periodList.get(position).getId();
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


    public interface ScheduleClickListener {
        void onItemClick(int position, View v);
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

        private ScheduleClickListener clickListener;

        public PeriodHolder(View itemView, ScheduleClickListener myClickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            clickListener = myClickListener;

            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null){
                clickListener.onItemClick(getPosition(), view);
            }
        }
    }

    public class EmptyObjectHolder extends BaseHolder implements View.OnClickListener {
        @Bind(R.id.item_time) TextView time;

        private ScheduleClickListener clickListener;

        public EmptyObjectHolder(final View itemView, ScheduleClickListener myClickListener) {
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
