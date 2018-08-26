package com.example.administrator.myapplication.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.fragment.Fragment1;
import com.example.administrator.myapplication.fragment.Fragment2;
import com.example.administrator.myapplication.fragment.Fragment3;
import com.example.administrator.myapplication.fragment.Fragment4;

public class Main2Activity extends BaseActivity {

    private Fragment1 fragment1;
    private Fragment2 fragment2;
    private Fragment3 fragment3;
    private Fragment4 fragment4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        initView();

    }

    private void initView() {
        Listener listener = new Listener();
        findViewById(R.id.page1).setOnClickListener(listener);
        findViewById(R.id.page2).setOnClickListener(listener);
        findViewById(R.id.page3).setOnClickListener(listener);
        findViewById(R.id.page4).setOnClickListener(listener);

        findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main2Activity.this, Main3Activity.class));
            }
        });
    }

    private void changeFragment(int index) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hiedeAll(transaction);
        switch (index) {
            case 1:
                if(fragment1 == null) {
                    fragment1 = new Fragment1();
                    transaction.add(R.id.container, fragment1);
                }
                transaction.show(fragment1);
                break;
            case 2:
                if(fragment2 == null) {
                    fragment2 = new Fragment2();
                    transaction.add(R.id.container, fragment2);
                }
                transaction.show(fragment2);
                break;
            case 3:
                if(fragment3 == null) {
                    fragment3 = new Fragment3();
                    transaction.add(R.id.container, fragment3);
                }
                transaction.show(fragment3);
                break;
            case 4:
                if(fragment4 == null) {
                    fragment4 = new Fragment4();
                    transaction.add(R.id.container, fragment4);
                }
                transaction.show(fragment4);
                break;
            default:
                break;
        }
        transaction.commit();
    }

    private void hiedeAll(FragmentTransaction transaction) {
        if(fragment1 != null) {
            transaction.hide(fragment1);
        }
        if(fragment2 != null) {
            transaction.hide(fragment2);
        }
        if(fragment3 != null) {
            transaction.hide(fragment3);
        }
        if(fragment4 != null) {
            transaction.hide(fragment4);
        }
    }



    private class Listener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.page1:
                    changeFragment(1);
                    break;
                case R.id.page2:
                    changeFragment(2);
                    break;
                case R.id.page3:
                    changeFragment(3);
                    break;
                case R.id.page4:
                    changeFragment(4);
                    break;
                default:
                    break;
            }
        }
    }
}
