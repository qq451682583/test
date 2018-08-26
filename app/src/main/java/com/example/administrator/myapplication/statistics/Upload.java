package com.example.administrator.myapplication.statistics;

import android.util.Log;

import com.zw.yzk.component.statistics.config.UploadPolicy;

import java.util.List;

public class Upload implements UploadPolicy {
    @Override
    public void upload(String data) {
        Log.e("zhanwei", "upload data: " + data);
    }

    @Override
    public void upload(List<String> data) {
        if(data == null) {
            return;
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < data.size(); i++) {
            builder.append("[").append(i).append("]: ")
                    .append(data.get(i));
        }
        Log.e("zhanwei", "upload data list : " + builder.toString());
    }
}
