package com.flypple.spandremotewidget.fragment;

import android.content.Intent;
import android.content.IntentFilter;
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
import com.flypple.spandremotewidget.main.MainSPReceiver;
import com.flypple.spandremotewidget.remote.RemoteSPReceiver;

import java.util.List;

/**
 * Created by qiqinglin
 * 2021/5/19
 * flypple20088@163.com
 */
public class RemoteSPFragment extends BaseSPFragment {

    private MainSPReceiver mainSPReceiver;

    public static RemoteSPFragment newInstance() {
        Bundle args = new Bundle();
        RemoteSPFragment fragment = new RemoteSPFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initReceiver();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void initReceiver() {
        if (mainSPReceiver == null) {
            mainSPReceiver = new MainSPReceiver(new MainSPReceiver.CallBack() {
                @Override
                public void onPutData(List<Pair> pairs) {
                    data.addAll(pairs);
                    spDataAdapter.notifyDataSetChanged();
                }

                @Override
                public void onGetAllSPData(List<Pair> pairs) {
                    data.clear();
                    data.addAll(pairs);
                    spDataAdapter.notifyDataSetChanged();
                }

                @Override
                public void onClearData() {
                    data.clear();
                    spDataAdapter.notifyDataSetChanged();
                }
            });
            IntentFilter intentFilter = new IntentFilter(MainSPReceiver.ACTION_ON_PUT);
            intentFilter.addAction(MainSPReceiver.ACTION_ON_GET_ALL);
            intentFilter.addAction(MainSPReceiver.ACTION_ON_CLEAR);
            getContext().registerReceiver(mainSPReceiver, intentFilter);
        }

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

        // 发广播到远端进程存储SP数据
        Intent intent = new Intent(RemoteSPReceiver.ACTION_PUT);
        Bundle bundle = new Bundle();
        bundle.putString(keyString, valueString);
        intent.putExtra(Constants.INTENT_KEY_SP_DATA, bundle);
        getContext().sendBroadcast(intent);
    }

    @Override
    protected void getAllData(View view) {
        // 发广播到远端进程请求所有SP数据
        Intent intent = new Intent(RemoteSPReceiver.ACTION_GET_ALL);
        getContext().sendBroadcast(intent);
    }

    @Override
    protected void clearSP(View view) {
        // 发广播到远端进程请求所有SP数据
        Intent intent = new Intent(RemoteSPReceiver.ACTION_CLEAR);
        getContext().sendBroadcast(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mainSPReceiver != null) {
            getContext().unregisterReceiver(mainSPReceiver);
            mainSPReceiver = null;
        }
    }
}
