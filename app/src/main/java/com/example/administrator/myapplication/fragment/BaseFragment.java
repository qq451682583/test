package com.example.administrator.myapplication.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.myapplication.R;
import com.zw.yzk.component.statistics.config.StatisticsExtras;
import com.zw.yzk.component.statistics.statistics.FragmentStatistics;
import com.zw.yzk.component.statistics.utils.ViewUtils;

import java.util.Map;

public abstract class BaseFragment extends Fragment implements StatisticsExtras {

    public int getLayoutId() {
        return R.layout.fragment_base;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(getLayoutId(), container, false);
        ((TextView) rootView.findViewById(R.id.title)).setText(getTitle());

        rootView.setTag(ViewUtils.FRAGMENT_TAG, this.getClass().getSimpleName());
        ViewUtils.setTagToView(rootView, 0);
        ViewUtils.setTagToViewTree(rootView);


        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public Map<String, String> getExtraInfo() {
        return null;
    }

    abstract String getTitle();
}
