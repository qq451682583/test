package com.zw.yzk.component.statistics.statistics;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.zw.yzk.component.statistics.config.StatisticsExtras;
import com.zw.yzk.component.statistics.Statistics;
import com.zw.yzk.component.statistics.bean.PageBean;


/**
 * 统计fragment页面信息
 */
public class FragmentStatistics {

    private PageBean pageBean;
    private long resumeTime;
    private boolean needPause;

    public FragmentStatistics() {
        needPause = true;
        pageBean = new PageBean();
    }

    public void onCreateView(Fragment fragment) {
        init(fragment);
    }

    /**
     * Fragment onResume调用时调用，当fragment与viewpager联用时onResume调用时并不一定是处于可见状态
     * 需要与isHidden和getUserVisibleHint一起判断
     *
     * @param fragment fragment
     */
    public void onResume(Fragment fragment) {
        if (!fragment.isHidden() && fragment.getUserVisibleHint()) {
            show();
        }
    }

    /**
     * 用于计算fragment的可见时间
     */
    public void onPause() {
        if(needPause) {
            hidden();
        }
    }

    /**
     * 一般是在一个Activity中展示多个Fragment的时候会触发onHiddenChanged调用
     *
     * @param hidden true：不可见，false：可见
     */
    public void onHiddenChanged(boolean hidden) {
        needPause = !hidden;
        if (hidden) {
            hidden();
        } else {
            show();
        }
    }

    /**
     * 在与ViewPager联用的时候onResume不能准确的判断Fragment是否处于可见状态，用setUserVisibleHint
     * 可以判断
     *
     * @param visible true：可见，false：不可见
     */
    public void setUserVisibleHint(boolean visible) {
        needPause = visible;
        if (visible) {
            show();
        } else {
            hidden();
        }
    }

    /**
     * Fragment页面被销毁，用于计算页面从创建到销毁的时间
     */
    public void onDestroyView() {
        pageBean.end = System.currentTimeMillis();
        pageBean.duration = pageBean.end - pageBean.start;

        Statistics.getInstance().addPage(pageBean.toString());
    }

    /**
     * 初次进入页面
     */
    private void init(Fragment fragment) {
        if (pageBean.start == 0) {
            pageBean.start = System.currentTimeMillis();
            if(fragment instanceof StatisticsExtras) {
                pageBean.extras = ((StatisticsExtras) fragment).getExtraInfo();
            }
            FragmentActivity activity = fragment.getActivity();
            if (activity != null) {
                pageBean.name = activity.getClass().getSimpleName() + "." + fragment.getClass().getSimpleName();
            } else {
                pageBean.name = fragment.getClass().getSimpleName();
            }
        }
    }


    /**
     * 当页面处于可见状态
     */
    private void show() {
        resumeTime = System.currentTimeMillis();
    }

    /**
     * 页面处于不可见状态，由于有可能调用setUserVisibleHint(单Activity多个Fragment)
     * 或onHiddenChanged(ViewPager+Fragment)之后还会调用onPause所以在这里重新设置resumeTime
     * 避免pageBean.show错误计算
     */
    private void hidden() {
        if(resumeTime == 0) {
            return;
        }
        long show = System.currentTimeMillis() - resumeTime;
        pageBean.show += show;
        resumeTime = System.currentTimeMillis();
    }
}
