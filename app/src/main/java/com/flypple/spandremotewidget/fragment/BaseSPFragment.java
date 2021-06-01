package com.flypple.spandremotewidget.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.flypple.spandremotewidget.Pair;
import com.flypple.spandremotewidget.R;
import com.flypple.spandremotewidget.SPDataAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qiqinglin
 * 2021/5/19
 * flypple20088@163.com
 */
public abstract class BaseSPFragment extends Fragment {
    protected EditText etKey;
    protected EditText etValue;
    protected Button btnPut;
    protected Button btnGetAll;
    protected Button btnClear;
    protected RecyclerView rvSPDataContainer;

//    protected SharedPreferences sharedPreferences;
    protected List<Pair> data;
    protected SPDataAdapter spDataAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sp, container, false);
        initView(view);
        initAction();
        return view;
    }

    private void initAction() {

        btnPut.setOnClickListener(this::putData);
        btnGetAll.setOnClickListener(this::getAllData);
        btnClear.setOnClickListener(this::clearSP);

        data = new ArrayList<>();
        spDataAdapter = new SPDataAdapter(getContext(), data);
        rvSPDataContainer.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rvSPDataContainer.setAdapter(spDataAdapter);
    }

    private void initView(View view) {
        etKey = view.findViewById(R.id.et_key);
        etValue = view.findViewById(R.id.et_value);
        btnPut = view.findViewById(R.id.btn_put);
        btnGetAll = view.findViewById(R.id.btn_get_all);
        btnClear = view.findViewById(R.id.btn_clear);
        rvSPDataContainer = view.findViewById(R.id.rv_sp_data_container);
    }

    protected abstract void putData(View view);

    protected abstract void getAllData(View view);

    protected abstract void clearSP(View view);
}
