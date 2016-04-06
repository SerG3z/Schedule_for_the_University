package com.sample.drawer.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.sample.drawer.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 3/29/2016.
 */
public class ShowItemLessonActivity extends AppCompatActivity {

    @Bind(R.id.time_lesson_show)
    TextView timeLesson;
    @Bind(R.id.type_lesson)
    TextView typeLesson;
    @Bind(R.id.name_lesson)
    TextView nameLesson;
    @Bind(R.id.number_auditory)
    TextView numberAuditory;
    @Bind(R.id.fio_teacher)
    TextView fioTeacher;

    private static final String TIME_INTENT_KEY = "time_intent_key";
    private static final String TYPE_INTENT_KEY = "type_intent_key";
    private static final String NAME_INTENT_KEY = "name_intent_key";
    private static final String NUMBER_INTENT_KEY = "number_intent_key";
    private static final String TEACHER_INTENT_KEY = "teacher_intent_key";

    public static Intent newIntent(Context context, String time, String type, String nameLesson, String number, String teacher) {
        Intent intent = new Intent(context, ShowItemLessonActivity.class);
        intent.putExtra(TIME_INTENT_KEY, time);
        intent.putExtra(TYPE_INTENT_KEY, type);
        intent.putExtra(NAME_INTENT_KEY, nameLesson);
        intent.putExtra(NUMBER_INTENT_KEY, number);
        intent.putExtra(TEACHER_INTENT_KEY, teacher);
        return intent;
    }

    private void initializationIntent(Intent intent) {
        if (intent != null) {
            timeLesson.setText(intent.getStringExtra(TIME_INTENT_KEY));
            typeLesson.setText(intent.getStringExtra(TYPE_INTENT_KEY ));
            nameLesson.setText(intent.getStringExtra(NAME_INTENT_KEY ));
            numberAuditory.setText(intent.getStringExtra(NUMBER_INTENT_KEY ));
            fioTeacher.setText(intent.getStringExtra(TEACHER_INTENT_KEY ));
        }
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_lesson_show_info);
        ButterKnife.bind(this);
        initializationIntent(getIntent());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
