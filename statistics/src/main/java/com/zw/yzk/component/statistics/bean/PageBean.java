package com.zw.yzk.component.statistics.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * 统计页面事件
 * 页面时长
 * 页面名称
 */
public class PageBean extends StatisticsBean{

    //页面展示时间（应用处于后台时不算）
    public long show;
    //页面名称
    public String name;
    //前一个页面
    public String previous;

    @Override
    public String toString() {
        JSONObject object = new JSONObject();
        try {
            object.put("start", start);
            object.put("end", end);
            object.put("show", show);
            object.put("duration", duration);
            object.put("name", name);
            object.put("previous", previous);
            if(extras != null) {
                for (String key: extras.keySet()) {
                    object.put(key,extras.get(key));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
    }
}
