package com.zw.yzk.component.statistics.config;

public class StatisticsConfig {

    //上传统计事件
    public UploadPolicy upload;
    //本地存储
    public StorePolicy store;
    //多少数据上传一次
    public int uploadCount;
    //根据控件路径决定点击事件是否上传，filter为空则上传所有，否则只上传filter包含的事件
    public String filter;

    private StatisticsConfig() {
        uploadCount = 50;
    }

    public static StatisticsConfig Builder() {
        return new StatisticsConfig();
    }

    /**
     * 设置本地存储策略
     *
     * @param store 本地存储策略
     */
    public StatisticsConfig setStorePolicy(StorePolicy store) {
        this.store = store;

        return this;
    }

    /**
     * 设置上传策略
     *
     * @param upload 上传策略
     */
    public StatisticsConfig setUploadPolicy(UploadPolicy upload) {
        this.upload = upload;

        return this;
    }

    /**
     * 设置多少条数据上传一次
     *
     * @param frequency 统计事件阀值，达到阀值之后上传,默认50
     */
    public StatisticsConfig setFrequency(int frequency) {
        this.uploadCount = frequency;

        return this;
    }

    /**
     * 设置筛选策略，filter为空则所有的统计事件都要上传否则只上传filter中包含的路径的事件
     * @param filter 统计事件筛选策略
     */
    public StatisticsConfig setFilterPolicy(String filter) {
        this.filter = filter;

        return this;
    }

}
