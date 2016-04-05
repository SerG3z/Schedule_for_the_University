package com.sample.drawer.activity;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OrmLitePreparedQueryLoader;
import com.sample.drawer.R;
import com.sample.drawer.database.Day;
import com.sample.drawer.database.HelperFactory;
import com.sample.drawer.database.Subject;
import com.sample.drawer.database.Task;
import com.sample.drawer.database.dao.DayDAO;
import com.sample.drawer.database.loader.OrmLiteQueryForAllOrderByLoader;
import com.sample.drawer.fragments.DateDialog;
import com.sample.drawer.fragments.schedule.AddValueDialogFragment;
import com.sample.drawer.utils.TimeHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

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
    private static final String TASK_ID_INTENT_KEY = "task_id";
    private static final String DATAPICKER_KEY = "datePicker";
    private static final String TAG_LESSON_ADD_DIALOG = "add_lesson_dialog";
    private static final int LOADER_LESSONS = 1;
    private static final int LOADER_DAY = 2;

    private List<Subject> lessonList;

    @Bind(R.id.add_task_info)
    EditText addTaskInfo;
    @Bind(R.id.add_task_deadline)
    TextView addTaskDeadline;
    @Bind(R.id.toolbar_add_new_task)
    Toolbar toolbar;
    @Bind(R.id.name_lesson)
    Spinner nameLesson;
    @Bind(R.id.set_deadline_cb)
    CheckBox setDeadline;

    DateDialog newFragment;

    private int taskID;
    private Calendar date;
    private String lessonName;
    private boolean created;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_task_show);
        ButterKnife.bind(this);

        date = Calendar.getInstance();
        date = new GregorianCalendar(date.get(Calendar.YEAR),
                date.get(Calendar.MONTH),date.get(Calendar.DATE));

        initializationIntent(getIntent());

        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        loadLessons();
        created = false;
        setDeadline.setChecked(false);
    }

    public static Intent newIntent(Context context,int taskID, String lesson, String deadline, String info) {
        Intent intent = new Intent(context, NewTaskActivity.class);
        intent.putExtra(LESSON_INTENT_KEY, lesson);
        intent.putExtra(DEADLINE_INTENT_KEY, deadline);
        intent.putExtra(INFO_INTENT_KEY, info);
        intent.putExtra(TASK_ID_INTENT_KEY, taskID);
        return intent;
    }

    private void initializationIntent(Intent intent) {
        if (intent != null) {
            addTaskDeadline.setText(intent.getStringExtra(DEADLINE_INTENT_KEY));
            addTaskInfo.setText(intent.getStringExtra(INFO_INTENT_KEY));
            String d = intent.getStringExtra(DEADLINE_INTENT_KEY);
            if (d != null){
                date = TimeHelper.dateFromString(d);
            }
            lessonName = intent.getStringExtra(LESSON_INTENT_KEY);
            taskID = intent.getIntExtra(TASK_ID_INTENT_KEY,0);
        }
    }

    @OnClick(R.id.add_task_deadline)
    public void clickTextViewDeadline() {
        newFragment = new DateDialog();
        newFragment.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(final DatePicker datePicker, final int year, final int monthOfYear, final int dayOfMonth) {
                String[] mounth = getResources().getStringArray(R.array.mount_list);
                if ((new GregorianCalendar(year,monthOfYear,dayOfMonth)).compareTo(Calendar.getInstance()) < 0)
                {
                    Toast.makeText(getBaseContext(),
                            R.string.no_task_in_the_past,
                            Toast.LENGTH_SHORT).show();
                    setDeadline.setChecked(false);
                }
                else{
                    date = new GregorianCalendar(year,monthOfYear,dayOfMonth);
                    addTaskDeadline.setText(getString(R.string.format_date, dayOfMonth, mounth[monthOfYear], year));
                    setDeadline.setChecked(true);
                }
            }
        });
        newFragment.show(getFragmentManager(), DATAPICKER_KEY);
    }

    private void loadLessons(){
        getLoaderManager().initLoader(LOADER_LESSONS, null, new LoaderManager.LoaderCallbacks<List<Subject>>() {
            @Override
            public Loader<List<Subject>> onCreateLoader(int id, Bundle args) {
                return new OrmLiteQueryForAllOrderByLoader<>(getBaseContext(), HelperFactory.
                        getHelper().getSubjectDAO(), Subject.FIELD_SUBJECT);
            }

            @Override
            public void onLoadFinished(Loader<List<Subject>> loader, List<Subject> data) {
                if (data != null) {
                    lessonList = data;
                    int selected = -1, i = 0;
                    List<String> lessonNames = new ArrayList<String>();
                    for (Subject s : data ) {
                        lessonNames.add(s.toString());
                        if (lessonName != null && s.getSubject().compareTo(lessonName) == 0){
                            selected = i;
                        }
                        i++;
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getBaseContext(),
                            android.R.layout.simple_spinner_dropdown_item, lessonNames);
                    nameLesson.setAdapter(adapter);
                    if (selected != -1){
                        nameLesson.setSelection(selected,true);
                    }
                }
            }

            @Override
            public void onLoaderReset(Loader<List<Subject>> loader) {
            }
        });
    }

    @OnClick(R.id.add_task_button_save)
    public void clickToButtonSave() {
        getLoaderManager().initLoader(LOADER_DAY, null, new LoaderManager.LoaderCallbacks<List<Day>>() {
            @Override
            public Loader<List<Day>> onCreateLoader(int id, Bundle args) {
                DayDAO dao = HelperFactory.getHelper().getDayDAO();
                try {
                    return new OrmLitePreparedQueryLoader<>(getBaseContext(),dao,
                            dao.getDayByDate(date.getTime()));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public void onLoadFinished(Loader<List<Day>> loader, List<Day> data) {
                Day day = null;
                if (setDeadline.isChecked()){
                    if (data != null && data.size() > 0) {
                        day = data.get(0);
                    } else {
                        day = new Day(date.getTime(),date.get(Calendar.DAY_OF_WEEK));
                        try {
                            HelperFactory.getHelper().getDayDAO().create(day);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
                Task task = new Task(addTaskInfo.getText().toString(), day,
                        lessonList.get(nameLesson.getSelectedItemPosition()));
                if (taskID == 0 && !created){ // добавление
                    try {
                        created = true;
                        HelperFactory.getHelper().getTaskDAO().create(task);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                else { //редактирование
                    task.setId(taskID);
                    try {
                        HelperFactory.getHelper().getTaskDAO().update(task);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                setResult(RESULT_OK, new Intent());
                finish();
            }

            @Override
            public void onLoaderReset(Loader<List<Day>> loader) {

            }
        });
    }

    @OnClick(R.id.btn_add_name_lesson)
    public void addLesson(){
        DialogFragment newFragment = AddValueDialogFragment.
                newInstance(getString(R.string.name_lesson));
        newFragment.show(getFragmentManager(), TAG_LESSON_ADD_DIALOG);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
