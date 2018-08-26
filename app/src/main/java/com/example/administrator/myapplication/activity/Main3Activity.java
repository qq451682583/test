package com.example.administrator.myapplication.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.fragment.BaseFragment;
import com.example.administrator.myapplication.fragment.Fragment1;
import com.example.administrator.myapplication.fragment.Fragment2;
import com.example.administrator.myapplication.fragment.Fragment3;
import com.example.administrator.myapplication.fragment.Fragment4;
import com.example.administrator.myapplication.fragment.Fragment5;
import com.example.administrator.myapplication.fragment.Fragment6;
import com.zw.yzk.component.statistics.config.StatisticsExtras;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main3Activity extends BaseActivity implements StatisticsExtras {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main3Activity.this, Main4Activity.class));
            }
        });

        initView();
    }

    @Override
    public Map<String, String> getExtraInfo() {
        Map<String, String> map = new HashMap<>();
        map.put("activity3", "test");
        map.put("title", "activity3");
        return map;
    }

    private void initView() {
        List<BaseFragment> list = new ArrayList<>();
        list.add(new Fragment1());
        list.add(new Fragment2());
        list.add(new Fragment3());
        list.add(new Fragment4());
        list.add(new Fragment5());
        list.add(new Fragment6());

        ViewPager pager = findViewById(R.id.pager);
        pager.setAdapter(new MyAdapter(getSupportFragmentManager(), list));
    }

    private class MyAdapter extends FragmentPagerAdapter{

        private List<BaseFragment> fragments;

        public MyAdapter(FragmentManager fm, List<BaseFragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}
