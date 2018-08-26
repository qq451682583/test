package com.zw.yzk.component.statistics;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import com.zw.yzk.component.statistics.config.StatisticsConfig;
import com.zw.yzk.component.statistics.statistics.ActivityStatistics;
import com.zw.yzk.component.statistics.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;


public class Statistics {

    private static Statistics instance;
    //统计Activity事件
    private ActivityStatistics activityStatistics;
    //存储统计事件用于统一上传
    private List<String> pageList;
    private List<String> dataList;
    //配置参数
    private StatisticsConfig config;


    private Statistics() {
        dataList = new ArrayList<>();
        pageList = new ArrayList<>();
        activityStatistics = new ActivityStatistics();
    }

    public static Statistics getInstance() {
        if (instance == null) {
            synchronized (Statistics.class) {
                if (instance == null) {
                    instance = new Statistics();
                }
            }
        }
        return instance;
    }

    /**
     * 初始化
     *
     * @param application Application this
     */
    public void init(Application application, StatisticsConfig config) {
        if (application == null) {
            throw new IllegalArgumentException("init failed application is null");
        }
        if (config == null) {
            throw new IllegalArgumentException("must set config");
        }
        if (config.upload == null) {
            throw new IllegalArgumentException("upload policy could not be null");
        }
        this.config = config;
        activityStatistics.registerActivityCallbacks(application);
        uploadLocalData();
    }


    /**
     * 添加数据，根据上传策略决定是否立即上传
     *
     * @param page 页面事件
     */
    public void addPage(String page) {
        Log.e("zhanwei", "page: " + page);
        pageList.add(page);
        if (pageList.size() >= config.uploadCount) {
            List<String> list = new ArrayList<>(pageList);
            config.upload.upload(list);
            pageList.clear();
        }
    }

    /**
     * 添加数据，根据上传策略决定是否立即上传
     *
     * @param data 统计事件
     */
    public void addData(String data, String path) {
        if (!filter(path)) {
            return;
        }
        Log.e("zhanwei", "data: " + data);
        dataList.add(data);
        if (dataList.size() >= config.uploadCount) {
            List<String> list = new ArrayList<>(dataList);
            config.upload.upload(list);
            dataList.clear();
        }
    }

    /**
     * 立即上传数据
     *
     * @param data 统计事件
     */
    public void upload(String data) {
        config.upload.upload(data);
    }

    /**
     * 统计数据是否符合统计条件
     *
     * @param path 统计数据路径
     * @return true：符合条件，false：不符合条件，丢弃
     */
    private boolean filter(String path) {
        if (TextUtils.isEmpty(config.filter)) {
            return true;
        }
        if (TextUtils.isEmpty(path)) {
            return false;
        }
        if (path.contains(ViewUtils.CHILD)) {
            String p = path.substring(0, path.indexOf(ViewUtils.CHILD));
            return config.filter.contains(p);
        } else {
            return config.filter.contains(path);
        }
    }

    /**
     * 退出应用
     */
    public void terminate() {
        Log.e("zhanwei", "terminate");
        if (config.store == null) {
            return;
        }
        config.store.saveData(dataList);
    }

    /**
     * 检查是否有本地数据需要上传
     */
    private void uploadLocalData() {
        if (config.store == null) {
            return;
        }
        List<String> local = config.store.getData();
        config.store.clearData();
        config.upload.upload(local);
    }

    /**
     * 获取顶层Activity名称
     *
     * @return activity名称
     */
    public String getTopActivity() {
        return activityStatistics.getTopActivityName();
    }
}
