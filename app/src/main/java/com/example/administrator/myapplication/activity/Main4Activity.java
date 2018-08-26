package com.example.administrator.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.administrator.myapplication.R;
import com.zw.yzk.component.statistics.config.StatisticsExtras;

import java.util.Map;

public class Main4Activity extends BaseActivity implements StatisticsExtras {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main4Activity.this, Main5Activity.class));
            }
        });
    }

    @Override
    public Map<String, String> getExtraInfo() {
        return null;
    }
}
