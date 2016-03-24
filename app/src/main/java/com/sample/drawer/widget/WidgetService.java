package com.sample.drawer.widget;

import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViewsService;

/**
 * Created by admin on 3/24/2016.
 */
public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(final Intent intent) {
        Log.d("widgetLogs", "srartWidgetservice");
        return new WidgetAdapter(getApplicationContext(), intent);
    }
}
