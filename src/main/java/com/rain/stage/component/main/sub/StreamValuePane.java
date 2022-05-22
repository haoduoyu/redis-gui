package com.rain.stage.component.main.sub;

import com.alibaba.fastjson.JSONObject;

/**
 * @author rain.z
 * @description StringValuePane
 * @date 2022/05/22
 */
public class StreamValuePane extends BasePane {
    private JSONObject data;

    public StreamValuePane(JSONObject data) {
        this.data = data;
    }
}
