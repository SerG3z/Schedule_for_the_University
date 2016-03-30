package com.sample.drawer.fragments.schedule;

import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.sample.drawer.R;
import com.sample.drawer.database.Classroom;
import com.sample.drawer.database.HelperFactory;
import com.sample.drawer.database.PeriodTime;
import com.sample.drawer.database.PeriodType;
import com.sample.drawer.database.Subject;
import com.sample.drawer.database.Teacher;

import java.sql.SQLException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
//TODO: доделать диалог добавления времени
public class AddTimeDialogFragment extends DialogFragment {

    @Bind(R.id.timePicker) TimePicker timePicker;
    @Bind(R.id.cancel_btn) Button cancelBtn;
    @Bind(R.id.ok_btn) Button okBtn;
    @Bind(R.id.time_tv) TextView timeTv;

    private String beginTime = "";
    private String endTime = "";

    private int h, m;

    public static AddTimeDialogFragment newInstance() {
        return new AddTimeDialogFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_time_dialog,
                container, false);
        getDialog().setTitle(R.string.lesson_begin);
        ButterKnife.bind(this, rootView);
        timePicker.setIs24HourView(true);
        cancelBtn.setText(R.string.cancel);
        okBtn.setText(R.string.proceed);
        getDialog().setTitle(R.string.lesson_begin);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                h = hourOfDay;
                m = minute;
            }
        });
        return rootView;
    }

    @OnClick(R.id.ok_btn)
    public void onSubmit(){
        if (beginTime.isEmpty()){
            beginTime = String.format("%02d:%02d",h,m);
            timeTv.setText(beginTime);
            getDialog().setTitle(R.string.lesson_end);
            cancelBtn.setText(R.string.back);
        }
        else if (endTime.isEmpty()){
            endTime = String.format("%02d:%02d",h,m);
            if (endTime.compareTo(beginTime) <= 0){
                Toast.makeText(getActivity(), R.string.wrong_interval, Toast.LENGTH_SHORT).show();
                endTime = "";
                return;
            }
            timeTv.setText(beginTime+"-"+endTime);
            getDialog().setTitle(R.string.accept);
            okBtn.setText(R.string.ok);
        }
        else {
            try {
                HelperFactory.getHelper().getPeriodTimeDAO().create(new PeriodTime(beginTime,endTime));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            getDialog().dismiss();
        }
    }

    @OnClick(R.id.cancel_btn)
    public void onCancel(){
        if (beginTime.isEmpty()){
            getDialog().dismiss();
        } else if (endTime.isEmpty()){
            cancelBtn.setText(R.string.cancel);
            okBtn.setText(R.string.proceed);
            beginTime = "";
            timeTv.setText(beginTime);
            getDialog().setTitle(R.string.lesson_begin);
        } else {
            timeTv.setText(beginTime);
            getDialog().setTitle(R.string.lesson_end);
            endTime = "";
            cancelBtn.setText(R.string.back);
            okBtn.setText(R.string.proceed);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
