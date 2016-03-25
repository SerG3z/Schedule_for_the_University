package com.sample.drawer.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.Spinner;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.sample.drawer.R;
import com.sample.drawer.model.Data;
import com.sample.drawer.scheduleDataBase.Classroom;
import com.sample.drawer.scheduleDataBase.Day;
import com.sample.drawer.scheduleDataBase.HelperFactory;
import com.sample.drawer.scheduleDataBase.Period;
import com.sample.drawer.scheduleDataBase.PeriodTime;
import com.sample.drawer.scheduleDataBase.PeriodType;
import com.sample.drawer.scheduleDataBase.ScheduleDBHelper;
import com.sample.drawer.scheduleDataBase.Subject;
import com.sample.drawer.scheduleDataBase.Teacher;
import com.sample.drawer.scheduleDataBase.Week;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by serg on 23.03.16.
 */
public class NewLessonActivity extends ActionBarActivity {

    enum WeekType {BOTH, EVEN, ODD};

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


    //работа с базой
    @OnClick(R.id.button_save)
    public void onClickButtonSave() {

        ScheduleDBHelper helper = HelperFactory.getHelper();

        int id = -1;
        try {
            Subject subject = new Subject(nameLesson.getText().toString());
            helper.getSubjectDAO().create(subject);
            Teacher teacher = new Teacher(fioTeacher.getText().toString());
            helper.getTeacherDAO().create(teacher);
            PeriodType periodType = new PeriodType(typeLesson.getSelectedItem().toString());
            helper.getPeriodTypeDAO().create(periodType);
            Classroom classroom = new Classroom(numberAuditory.getText().toString());
            helper.getClassroomDAO().create(classroom);
            PeriodTime periodTime = new PeriodTime(time.getText().toString(),
                    time.getText().toString());
            helper.getPeriodTimeDAO().create(periodTime);

            //TODO: корректное время, день, неделя, нумерация пар

            String both = getResources().getStringArray(R.array.type_week_list)[WeekType.BOTH.ordinal()],
                    even = getResources().getStringArray(R.array.type_week_list)[WeekType.EVEN.ordinal()],
                    odd = getResources().getStringArray(R.array.type_week_list)[WeekType.ODD.ordinal()];

            boolean firstWeek = (typeWeek.getSelectedItem().toString().compareTo(both) == 0) ||
                    (typeWeek.getSelectedItem().toString().compareTo(odd) == 0);
            boolean secondWeek = (typeWeek.getSelectedItem().toString().compareTo(both) == 0) ||
                    (typeWeek.getSelectedItem().toString().compareTo(even) == 0);

            Period period = new Period.Builder(subject,periodTime,firstWeek, secondWeek)
                    .teacher(teacher).type(periodType).сlassroom(classroom).build();
            helper.getPeriodDAO().create(period);

            id = period.getId();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        startActivity(AddNewSchedule.newIntent(this, id));
    }
}
