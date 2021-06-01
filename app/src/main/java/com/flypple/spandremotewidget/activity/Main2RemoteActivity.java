package com.flypple.spandremotewidget.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.flypple.spandremotewidget.R;
import com.flypple.spandremotewidget.fragment.MainSPFragment;
import com.flypple.spandremotewidget.fragment.RemoteSPFragment;
import com.flypple.spandremotewidget.remote.RemoteService;

/**
 * Created by qiqinglin
 * 2021/5/20
 * flypple20088@163.com
 */
public class Main2RemoteActivity extends AppCompatActivity {

    private Button btnServiceStart;
    private Button btnServiceEnd;
    private TextView tvServiceState;

    private boolean serviceAlive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_and_remote);

        btnServiceStart = findViewById(R.id.btn_service_start);
        btnServiceEnd = findViewById(R.id.btn_service_end);
        tvServiceState = findViewById(R.id.tv_service_state);

        btnServiceStart.setOnClickListener(this::startService);
        btnServiceEnd.setOnClickListener(this::stopService);

        MainSPFragment mainSPFragment = MainSPFragment.newInstance();
        RemoteSPFragment remoteSPFragment = RemoteSPFragment.newInstance();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fl_main_fragment_container, mainSPFragment);
        transaction.replace(R.id.fl_remote_fragment_container, remoteSPFragment);
        transaction.commit();
    }

    private void updateServiceState() {
        if (serviceAlive) {
            tvServiceState.setText("服务正在运行");
        } else {
            tvServiceState.setText("服务停止运行");
        }
    }

    private void startService(View view) {
        startService();
        updateServiceState();
    }

    private void startService() {
        Intent intent = new Intent(this, RemoteService.class);
        startService(intent);
        serviceAlive = true;
    }

    private void stopService(View view) {
        stopService();
        updateServiceState();
    }

    private void stopService() {
        serviceAlive = false;
        Intent intent = new Intent(this, RemoteService.class);
        stopService(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService();
    }
}
