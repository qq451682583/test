package com.zw.yzk.component.statistics.config;

import java.util.List;

/**
 * 上传统计事件
 */
public interface UploadPolicy {

    void upload(String data);

    void upload(List<String> data);
}
