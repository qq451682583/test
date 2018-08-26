package com.example.administrator.myapplication.activity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.LruCache;
import android.view.View;

import com.example.administrator.myapplication.R;
import com.zw.yzk.component.statistics.statistics.ClickStatistics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Vector;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;


public class MainActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        test();

    }

    private void test() {
        findViewById(R.id.test).setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, Main2Activity.class)));

    }


}
