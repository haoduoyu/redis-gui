package com.rain.service;

import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Set;

/**
 * @author rain.z
 * @description LeftService
 * @date 2022/05/15
 */
public class LeftService {
    private final RedisService redisService;
    private final DBService dbService;

    public LeftService() {
        this.redisService = new RedisService();
        this.dbService = new DBService();
    }

    public boolean connectRedis(JSONObject jsonObject) {
        return this.redisService.connectRedis(jsonObject);
    }

    public List<JSONObject> getData() {
        return this.dbService.getData();
    }

    public void deleteData(JSONObject jsonObject) {
        this.dbService.deleteData(jsonObject);
    }

    public Set<String> getAllKeys(JSONObject jsonObject) {
        String id = jsonObject.getString("id");
        return this.redisService.keys(id, "*");
    }
}
