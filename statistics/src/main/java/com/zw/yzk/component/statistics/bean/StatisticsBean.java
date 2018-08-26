package com.zw.yzk.component.statistics.bean;

import java.util.Map;

public abstract class StatisticsBean {

    //点击开始时间
    public long start;
    //点击结束时间
    public long end;
    //点击事件方法调用时长
    public long duration;
    //统计点击事件的时候额外携带的数据
    public Map<String, String> extras;

    public abstract String toString();
}
