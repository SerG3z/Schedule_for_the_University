package com.sample.drawer.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.sample.drawer.R;
import com.sample.drawer.fragments.schedule.ItemDayFragment;
import com.sample.drawer.database.Period;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 3/24/2016.
 */
public class WidgetAdapter implements RemoteViewsService.RemoteViewsFactory {

    List<Period> periodList;
    Context context;
    int widgetId;

    public WidgetAdapter(final Context context, Intent intent) {
        this.context = context;
        widgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);

    }

    @Override
    public void onCreate() {
        periodList = new ArrayList<Period>();
        periodList = ItemDayFragment.getLessons();
        Log.d("widgetLogs", "onCreateWidgetAdapter");
    }

    @Override
    public int getCount() {
        return periodList.size();
    }

    @Override
    public long getItemId(final int position) {
        return position;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public RemoteViews getViewAt(final int position) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.item_lesson);

        remoteViews.setTextViewText(R.id.item_time, periodList.get(position).getTime().toString());
        remoteViews.setTextViewText(R.id.item_type_lesson, periodList.get(position).getType().toString());
        remoteViews.setTextViewText(R.id.item_name_lesson, periodList.get(position).getSubject().toString());
        remoteViews.setTextViewText(R.id.item_fio_teacher, periodList.get(position).getTeacher().toString());
        remoteViews.setTextViewText(R.id.item_number_auditory, periodList.get(position).getClassroom().toString());
        return remoteViews;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void onDataSetChanged() {
        periodList = ItemDayFragment.getLessons();
    }

    @Override
    public void onDestroy() {

    }
}
