package com.zw.yzk.component.statistics.statistics;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.zw.yzk.component.statistics.config.StatisticsExtras;
import com.zw.yzk.component.statistics.Statistics;
import com.zw.yzk.component.statistics.bean.PageBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 统计Activity页面信息
 */
public class ActivityStatistics {

    private List<PageBean> pageList;
    private Long resumeTime;

    public ActivityStatistics() {
        pageList = new ArrayList<>();
    }

    public void registerActivityCallbacks(Application application) {
        application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {
                PageBean page = new PageBean();
                page.name = activity.getClass().getSimpleName();
                if (pageList.isEmpty()) {
                    page.previous = "";
                } else {
                    page.previous = pageList.get(pageList.size() - 1).name;
                }
                if (activity instanceof StatisticsExtras) {
                    page.extras = ((StatisticsExtras) activity).getExtraInfo();
                }
                page.start = System.currentTimeMillis();
                pageList.add(page);
            }

            @Override
            public void onActivityStarted(Activity activity) {
            }

            @Override
            public void onActivityResumed(Activity activity) {
                resumeTime = System.currentTimeMillis();
            }

            @Override
            public void onActivityPaused(Activity activity) {
                pageList.get(pageList.size() - 1).show += System.currentTimeMillis() - resumeTime;
            }

            @Override
            public void onActivityStopped(Activity activity) {
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                PageBean last = pageList.get(pageList.size() - 1);
                last.end = System.currentTimeMillis();
                last.duration = last.end - last.start;
                pageList.remove(last);

                Statistics.getInstance().addPage(last.toString());

                if (pageList.size() == 0) {
                    //所有的Activity都被销毁，表示退出应用了
                    Statistics.getInstance().terminate();
                }
            }
        });
    }

    /**
     * 获取最顶层Activity
     *
     * @return activity名称
     */
    public String getTopActivityName() {
        if (pageList.isEmpty()) {
            return "";
        }
        return pageList.get(pageList.size() - 1).name;
    }

}
