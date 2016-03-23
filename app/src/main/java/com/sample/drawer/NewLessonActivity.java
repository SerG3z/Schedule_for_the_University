package com.sample.drawer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Spinner;
import android.widget.TextView;

import com.sample.drawer.model.Data;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by serg on 23.03.16.
 */
public class NewLessonActivity extends ActionBarActivity {

    @Bind(R.id.time_lesson) TextView time;
    @Bind(R.id.fio_teacher) TextView fioTeacher;
    @Bind(R.id.name_lesson) TextView nameLesson;
    @Bind(R.id.number_auditory) TextView numberAuditory;
    @Bind(R.id.type_lesson) Spinner typeLesson;
    @Bind(R.id.type_week) Spinner typeWeek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_record);
        ButterKnife.bind(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.button_save)
    public void onClickButtonSave() {

        Data itemRecord = new Data(
                time.getText().toString(),
                typeLesson.getSelectedItem().toString(),
                nameLesson.getText().toString(),
                fioTeacher.getText().toString(),
                numberAuditory.getText().toString(),
                typeWeek.getSelectedItem().toString()
        );

        startActivity(AddNewSchedule.newIntent(this, itemRecord));
    }
}
