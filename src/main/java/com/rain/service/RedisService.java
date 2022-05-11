package com.rain.service;

import com.alibaba.fastjson.JSONObject;

/**
 * @author rain.z
 * @description RedisService
 * @date 2022/05/11
 */
public class RedisService {
    public void connectRedis(JSONObject connectionInfo) {
        System.out.println(connectionInfo.toJSONString());
    }
}
