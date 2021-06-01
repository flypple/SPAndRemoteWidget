package com.flypple.spandremotewidget.remote;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;

import com.flypple.spandremotewidget.Constants;
import com.flypple.spandremotewidget.Pair;
import com.flypple.spandremotewidget.main.MainSPReceiver;
import com.flypple.spandremotewidget.sp.MPSharedPreferences;

import java.util.Map;
import java.util.Set;

/**
 * Created by qiqinglin
 * 2021/5/20
 * flypple20088@163.com
 */
public class RemoteSPReceiver extends BroadcastReceiver {

    public static final String ACTION_PUT = "com.flypple.spandremotewidget.remote.sp_receive_put";
    public static final String ACTION_GET_ALL = "com.flypple.spandremotewidget.remote.sp_receive_get_all";
    public static final String ACTION_CLEAR = "com.flypple.spandremotewidget.remote.sp_receive_clear";

    public Context context;
//    public SharedPreferences sharedPreferences;
    public MPSharedPreferences sharedPreferences;

    public RemoteSPReceiver(Context context) {
        this.context = context;
        sharedPreferences = new MPSharedPreferences(context, Constants.SP_NAME);
    }

    /*private SharedPreferences sharedPreferences {
        return context.getSharedPreferences(Constants.SP_NAME, Context.MODE_MULTI_PROCESS);
    }*/

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (ACTION_PUT.equals(action)) {
            putData(intent);
        } else if (ACTION_GET_ALL.equals(action)) {
            getAllData(intent);
        } else if (ACTION_CLEAR.equals(action)) {
            clearData(intent);
        }
    }

    private void putData(Intent intent) {
//        SharedPreferences.Editor editor = sharedPreferences.edit();
        boolean hasPutData = false;
        Bundle extras = intent.getBundleExtra(Constants.INTENT_KEY_SP_DATA);
        if (extras == null) {
            return;
        }
        Set<String> keySet = extras.keySet();
        for (String key : keySet) {
            if (TextUtils.isEmpty(key) || !extras.containsKey(key)) {
                continue;
            }
            Object obj = extras.get(key);
            try {
                String value = String.valueOf(obj);
                sharedPreferences.putString(key, value);
                hasPutData = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (hasPutData) {
            // 发广播通知主进程更新UI
            Intent newIntent = new Intent(MainSPReceiver.ACTION_ON_PUT);
            newIntent.putExtra(Constants.INTENT_KEY_SP_DATA, extras);
            context.sendBroadcast(newIntent);
        }
    }

    private void getAllData(Intent intent) {
        Map<String, ?> all = sharedPreferences.getAll();
        Bundle bundle = new Bundle();
        Set<String> keySet = all.keySet();
        for (String key : keySet) {
            if (TextUtils.isEmpty(key) || !all.containsKey(key)) {
                continue;
            }
            Object obj = all.get(key);
            try {
                String value = String.valueOf(obj);
                bundle.putString(key, value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 发广播通知主进程更新UI
        Intent newIntent = new Intent(MainSPReceiver.ACTION_ON_GET_ALL);
        newIntent.putExtra(Constants.INTENT_KEY_SP_DATA, bundle);
        context.sendBroadcast(newIntent);
    }

    private void clearData(Intent intent) {
        sharedPreferences.clear();
        // 发广播通知主进程更新UI
        Intent newIntent = new Intent(MainSPReceiver.ACTION_ON_CLEAR);
        context.sendBroadcast(newIntent);
    }
}
