package com.example.administrator.myapplication.activity;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.fragment.Fragment5;

public class Main5Activity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        initView();
    }

    private void initView() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, new Fragment5())
                .commit();
    }
}
