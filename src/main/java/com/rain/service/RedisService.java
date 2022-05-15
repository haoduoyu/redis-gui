package com.rain.service;

import com.alibaba.fastjson.JSONObject;
import com.rain.util.RedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author rain.z
 * @description RedisService
 * @date 2022/05/11
 */
public class RedisService {
    private final static Map<String, JedisPool> redisClientCache = new HashMap<String, JedisPool>();

    public boolean connectRedis(JSONObject connectionInfo) {
        JSONObject params = connectionInfo.getJSONObject("params");
        String id = connectionInfo.getString("id");

        if (!redisClientCache.containsKey(id) || redisClientCache.get(id) == null) {
            String host = params.getString("address");
            int port = params.getIntValue("port");

            try {
                JedisPool redisInstance = RedisUtil.getRedisInstance(host, port);
                redisInstance.getResource();
                redisClientCache.put(id, redisInstance);
                return true;
            } catch (JedisException e) {
                System.out.println("警告一下链接失败");
                return false;
            }
        }
        return true;
    }

    private Jedis getResource(String id) {
        return redisClientCache.get(id).getResource();
    }

    public String get(String id, String key) {
        return this.getResource(id).get(key);
    }

    public String set(String id, String key, String value) {
        return this.getResource(id).set(key, value);
    }

    public String info(String id, String section) {
        return this.getResource(id).info(section);
    }

    public String info(String id) {
        return this.getResource(id).info();
    }

    public Set<String> keys(String id, String pattern) {
        return this.getResource(id).keys(pattern);
    }
}
