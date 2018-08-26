package com.zw.yzk.component.statistics.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * 点击事件
 */
public class ClickBean extends StatisticsBean {

    //组件path，用于确定是哪个组件被点击
    public String path;
    //组件名称（TextView/Button/View）
    public String name;
    //组件文字(如果是TextView或者其子类)
    public String text;

    @Override
    public String toString() {
        JSONObject object = new JSONObject();
        try {
            object.put("start", start);
            object.put("end", end);
            object.put("duration", duration);
            object.put("name", name);
            object.put("path", path);
            object.put("text", text);
            if (extras != null) {
                for (String key : extras.keySet()) {
                    object.put(key, extras.get(key));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
    }
}
