package com.flypple.spandremotewidget.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.flypple.spandremotewidget.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_main_to_main).setOnClickListener(v -> {
            startActivity(new Intent(this, Main2MainActivity.class));
        });
        findViewById(R.id.btn_main_to_remote).setOnClickListener(v -> {
            startActivity(new Intent(this, Main2RemoteActivity.class));
        });
        findViewById(R.id.btn_multi_sp_test).setOnClickListener(v -> {
            startActivity(new Intent(this, MPSPActivity.class));
        });
    }
}