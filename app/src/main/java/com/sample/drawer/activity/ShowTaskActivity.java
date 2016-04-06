package com.sample.drawer.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.sample.drawer.R;
import com.sample.drawer.database.HelperFactory;

import java.sql.SQLException;

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
    private static final String TASK_ID_INTENT_KEY = "task_id";
    public static final int REQUEST_CODE = 1;

    private int taskID;


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
                        getIntent().getIntExtra(TASK_ID_INTENT_KEY,0),
                        lesson.getText().toString(),
                        deadline.getText().toString(),
                        info.getText().toString());
                startActivityForResult(intent,REQUEST_CODE);
                return true;
            case R.id.complete_task_toolbar:
                try {
                    HelperFactory.getHelper().getTaskDAO().deleteById(taskID);
                    Toast.makeText(getBaseContext(), R.string.completed,
                            Toast.LENGTH_LONG).show();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static Intent newIntent(Context context, int taskID, String textDeadline, String textLesson, String info) {
        Intent intent = new Intent(context.getApplicationContext(), ShowTaskActivity.class);
        intent.putExtra(DEADLINE_INTENT_KEY, textDeadline);
        intent.putExtra(LESSON_INTENT_KEY, textLesson);
        intent.putExtra(INFO_INTENT_KEY, info);
        intent.putExtra(TASK_ID_INTENT_KEY, taskID);
        return intent;
    }

    private void installIntent(Intent intent) {
        deadline.setText(intent.getStringExtra(DEADLINE_INTENT_KEY));
        lesson.setText(intent.getStringExtra(LESSON_INTENT_KEY));
        info.setText(intent.getStringExtra(INFO_INTENT_KEY));
        taskID = intent.getIntExtra(TASK_ID_INTENT_KEY,0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            finish();
        }
    }
}