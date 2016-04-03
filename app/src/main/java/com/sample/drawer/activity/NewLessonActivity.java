package com.sample.drawer.activity;

import android.app.FragmentManager;
import android.app.DialogFragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OrmLitePreparedQueryLoader;
import com.j256.ormlite.android.apptools.OrmLiteQueryForAllLoader;
import com.sample.drawer.R;
import com.sample.drawer.database.Classroom;
import com.sample.drawer.database.Day;
import com.sample.drawer.database.DayPeriod;
import com.sample.drawer.database.HelperFactory;
import com.sample.drawer.database.Period;
import com.sample.drawer.database.PeriodTime;
import com.sample.drawer.database.PeriodType;
import com.sample.drawer.database.ScheduleDBHelper;
import com.sample.drawer.database.Subject;
import com.sample.drawer.database.Teacher;
import com.sample.drawer.database.dao.DayDAO;
import com.sample.drawer.database.dao.DayPeriodDAO;
import com.sample.drawer.database.dao.PeriodDAO;
import com.sample.drawer.database.dao.TeacherDAO;
import com.sample.drawer.database.loader.OrmLiteQueryForAllOrderByLoader;
import com.sample.drawer.fragments.schedule.AddTimeDialogFragment;
import com.sample.drawer.fragments.schedule.AddValueDialogFragment;

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
    @Bind(R.id.button_delete) Button deleteBtn;
    FragmentManager fragmentManager;
    public static final String ARG_DAY_OF_WEEK = "day_of_week";
    public static final String ARG_LESSON_ID = "lesson_id";
    public static final String TAG_DIALOG = "dialog";
    public static final int LOADER_ADD_LESSON = 10;
    public static final int LOADER_EDIT_LESSON = 11;
    public static final int LOADER_DELETE_LESSON = 11;

    private List<Subject> lessonList;
    private List<Teacher> teacherList;
    private List<PeriodTime> timeList;
    private List<Classroom> auditoryList;
    private List<PeriodType> typeLessonList;

    private int lessonID;
    private int dayOfWeek;


    //добавление пары для дня недели
    public static Intent newAddIntent(Context context, int dayOfWeek){
        Intent intent = new Intent(context, NewLessonActivity.class);
        intent.putExtra(ARG_DAY_OF_WEEK, dayOfWeek);
        return intent;
    }

    //редактирование существующей пары
    public static Intent newEditIntent(Context context, int dayOfWeek, int lessonID){
        Intent intent = new Intent(context, NewLessonActivity.class);
        intent.putExtra(ARG_LESSON_ID, lessonID);
        intent.putExtra(ARG_DAY_OF_WEEK, dayOfWeek);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_record);
        ButterKnife.bind(this);
        loadLists();

        lessonID = getIntent().getIntExtra(ARG_LESSON_ID,0);
        dayOfWeek = getIntent().getIntExtra(ARG_DAY_OF_WEEK,0);
        if (lessonID == 0){
            deleteBtn.setEnabled(false);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.button_cancel)
    public void onClickButtonCancel(){
        this.finish();
    }

    @OnClick(R.id.button_delete)
    public void onClickButtonDelete(){
        final Bundle bundle = new Bundle();
        bundle.putInt(ARG_LESSON_ID, lessonID);
        getLoaderManager().initLoader(LOADER_DELETE_LESSON, bundle, new LoaderManager.LoaderCallbacks<List<DayPeriod>>() {
            @Override
            public Loader<List<DayPeriod>> onCreateLoader(int id, Bundle args) {
                DayPeriodDAO dayPeriodDAO = HelperFactory.getHelper().getDayPeriodDAO();
                int lessonID = args.getInt(ARG_LESSON_ID);
                try {
                    return new OrmLitePreparedQueryLoader<>(getBaseContext(), dayPeriodDAO,
                            dayPeriodDAO.getWithPeriodId(lessonID));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public void onLoadFinished(Loader<List<DayPeriod>> loader, List<DayPeriod> data) {
                removeLesson(data);
                finish();
            }

            @Override
            public void onLoaderReset(Loader<List<DayPeriod>> loader) {

            }
        });
    }

    public void removeLesson(List<DayPeriod> dayPeriods){
        for (DayPeriod dp : dayPeriods){
            try {
                HelperFactory.getHelper().getDayPeriodDAO().delete(dp);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        try {
            HelperFactory.getHelper().getPeriodDAO().deleteById(lessonID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //работа с базой
    @OnClick(R.id.button_save)
    public void onClickButtonSave() {
        ScheduleDBHelper helper = HelperFactory.getHelper();
        int id = 0;
        try {
            if (lessonList.isEmpty()){
                Toast.makeText(this, R.string.toast_choose_subject,  Toast.LENGTH_SHORT).show();
                return;
            }
            final Subject subject = lessonList.get(nameLesson.getSelectedItemPosition());

            final Teacher teacher = teacherList.isEmpty() ? null :
                    teacherList.get(fioTeacher.getSelectedItemPosition());
            final PeriodType periodType = typeLessonList.isEmpty() ? null :
                    typeLessonList.get(typeLesson.getSelectedItemPosition());
            final Classroom classroom = auditoryList.isEmpty() ? null :
                    auditoryList.get(numberAuditory.getSelectedItemPosition());
            if (timeList.isEmpty()){
                Toast.makeText(this, R.string.toast_choose_time,  Toast.LENGTH_SHORT).show();
                return;
            }
            final PeriodTime periodTime = timeList.get(time.getSelectedItemPosition());

            String both = getResources().getStringArray(R.array.type_week_list)[WeekType.BOTH.ordinal()],
                    even = getResources().getStringArray(R.array.type_week_list)[WeekType.EVEN.ordinal()],
                    odd = getResources().getStringArray(R.array.type_week_list)[WeekType.ODD.ordinal()];

            final boolean firstWeek = (typeWeek.getSelectedItem().toString().compareTo(both) == 0) ||
                    (typeWeek.getSelectedItem().toString().compareTo(odd) == 0);
            final boolean secondWeek = (typeWeek.getSelectedItem().toString().compareTo(both) == 0) ||
                    (typeWeek.getSelectedItem().toString().compareTo(even) == 0);


            Period period = new Period.Builder(subject, periodTime, firstWeek, secondWeek)
                    .teacher(teacher).type(periodType).сlassroom(classroom).build();

            //добавление
            if (lessonID == 0){
                helper.getPeriodDAO().create(period);
                id = period.getId();
                addLessonToSchedule(period);
            } else { //редактирование
                updateLesson(period);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void addLessonToSchedule(final Period period){
        final Bundle bundle = new Bundle();
        bundle.putInt(ARG_DAY_OF_WEEK,dayOfWeek);
        getLoaderManager().initLoader(LOADER_ADD_LESSON, bundle, new LoaderManager.LoaderCallbacks<List<Day>>() {
            @Override
            public Loader<List<Day>> onCreateLoader(int id, Bundle args) {
                DayDAO dayDAO = HelperFactory.getHelper().getDayDAO();
                int dayOfWeek = args.getInt(ARG_DAY_OF_WEEK);
                try {
                    return new OrmLitePreparedQueryLoader<>(getBaseContext(), dayDAO,
                            dayDAO.getDayByDayOfWeek(dayOfWeek));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public void onLoadFinished(Loader<List<Day>> loader, List<Day> data) {
                addLessonToDays(period,data);
                finish();
            }

            @Override
            public void onLoaderReset(Loader<List<Day>> loader) {

            }
        });
    }


    private void updateLesson(final Period period){
        final Bundle bundle = new Bundle();
        bundle.putInt(ARG_DAY_OF_WEEK,dayOfWeek);
        bundle.putInt(ARG_LESSON_ID,lessonID);
        getLoaderManager().initLoader(LOADER_EDIT_LESSON, bundle, new LoaderManager.LoaderCallbacks<List<Day>>() {
            int lessonID;

            @Override
            public Loader<List<Day>> onCreateLoader(int id, Bundle args) {
                DayDAO dayDAO = HelperFactory.getHelper().getDayDAO();
                int dayOfWeek = args.getInt(ARG_DAY_OF_WEEK);
                lessonID = args.getInt(ARG_LESSON_ID);
                try {
                    return new OrmLitePreparedQueryLoader<>(getBaseContext(), dayDAO,
                            dayDAO.getDayByDayOfWeek(dayOfWeek));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public void onLoadFinished(Loader<List<Day>> loader, List<Day> data) {
                period.setId(lessonID);
                try {
                    HelperFactory.getHelper().getPeriodDAO().update(period);
                    HelperFactory.getHelper().getDayPeriodDAO().deleteByPeriod(period);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                addLessonToDays(period, data);
                finish();
            }

            @Override
            public void onLoaderReset(Loader<List<Day>> loader) {
            }
        });
    }

    private void addLessonToDays(Period period, List<Day> days){
        if (days != null) {
            for (Day d : days) {
                boolean add = true;
                if (d.getWeek().isFirst() && !period.isFirstWeek() ||
                        !d.getWeek().isFirst() && !period.isSecondWeek())
                    add = false;
                if (add) {
                    try {
                        HelperFactory.getHelper().getDayPeriodDAO().
                                create(new DayPeriod(d, period));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
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
                    return new OrmLiteQueryForAllOrderByLoader(getBaseContext(),
                            HelperFactory.getHelper().getSubjectDAO(), Subject.FIELD_SUBJECT);
                case LOADER_TIMES:
                    return new OrmLiteQueryForAllOrderByLoader<>(getBaseContext(),
                            HelperFactory.getHelper().getPeriodTimeDAO(), PeriodTime.FIELD_BEGIN_TIME);
                case LOADER_AUDITORIES:
                    return new OrmLiteQueryForAllOrderByLoader<>(getBaseContext(),
                            HelperFactory.getHelper().getClassroomDAO(), Classroom.FIELD_CLASSROOM);
                case LOADER_TEACHERS:
                    return new OrmLiteQueryForAllOrderByLoader<>(getBaseContext(),
                            HelperFactory.getHelper().getTeacherDAO(), Teacher.FIELD_TEACHER);
                case LOADER_LESSON_TYPES:
                    return new OrmLiteQueryForAllOrderByLoader<>(getBaseContext(),
                            HelperFactory.getHelper().getPeriodTypeDAO(), PeriodType.FIELD_PERIOD_TYPE);
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


    @OnClick(R.id.btn_add_fio_teacher)
    void showAddTeacherDialog(){
        showAddDialog(getString(R.string.fio_teacher));
    }
    @OnClick(R.id.btn_add_name_lesson)
    void showAddLessonDialog(){
        showAddDialog(getString(R.string.name_lesson));
    }
    @OnClick(R.id.btn_add_number_auditory)
    void showAddAuditoryDialog(){
        showAddDialog(getString(R.string.number_auditory));
    }
    @OnClick(R.id.btn_add_type_lesson)
    void showAddLessonTypeDialog(){
        showAddDialog(getString(R.string.type_lesson));
    }
    @OnClick(R.id.btn_add_time)
    void showAddTimeDialog(){
        DialogFragment newFragment = AddTimeDialogFragment.newInstance();
        newFragment.show(getFragmentManager(), TAG_DIALOG);
    }

    private void showAddDialog(String arg){
        DialogFragment newFragment = AddValueDialogFragment.newInstance(arg);
        newFragment.show(getFragmentManager(), TAG_DIALOG);
    }

}
