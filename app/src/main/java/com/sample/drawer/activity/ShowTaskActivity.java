package com.sample.drawer.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.sample.drawer.R;

/**
 * Created by admin on 3/26/2016.
 */
public class ShowTaskActivity extends AppCompatActivity {

    TextView deadline;
    TextView lesson;
    TextView info;

    private static final String DEADLINE_INTENT_KEY = "deadline";
    private static final String LESSON_INTENT_KEY = "lesson";
    private static final String INFO_INTENT_KEY = "info";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_task_show_info);

        deadline = (TextView) findViewById(R.id.task_item_deadline_show);
        lesson = (TextView) findViewById(R.id.task_item_lesson_show);
        info = (TextView) findViewById(R.id.task_item_info_show);

        Toolbar toolbar = (Toolbar) findViewById(R.id.show_task_toolbar);
        setSupportActionBar(toolbar);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);

        installIntent(getIntent());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_show_task, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_task_toolbar:
                Intent intent = NewTaskActivity.newIntent(getApplicationContext(),
                        lesson.getText().toString(),
                        deadline.getText().toString(),
                        info.getText().toString());
                startActivity(intent);
                return true;
            case R.id.complete_task_toolbar:
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static Intent newIntent(Context context, String textDeadline, String textLesson, String info) {
        Intent intent = new Intent(context.getApplicationContext(), ShowTaskActivity.class);
        intent.putExtra(DEADLINE_INTENT_KEY, textDeadline);
        intent.putExtra(LESSON_INTENT_KEY, textLesson);
        intent.putExtra(INFO_INTENT_KEY, info);
        return intent;
    }

    private void installIntent(Intent intent) {
        deadline.setText(intent.getStringExtra(DEADLINE_INTENT_KEY));
        lesson.setText(intent.getStringExtra(LESSON_INTENT_KEY));
        info.setText(intent.getStringExtra(INFO_INTENT_KEY));
    }
}
