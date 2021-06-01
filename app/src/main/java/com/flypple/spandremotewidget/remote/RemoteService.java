package com.flypple.spandremotewidget.remote;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import androidx.annotation.Nullable;

/**
 * Created by qiqinglin
 * 2021/5/19
 * flypple20088@163.com
 */
public class RemoteService extends Service {

    private RemoteSPReceiver remoteSPReceiver;

    @Override
    public void onCreate() {
        super.onCreate();

        if (remoteSPReceiver == null) {
            remoteSPReceiver = new RemoteSPReceiver(this);
            IntentFilter intentFilter = new IntentFilter(RemoteSPReceiver.ACTION_PUT);
            intentFilter.addAction(RemoteSPReceiver.ACTION_GET_ALL);
            intentFilter.addAction(RemoteSPReceiver.ACTION_CLEAR);
            registerReceiver(remoteSPReceiver ,intentFilter);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (remoteSPReceiver != null) {
            unregisterReceiver(remoteSPReceiver);
            remoteSPReceiver = null;
        }
    }
}
