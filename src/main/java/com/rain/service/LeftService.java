package com.rain.service;

import com.alibaba.fastjson.JSONObject;
import com.rain.constant.TabType;

import java.util.List;
import java.util.Objects;
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

    public JSONObject getValueByKey(JSONObject jsonObject, String key) {
        String id = jsonObject.getString("id");

        String value = this.redisService.get(id, key);

        if (Objects.isNull(value)) {
            return new JSONObject();
        }

        String type = this.redisService.type(id, key);
        JSONObject result = new JSONObject();
        result.put("value", value);
        result.put("type", TabType.typeNameToValue(type));
        result.put("key", key);

        return result;
    }
}
