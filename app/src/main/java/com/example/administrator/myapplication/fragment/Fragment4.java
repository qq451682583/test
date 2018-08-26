package com.example.administrator.myapplication.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.myapplication.R;
import com.zw.yzk.component.statistics.statistics.ClickStatistics;

public class Fragment4 extends BaseFragment {


    @Override
    String getTitle() {
        return this.getClass().getSimpleName();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root =  super.onCreateView(inflater, container, savedInstanceState);
        initView(root);
        return root;
    }

    private void initView(View root) {
        if(root == null) {
            return;
        }
        root.findViewById(R.id.title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ClickStatistics clickStatistics = new ClickStatistics(v);
//                clickStatistics.clickEnd();
            }
        });
    }
}
