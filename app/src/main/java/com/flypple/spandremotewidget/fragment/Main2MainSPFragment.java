package com.flypple.spandremotewidget.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;

import com.flypple.spandremotewidget.Constants;
import com.flypple.spandremotewidget.Pair;

import java.util.Map;
import java.util.Set;

/**
 * Created by qiqinglin
 * 2021/5/19
 * flypple20088@163.com
 */
public class Main2MainSPFragment extends BaseSPFragment {

    private SharedPreferences sharedPreferences;

    public static Main2MainSPFragment newInstance() {
        Bundle args = new Bundle();
        Main2MainSPFragment fragment = new Main2MainSPFragment();
        fragment.setArguments(args);
        return fragment;
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

        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(keyString, valueString);
        editor.apply();
        data.add(new Pair(keyString, valueString));
        spDataAdapter.notifyDataSetChanged();
    }

    @Override
    protected void getAllData(View view) {
        data.clear();
        Map<String, ?> all = getSharedPreferences().getAll();
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
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.clear();
        editor.apply();
        data.clear();
        spDataAdapter.notifyDataSetChanged();
    }

    private SharedPreferences getSharedPreferences() {
        if (sharedPreferences == null) {
            sharedPreferences = getContext().getSharedPreferences(Constants.SP_NAME, Context.MODE_MULTI_PROCESS);
        }
        return sharedPreferences;
    }
}
