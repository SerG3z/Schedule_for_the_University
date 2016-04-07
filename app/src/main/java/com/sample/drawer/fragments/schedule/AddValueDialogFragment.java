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

import com.sample.drawer.R;
import com.sample.drawer.database.Classroom;
import com.sample.drawer.database.HelperFactory;
import com.sample.drawer.database.PeriodType;
import com.sample.drawer.database.Subject;
import com.sample.drawer.database.Teacher;

import java.sql.SQLException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddValueDialogFragment extends DialogFragment {

    @Bind(R.id.newValue_et) EditText newValueEt;

    private static final String ARG_VALUE_TYPE = "value_type";


    private String valueType;

    public static AddValueDialogFragment newInstance(String valueType) {
        AddValueDialogFragment fragment = new AddValueDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_VALUE_TYPE, valueType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            valueType = getArguments().getString(ARG_VALUE_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_value_dialog,
                container, false);
        getDialog().setTitle(R.string.add);
        ButterKnife.bind(this, rootView);
        newValueEt.setHint(valueType);
        return rootView;
    }

    @OnClick(R.id.ok_btn)
    public void onSubmit(){
        String s = newValueEt.getText().toString();
        if (s.isEmpty()){
            return;
        }
        try {
            if (valueType.compareTo(getString(R.string.name_lesson))==0){
                    HelperFactory.getHelper().getSubjectDAO().create(new Subject(s));
            } else if (valueType.compareTo(getString(R.string.fio_teacher))==0){
                HelperFactory.getHelper().getTeacherDAO().create(new Teacher(s));
            } else if (valueType.compareTo(getString(R.string.number_auditory))==0){
                HelperFactory.getHelper().getClassroomDAO().create(new Classroom(s));
            } else if (valueType.compareTo(getString(R.string.type_lesson))==0){
                HelperFactory.getHelper().getPeriodTypeDAO().create(new PeriodType(s));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        getDialog().dismiss();
    }

    @OnClick(R.id.cancel_btn)
    public void onCancel(){
        getDialog().dismiss();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
