package com.zw.yzk.component.statistics.statistics;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.zw.yzk.component.statistics.Statistics;
import com.zw.yzk.component.statistics.utils.ViewUtils;
import com.zw.yzk.component.statistics.bean.ClickBean;

/**
 * 点击事件收集
 */
public class ClickStatistics {

    private ClickBean clickBean;

    public ClickStatistics(View view) {
        clickBean = new ClickBean();
        clickStart();
        setClickInfo(view);
    }

    /**
     * 点击事件开始
     */
    private void clickStart() {
        if (clickBean != null) {
            clickBean.start = System.currentTimeMillis();
        }
    }

    /**
     * 点击事件结束
     */
    public void clickEnd() {
        if (clickBean != null) {
            clickBean.end = System.currentTimeMillis();
            clickBean.duration = clickBean.end - clickBean.start;

            Statistics.getInstance().addData(clickBean.toString(), clickBean.path);
        }
    }

    /**
     * 初始化点击事件
     *
     * @param view 被点击的View
     */
    private void setClickInfo(View view) {
        String activity = ViewUtils.getActivityName(view);

        //设置name
        clickBean.name = TextUtils.isEmpty(activity)
                ? Statistics.getInstance().getTopActivity() : activity;
        //设置text
        if (view instanceof TextView) {
            clickBean.text = ((TextView) view).getText().toString();
        }
        //设置path
        clickBean.path = ViewUtils.getViewPath(view);
    }
}
