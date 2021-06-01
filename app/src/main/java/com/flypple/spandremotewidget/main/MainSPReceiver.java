package com.flypple.spandremotewidget.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.flypple.spandremotewidget.Constants;
import com.flypple.spandremotewidget.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by qiqinglien
 * 2021/5/20
 * flypple20088@163.com
 */
public class MainSPReceiver extends BroadcastReceiver {

    public static final String ACTION_ON_PUT = "com.flypple.spandremotewidget.main.sp_receive_on_put";
    public static final String ACTION_ON_GET_ALL = "com.flypple.spandremotewidget.main.sp_receive_on_get_all";
    public static final String ACTION_ON_CLEAR = "com.flypple.spandremotewidget.main.sp_receive_on_clear";

    private CallBack callBack;

    public MainSPReceiver(CallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (ACTION_ON_PUT.equals(action)) {
            onPutData(intent);
        } else if (ACTION_ON_GET_ALL.equals(action)) {
            onGetAllData(intent);
        } else if (ACTION_ON_CLEAR.equals(action)) {
            onClearData(intent);
        }
    }

    private void onPutData(Intent intent) {
        Bundle bundle = intent.getBundleExtra(Constants.INTENT_KEY_SP_DATA);
        if (bundle == null) {
            return;
        }

        List<Pair> pairs = bundlePairList(bundle);
        callBack.onPutData(pairs);
    }

    private List<Pair> bundlePairList(Bundle bundle) {
        ArrayList<Pair> pairs = new ArrayList<>();
        Set<String> keySet = bundle.keySet();
        for (String key : keySet) {
            if (TextUtils.isEmpty(key) || !bundle.containsKey(key)) {
                continue;
            }
            Object obj = bundle.get(key);
            try {
                String value = String.valueOf(obj);
                Pair pair = new Pair(key, value);
                pairs.add(pair);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return pairs;
    }

    private void onGetAllData(Intent intent) {
        Bundle bundle = intent.getBundleExtra(Constants.INTENT_KEY_SP_DATA);
        if (bundle == null) {
            return;
        }

        List<Pair> pairs = bundlePairList(bundle);
        callBack.onGetAllSPData(pairs);
    }

    private void onClearData(Intent intent) {
        callBack.onClearData();
    }

    public interface CallBack {
        void onPutData(List<Pair> pairs);
        void onGetAllSPData(List<Pair> pairs);
        void onClearData();
    }
}
