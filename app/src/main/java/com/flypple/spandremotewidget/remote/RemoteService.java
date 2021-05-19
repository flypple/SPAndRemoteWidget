package com.flypple.spandremotewidget.remote;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

/**
 * Created by qiqinglin
 * 2021/5/19
 * flypple20088@163.com
 */
public class RemoteService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
