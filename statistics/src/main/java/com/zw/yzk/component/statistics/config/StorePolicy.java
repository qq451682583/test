package com.zw.yzk.component.statistics.config;

import java.util.List;

/**
 * 暂存统计事件，在上传策略不允许上传或者没有网络的时候先把统计事件暂存在本地
 */
public interface StorePolicy {

    void saveData(List<String> data);

    void clearData();

    void addData(String data);

    List<String> getData();
}
