package com.flypple.spandremotewidget.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.flypple.spandremotewidget.R;
import com.flypple.spandremotewidget.fragment.Main2MainSPFragment;

/**
 * Created by qiqinglin
 * 2021/5/20
 * flypple20088@163.com
 */
public class Main2MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_2_main);

        Main2MainSPFragment fragment1 = Main2MainSPFragment.newInstance();
        Main2MainSPFragment fragment2 = Main2MainSPFragment.newInstance();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fl_main_fragment_container, fragment1);
        transaction.replace(R.id.fl_remote_fragment_container, fragment2);
        transaction.commit();
    }
}
