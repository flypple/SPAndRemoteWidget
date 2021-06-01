package com.flypple.spandremotewidget.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.flypple.spandremotewidget.Constants;
import com.flypple.spandremotewidget.Pair;
import com.flypple.spandremotewidget.sp.MPSharedPreferences;

import java.util.Map;
import java.util.Set;

/**
 * Created by qiqinglin
 * 2021/5/19
 * flypple20088@163.com
 */
public class MainSPFragment extends BaseSPFragment {

    MPSharedPreferences sharedPreferences;

    public static MainSPFragment newInstance() {
        Bundle args = new Bundle();
        MainSPFragment fragment = new MainSPFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        sharedPreferences = new MPSharedPreferences(getContext(), Constants.SP_NAME);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void putData(View view) {
        Editable textKey = etKey.getText();
        Editable textValue = etValue.getText();
        if (textKey == null || textValue == null) {
            return;
        }

        String keyString = textKey.toString().trim();
        String valueString = textValue.toString().trim();
        if (TextUtils.isEmpty(keyString) || TextUtils.isEmpty(valueString)) {
            return;
        }

//        SharedPreferences.Editor editor = getSharedPreferences().edit();
        sharedPreferences.putString(keyString, valueString);
//        editor.apply();
        data.add(new Pair(keyString, valueString));
        spDataAdapter.notifyDataSetChanged();
    }

    @Override
    protected void getAllData(View view) {
        data.clear();
        Map<String, ?> all = sharedPreferences.getAll();
        Set<String> keySet = all.keySet();
        for (String key : keySet) {
            if (TextUtils.isEmpty(key) || !all.containsKey(key)) {
                continue;
            }
            Object obj = all.get(key);
            try {
                String value = String.valueOf(obj);
                data.add(new Pair(key, value));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        spDataAdapter.notifyDataSetChanged();
    }

    @Override
    protected void clearSP(View view) {
//        SharedPreferences.Editor editor = getSharedPreferences().edit();
//        editor.clear();
//        editor.apply();
        sharedPreferences.clear();
        data.clear();
        spDataAdapter.notifyDataSetChanged();
    }

    /*private SharedPreferences getSharedPreferences() {
        return getContext().getSharedPreferences(Constants.SP_NAME, Context.MODE_MULTI_PROCESS);
    }*/
}
