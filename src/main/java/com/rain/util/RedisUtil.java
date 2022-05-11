package com.rain.util;

import redis.clients.jedis.JedisPool;

/**
 * @author rain.z
 * @description RedisUtil
 * @date 2022/05/10
 */
public class RedisUtil {
    public static JedisPool getRedisInstance(String host, int port) {
        port = port > 0 ? port : 6379;
        JedisPool pool = new JedisPool(host, port);
        return pool;
    }
}
