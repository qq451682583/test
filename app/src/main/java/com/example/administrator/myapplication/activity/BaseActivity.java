package com.example.administrator.myapplication.activity;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.zw.yzk.component.statistics.statistics.ClickStatistics;
import com.zw.yzk.component.statistics.utils.ViewUtils;


@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.e("zhanwei", "s time: " + System.currentTimeMillis());
        getAllChildViews(getWindow().getDecorView());
        Log.e("zhanwei", "b time: " + System.currentTimeMillis());
    }

    private void getAllChildViews(View view) {
        ViewUtils.setTagToViewTree(view);
    }

    public void aaa(View v) {
        ClickStatistics clickStatistics = new ClickStatistics(v);
        clickStatistics.clickEnd();
    }

}
