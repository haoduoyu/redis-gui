package com.rain.service;

import com.alibaba.fastjson.JSONObject;
import com.rain.util.RedisUtil;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

import java.net.ConnectException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author rain.z
 * @description RedisService
 * @date 2022/05/11
 */
public class RedisService {
    private static Map<String, JedisPool> redisClientCache = new HashMap<String, JedisPool>();

    public void connectRedis(JSONObject connectionInfo) {
        JSONObject params = connectionInfo.getJSONObject("params");
        String id = connectionInfo.getString("id");

        if (!redisClientCache.containsKey(id) || redisClientCache.get(id) == null) {
            String host = params.getString("address");
            int port = params.getIntValue("port");

            try {
                JedisPool redisInstance = RedisUtil.getRedisInstance(host, port);
                redisInstance.getResource();
                redisClientCache.put(id, redisInstance);
            } catch (JedisException e) {
                System.out.println("警告一下链接失败");
            }
        }
    }

    public String get(String id, String key) {
        return redisClientCache.get(id).getResource().get(key);
    }

    public String set(String id, String key, String value) {
        return redisClientCache.get(id).getResource().set(key, value);
    }
}
