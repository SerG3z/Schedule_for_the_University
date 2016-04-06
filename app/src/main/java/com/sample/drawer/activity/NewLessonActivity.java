package com.sample.drawer.activity;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;

import com.j256.ormlite.android.apptools.OrmLitePreparedQueryLoader;
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
import com.sample.drawer.database.loader.OrmLiteQueryForAllOrderByLoader;
import com.sample.drawer.database.loader.OrmLiteQueryForIdLoader;
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

    public static final String ARG_DAY_OF_WEEK = "day_of_week";
    public static final String ARG_LESSON_ID = "lesson_id";
    public static final String TAG_DIALOG = "dialog";
    public static final int LOADER_ADD_LESSON = 10;
    public static final int LOADER_EDIT_LESSON = 11;
    public static final int LOADER_DELETE_LESSON = 12;
    public static final int LOADER_LOAD_LESSON = 13;
    @Bind(R.id.time_lesson)
    Spinner time;
    @Bind(R.id.fio_teacher)
    AutoCompleteTextView fioTeacher;
    @Bind(R.id.name_lesson)
    AutoCompleteTextView nameLesson;
    @Bind(R.id.number_auditory)
    AutoCompleteTextView numberAuditory;
    @Bind(R.id.type_lesson)
    Spinner typeLesson;
    @Bind(R.id.type_week)
    Spinner typeWeek;
    @Bind(R.id.button_delete)
    FloatingActionButton buttonDelete;
    FragmentManager fragmentManager;
    private List<Subject> lessonList;
    private List<Teacher> teacherList;
    private List<PeriodTime> timeList;
    private List<Classroom> auditoryList;
    private List<PeriodType> typeLessonList;

    private int lessonID;
    private int dayOfWeek;
    private int lessonIndex, auditoryIndex, teacherIndex;


    //добавление пары для дня недели
    public static Intent newAddIntent(Context context, int dayOfWeek) {
        Intent intent = new Intent(context, NewLessonActivity.class);
        intent.putExtra(ARG_DAY_OF_WEEK, dayOfWeek);
        return intent;
    }

    //редактирование существующей пары
    public static Intent newEditIntent(Context context, int dayOfWeek, int lessonID) {
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
        lessonID = getIntent().getIntExtra(ARG_LESSON_ID, 0);
        dayOfWeek = getIntent().getIntExtra(ARG_DAY_OF_WEEK, 0);
        if (lessonID != 0) {
            loadPeriod();
        } else {
            buttonDelete.setEnabled(false);
        }

        lessonIndex = auditoryIndex = teacherIndex = -1;
        final int nameThreshold = 2, numThreshold = 1;
        fioTeacher.setThreshold(nameThreshold);
        fioTeacher.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                teacherIndex = -1;
                for (int i = 0; i < teacherList.size(); i++) {
                    if (teacherList.get(i).toString().compareTo(s.toString()) == 0) {
                        teacherIndex = i;
                        break;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        numberAuditory.setThreshold(numThreshold);
        numberAuditory.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                auditoryIndex = -1;
                for (int i = 0; i < auditoryList.size(); i++) {
                    if (auditoryList.get(i).toString().compareTo(s.toString()) == 0) {
                        auditoryIndex = i;
                        break;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        nameLesson.setThreshold(nameThreshold);
        nameLesson.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                lessonIndex = -1;
                for (int i = 0; i < lessonList.size(); i++) {
                    if (lessonList.get(i).toString().compareTo(s.toString()) == 0) {
                        lessonIndex = i;
                        break;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
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


    public void removeLesson(List<DayPeriod> dayPeriods) {
        for (DayPeriod dp : dayPeriods) {
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

    @OnClick(R.id.button_save)
    public void onClickButtonSave() {
        ScheduleDBHelper helper = HelperFactory.getHelper();
        int id = 0;
        try {
            Subject subject;
            if (lessonIndex < 0) {
                subject = new Subject(nameLesson.getText().toString());
                HelperFactory.getHelper().getSubjectDAO().create(subject);
            } else {
                subject = lessonList.get(lessonIndex);
            }
            Teacher teacher;
            if (teacherIndex < 0) {
                teacher = new Teacher(fioTeacher.getText().toString());
                HelperFactory.getHelper().getTeacherDAO().create(teacher);
            } else {
                teacher = teacherList.get(teacherIndex);
            }
            Classroom classroom;
            if (auditoryIndex < 0) {
                classroom = new Classroom(numberAuditory.getText().toString());
                HelperFactory.getHelper().getClassroomDAO().create(classroom);
            } else {
                classroom = auditoryList.get(auditoryIndex);
            }
            final PeriodType periodType = typeLessonList.isEmpty() ? null :
                    typeLessonList.get(typeLesson.getSelectedItemPosition());

            if (timeList.isEmpty()) {
                Toast.makeText(this, R.string.toast_choose_time, Toast.LENGTH_SHORT).show();
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
            if (lessonID == 0) {
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

    private void addLessonToSchedule(final Period period) {
        final Bundle bundle = new Bundle();
        bundle.putInt(ARG_DAY_OF_WEEK, dayOfWeek);
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
                addLessonToDays(period, data);
                finish();
            }

            @Override
            public void onLoaderReset(Loader<List<Day>> loader) {

            }
        });
    }


    private void updateLesson(final Period period) {
        final Bundle bundle = new Bundle();
        bundle.putInt(ARG_DAY_OF_WEEK, dayOfWeek);
        bundle.putInt(ARG_LESSON_ID, lessonID);
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

    private void addLessonToDays(Period period, List<Day> days) {
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


    private void loadPeriod() {
        getLoaderManager().initLoader(LOADER_LOAD_LESSON, null, new LoaderManager.LoaderCallbacks<List<Period>>() {
            @Override
            public Loader<List<Period>> onCreateLoader(int id, Bundle args) {
                return new OrmLiteQueryForIdLoader<>(getBaseContext(),
                        HelperFactory.getHelper().getPeriodDAO(), lessonID);
            }

            @Override
            public void onLoadFinished(Loader<List<Period>> loader, List<Period> data) {
                if (data != null && !data.isEmpty()) {
                    Period period = data.get(0);
                    if (period == null || timeList == null || typeLessonList == null){
                        return;
                    }
                    for (int i = 0; i < timeList.size(); i++) {
                        if (period.getTime().compareTo(timeList.get(i)) == 0) {
                            time.setSelection(i, true);
                        }
                    }
                    nameLesson.setText(period.getSubject().toString());
                    fioTeacher.setText(period.getTeacher().toString());
                    numberAuditory.setText(period.getClassroom().toString());
                    for (int i = 0; i < typeLessonList.size(); i++) {
                        if (period.getType().toString().compareTo(typeLessonList.get(i).toString()) == 0) {
                            typeLesson.setSelection(i, true);
                        }
                    }
                }
            }

            @Override
            public void onLoaderReset(Loader<List<Period>> loader) {

            }
        });
    }

    private void loadLists() {
        getLoaderManager().initLoader(ListLoader.LOADER_LESSONS, null, new ListLoader());
        getLoaderManager().initLoader(ListLoader.LOADER_AUDITORIES, null, new ListLoader());
        getLoaderManager().initLoader(ListLoader.LOADER_TIMES, null, new ListLoader());
        getLoaderManager().initLoader(ListLoader.LOADER_TEACHERS, null, new ListLoader());
        getLoaderManager().initLoader(ListLoader.LOADER_LESSON_TYPES, null, new ListLoader());
    }

    @OnClick(R.id.btn_add_type_lesson)
    void showAddLessonTypeDialog() {
        showAddDialog(getString(R.string.type_lesson));
    }

    @OnClick(R.id.btn_add_time)
    void showAddTimeDialog() {
        DialogFragment newFragment = AddTimeDialogFragment.newInstance();
        newFragment.show(getFragmentManager(), TAG_DIALOG);
    }

    private void showAddDialog(String arg) {
        DialogFragment newFragment = AddValueDialogFragment.newInstance(arg);
        newFragment.show(getFragmentManager(), TAG_DIALOG);
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
                    return new OrmLiteQueryForAllOrderByLoader<>(getBaseContext(),
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
                    android.R.layout.simple_dropdown_item_1line, (List<String>) data);
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
