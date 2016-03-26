package com.sample.drawer.activity;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OrmLiteQueryForAllLoader;
import com.sample.drawer.R;
import com.sample.drawer.scheduleDataBase.Classroom;
import com.sample.drawer.scheduleDataBase.HelperFactory;
import com.sample.drawer.scheduleDataBase.Period;
import com.sample.drawer.scheduleDataBase.PeriodTime;
import com.sample.drawer.scheduleDataBase.PeriodType;
import com.sample.drawer.scheduleDataBase.ScheduleDBHelper;
import com.sample.drawer.scheduleDataBase.Subject;
import com.sample.drawer.scheduleDataBase.Teacher;

import java.sql.SQLException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by serg on 23.03.16.
 */
public class NewLessonActivity extends ActionBarActivity {

    @Bind(R.id.time_lesson) Spinner time;
    @Bind(R.id.fio_teacher) Spinner fioTeacher;
    @Bind(R.id.name_lesson) Spinner nameLesson;
    @Bind(R.id.number_auditory) Spinner numberAuditory;
    @Bind(R.id.type_lesson) Spinner typeLesson;
    @Bind(R.id.type_week) Spinner typeWeek;

    private List<Subject> lessonList;
    private List<Teacher> teacherList;
    private List<PeriodTime> timeList;
    private List<Classroom> auditoryList;
    private List<PeriodType> typeLessonList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_record);
        ButterKnife.bind(this);
        loadLists();
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
            if (lessonList.isEmpty()){
                Toast.makeText(this, R.string.toast_choose_subject,  Toast.LENGTH_SHORT).show();
                return;
            }
            Subject subject = lessonList.get(nameLesson.getSelectedItemPosition());

            Teacher teacher = teacherList.isEmpty() ? null :
                    teacherList.get(fioTeacher.getSelectedItemPosition());
            PeriodType periodType = typeLessonList.isEmpty() ? null :
                    typeLessonList.get(typeLesson.getSelectedItemPosition());
            Classroom classroom = auditoryList.isEmpty() ? null :
                    auditoryList.get(numberAuditory.getSelectedItemPosition());
            if (timeList.isEmpty()){
                Toast.makeText(this, R.string.toast_choose_time,  Toast.LENGTH_SHORT).show();
                return;
            }
            PeriodTime periodTime = timeList.get(time.getSelectedItemPosition());

            String both = getResources().getStringArray(R.array.type_week_list)[WeekType.BOTH.ordinal()],
                    even = getResources().getStringArray(R.array.type_week_list)[WeekType.EVEN.ordinal()],
                    odd = getResources().getStringArray(R.array.type_week_list)[WeekType.ODD.ordinal()];

            boolean firstWeek = (typeWeek.getSelectedItem().toString().compareTo(both) == 0) ||
                    (typeWeek.getSelectedItem().toString().compareTo(odd) == 0);
            boolean secondWeek = (typeWeek.getSelectedItem().toString().compareTo(both) == 0) ||
                    (typeWeek.getSelectedItem().toString().compareTo(even) == 0);

            Period period = new Period.Builder(subject, periodTime, firstWeek, secondWeek)
                    .teacher(teacher).type(periodType).сlassroom(classroom).build();
            helper.getPeriodDAO().create(period);

            id = period.getId();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        startActivity(AddNewSchedule.newIntent(this, id));
    }

    private void loadLists(){
        getLoaderManager().initLoader(ListLoader.LOADER_LESSONS, null, new ListLoader());
        getLoaderManager().initLoader(ListLoader.LOADER_AUDITORIES, null, new ListLoader());
        getLoaderManager().initLoader(ListLoader.LOADER_TIMES, null, new ListLoader());
        getLoaderManager().initLoader(ListLoader.LOADER_TEACHERS, null, new ListLoader());
        getLoaderManager().initLoader(ListLoader.LOADER_LESSON_TYPES, null, new ListLoader());
    }

    enum WeekType {BOTH, EVEN, ODD}

    private class ListLoader implements LoaderManager.LoaderCallbacks {

        public static final int LOADER_TIMES = 1,
                LOADER_LESSONS = 2,
                LOADER_AUDITORIES = 3,
                LOADER_TEACHERS = 4,
                LOADER_LESSON_TYPES = 5;

        @Override
        public Loader onCreateLoader(int id, Bundle args) {
            switch (id) {
                case LOADER_LESSONS:
                    return new OrmLiteQueryForAllLoader<>(getBaseContext(),
                            HelperFactory.getHelper().getSubjectDAO());
                case LOADER_TIMES:
                    return new OrmLiteQueryForAllLoader<>(getBaseContext(),
                            HelperFactory.getHelper().getPeriodTimeDAO());
                case LOADER_AUDITORIES:
                    return new OrmLiteQueryForAllLoader<>(getBaseContext(),
                            HelperFactory.getHelper().getClassroomDAO());
                case LOADER_TEACHERS:
                    return new OrmLiteQueryForAllLoader<>(getBaseContext(),
                            HelperFactory.getHelper().getTeacherDAO());
                case LOADER_LESSON_TYPES:
                    return new OrmLiteQueryForAllLoader<>(getBaseContext(),
                            HelperFactory.getHelper().getPeriodTypeDAO());
            }
            return null;
        }

        @Override
        public void onLoadFinished(Loader loader, Object data) {
            if (!(data instanceof List)) {
                return;
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getBaseContext(),
                    android.R.layout.simple_spinner_dropdown_item, (List<String>) data);
            switch (loader.getId()) {
                case LOADER_LESSONS:
                    lessonList = (List<Subject>) data;
                    nameLesson.setAdapter(adapter);
                    break;
                case LOADER_TIMES:
                    timeList = (List<PeriodTime>) data;
                    time.setAdapter(adapter);
                    break;
                case LOADER_AUDITORIES:
                    auditoryList = (List<Classroom>) data;
                    numberAuditory.setAdapter(adapter);
                    break;
                case LOADER_TEACHERS:
                    teacherList = (List<Teacher>) data;
                    fioTeacher.setAdapter(adapter);
                    break;
                case LOADER_LESSON_TYPES:
                    typeLessonList = (List<PeriodType>) data;
                    typeLesson.setAdapter(adapter);
                    break;
            }
        }

        @Override
        public void onLoaderReset(Loader loader) {
        }
    }
}
