package com.flypple.spandremotewidget;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Created by qiqinglin
 * 2021/5/19
 * flypple20088@163.com
 */
public class SPFragment extends Fragment {
    public static final String SP_FRAGMENT_TYPE = "SP_FRAGMENT_TYPE";

    public static SPFragment newInstance(String type) {
        Bundle args = new Bundle();
        args.putString(SP_FRAGMENT_TYPE, type);
        SPFragment fragment = new SPFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sp, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {

    }
}
