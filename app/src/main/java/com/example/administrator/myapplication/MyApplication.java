package com.example.administrator.myapplication;

import android.app.Application;
import android.app.Fragment;

import com.example.administrator.myapplication.statistics.Upload;
import com.zw.yzk.component.statistics.Statistics;
import com.zw.yzk.component.statistics.config.StatisticsConfig;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Statistics.getInstance().init(this, StatisticsConfig.Builder()
                .setUploadPolicy(new Upload())
//                .setFilterPolicy("Main3Activity.LinearLayout[0].ViewPager[1].Fragment6.Child[5].LinearLayout[0].RecyclerView[1].Child")
        );
    }

}
