package com.sample.drawer.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sample.drawer.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by admin on 3/28/2016.
 */
public class NewTaskActivity extends AppCompatActivity {

    private static final String LESSON_INTENT_KEY = "lesson_intent_key";
    private static final String DEADLINE_INTENT_KEY = "deadline_intent_key";
    private static final String INFO_INTENT_KEY = "info_intent_key";

    @Bind(R.id.add_task_lesson)
    EditText addTaskLesson;
    @Bind(R.id.add_task_info)
    EditText addTaskInfo;
    @Bind(R.id.add_task_deadline)
    TextView addTaskDeadline;
    @Bind(R.id.toolbar_add_new_task)
    Toolbar toolbar;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_task_show);
        ButterKnife.bind(this);

        initializationIntent(getIntent());

        setSupportActionBar(toolbar);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public static Intent newIntent(Context context, String lesson, String deadline, String info) {
        Intent intent = new Intent(context, NewTaskActivity.class);
        intent.putExtra(LESSON_INTENT_KEY, lesson);
        intent.putExtra(DEADLINE_INTENT_KEY, deadline);
        intent.putExtra(INFO_INTENT_KEY, info);
        return intent;
    }

    private void initializationIntent(Intent intent) {
        if (intent != null) {
            addTaskLesson.setText(intent.getStringExtra(LESSON_INTENT_KEY));
            addTaskDeadline.setText(intent.getStringExtra(DEADLINE_INTENT_KEY));
            addTaskInfo.setText(intent.getStringExtra(INFO_INTENT_KEY));
        }
    }


    @OnClick(R.id.add_task_button_save)
    public void clickToButtonSave() {
        Toast.makeText(getApplicationContext(), "save", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
