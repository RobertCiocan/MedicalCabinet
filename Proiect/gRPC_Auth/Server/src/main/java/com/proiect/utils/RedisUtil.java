package com.proiect.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtil {

    private static final String BLACKLIST_SET_KEY = "token_blacklist";

    private static JedisPool jedisPool = new JedisPool(new JedisPoolConfig(), "localhost", 6379);

    public static void blacklistToken(String token) {
        System.out.println("Blacklisting token: " + token);
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.sadd(BLACKLIST_SET_KEY, token);
        }
    }

    public static boolean isBlacklisted(String token) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.sismember(BLACKLIST_SET_KEY, token);
        }
    }
}
