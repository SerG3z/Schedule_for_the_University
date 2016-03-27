package com.sample.drawer.fragments.schedule;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sample.drawer.R;


public class AddValueDialogFragment extends Fragment {

    private static final String ARG_VALUE_TYPE = "value_type";

    private int valueType;

    public static AddValueDialogFragment newInstance(int valueType) {
        AddValueDialogFragment fragment = new AddValueDialogFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_VALUE_TYPE, valueType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            valueType = getArguments().getInt(ARG_VALUE_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_value_dialog, container, false);
    }

}
