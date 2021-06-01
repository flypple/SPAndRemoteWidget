package com.flypple.spandremotewidget.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.flypple.spandremotewidget.Constants;
import com.flypple.spandremotewidget.Pair;
import com.flypple.spandremotewidget.R;
import com.flypple.spandremotewidget.SPDataAdapter;
import com.flypple.spandremotewidget.sp.MPSharedPreferences;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by qiqinglin
 * 2021/5/23
 * flypple20088@163.com
 */
public class MPSPActivity extends AppCompatActivity {

    private static final String TYPE_INT = "int";
    private static final String TYPE_LONG = "long";
    private static final String TYPE_FLOAT = "float";
    private static final String TYPE_BOOLEAN = "boolean";
    private static final String TYPE_STRING = "String";
    private static final String TYPE_STRING_SET = "StringSet";

    private EditText etKey;
    private EditText etValue;
    private Button btnPut;
    private Button btnGetAll;
    private Button btnClear;
    private Button btnRead;
    private TextView tvDataType;
    private RecyclerView rvSPDataContainer;

    private List<Pair> data;
    private SPDataAdapter spDataAdapter;

    MPSharedPreferences sharedPreferences;

    private String dataType = TYPE_STRING;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mpsp);
        initView();
        initAction();

    }

    private void initAction() {

        btnPut.setOnClickListener(this::putData);
        btnGetAll.setOnClickListener(this::getAllData);
        btnClear.setOnClickListener(this::clearSP);
        btnRead.setOnClickListener(this::readData);
        tvDataType.setOnClickListener(this::showPopupMenu);

        data = new ArrayList<>();
        spDataAdapter = new SPDataAdapter(this, data);
        rvSPDataContainer.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvSPDataContainer.setAdapter(spDataAdapter);

        sharedPreferences = new MPSharedPreferences(this, Constants.SP_NAME);
    }

    private void initView() {
        etKey = findViewById(R.id.et_key);
        etValue = findViewById(R.id.et_value);
        btnPut = findViewById(R.id.btn_put);
        btnGetAll = findViewById(R.id.btn_get_all);
        btnClear = findViewById(R.id.btn_clear);
        btnRead = findViewById(R.id.btn_read);
        tvDataType = findViewById(R.id.tv_data_type);
        rvSPDataContainer = findViewById(R.id.rv_sp_data_container);
    }

    private void showPopupMenu(View view) {
        // View当前PopupMenu显示的相对View的位置
        PopupMenu popupMenu = new PopupMenu(this, view);
        // menu布局
        popupMenu.getMenuInflater().inflate(R.menu.type_menu, popupMenu.getMenu());
        // menu的item点击事件
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                String title = item.getTitle().toString();
                dataType = title;
                tvDataType.setText("类型：" + title);
                return false;
            }
        });
        // PopupMenu关闭事件
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
//                Toast.makeText(getApplicationContext(), "关闭PopupMenu", Toast.LENGTH_SHORT).show();
            }
        });

        popupMenu.show();
    }

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

        try {
            switch (dataType) {
                case TYPE_INT:
                    putInt(keyString, valueString);
                    break;
                case TYPE_LONG:
                    putLong(keyString, valueString);
                    break;
                case TYPE_FLOAT:
                    putFloat(keyString, valueString);
                    break;
                case TYPE_BOOLEAN:
                    putBoolean(keyString, valueString);
                    break;
                case TYPE_STRING:
                    putString(keyString, valueString);
                    break;
                case TYPE_STRING_SET:
                    putStringSet(keyString, valueString);
                    break;
            }
            getAllData(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

    protected void clearSP(View view) {
        sharedPreferences.clear();
        data.clear();
        spDataAdapter.notifyDataSetChanged();
    }

    protected void readData(View view) {
        Editable textKey = etKey.getText();
        if (textKey == null) {
            return;
        }

        String keyString = textKey.toString().trim();
        if (TextUtils.isEmpty(keyString)) {
            return;
        }

        try {
            String value = "";
            switch (dataType) {
                case TYPE_INT:
                    value = String.valueOf(sharedPreferences.getInt(keyString, -1));
                    break;
                case TYPE_LONG:
                    value = String.valueOf(sharedPreferences.getLong(keyString, -1));
                    break;
                case TYPE_FLOAT:
                    value = String.valueOf(sharedPreferences.getFloat(keyString, -1.0f));
                    break;
                case TYPE_BOOLEAN:
                    value = String.valueOf(sharedPreferences.getBoolean(keyString, false));
                    break;
                case TYPE_STRING:
                    value = String.valueOf(sharedPreferences.getString(keyString, "值不存在"));
                    break;
                case TYPE_STRING_SET:
                    HashSet<String> strings = new HashSet<>();
                    strings.add("值不存在");
                    value = String.valueOf(sharedPreferences.getStringSet(keyString, strings));
                    break;
            }
            Toast.makeText(this, value, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void putInt(String key, String value) {
        int intValue = Integer.parseInt(value);
        sharedPreferences.putInt(key, intValue);
    }

    private void putLong(String key, String value) {
        long longValue = Long.parseLong(value);
        sharedPreferences.putLong(key, longValue);
    }

    private void putFloat(String key, String value) {
        float floatValue = Float.parseFloat(value);
        sharedPreferences.putFloat(key, floatValue);
    }

    private void putBoolean(String key, String value) {
        boolean booleanValue = Boolean.parseBoolean(value);
        sharedPreferences.putBoolean(key, booleanValue);
    }

    private void putString(String key, String value) {
        sharedPreferences.putString(key, value);
    }

    private void putStringSet(String key, String value) {
        String[] split = value.split("\\|");
        HashSet<String> set = new HashSet<>();
        for (String str : split) {
            set.add(str);
        }
        sharedPreferences.putStringSet(key, set);
    }
}
