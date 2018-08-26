package com.example.administrator.myapplication.fragment;


import java.util.HashMap;
import java.util.Map;

public class Fragment3 extends BaseFragment {

    @Override
    String getTitle() {
        return this.getClass().getSimpleName();
    }

    @Override
    public Map<String, String> getExtraInfo() {
        Map<String, String> map = new HashMap<>();
        map.put("Fragment3", "test");
        map.put("title", "Fragment3");
        return map;
    }
}
