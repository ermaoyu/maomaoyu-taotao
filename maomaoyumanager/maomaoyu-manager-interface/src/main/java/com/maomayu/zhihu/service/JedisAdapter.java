package com.maomayu.zhihu.service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.List;
import java.util.Set;

/**
 * maomaoyu    2018/12/25_19:52
 **/
public interface JedisAdapter  {


    public long sadd (String key,String value);

    public long srem(String key,String value);

    public long scard(String key);

    public boolean sismember(String key,String value);

    public List<String> brpop(int timeout,String key);

    public long lpush(String key,String value);

    public List<String> lrange(String key,int start,int end);

    public long zadd(String key,double score,String value);

    public long zrem(String key,String value);

    public String get(String key);

    public String setex(String key,String value);

    public Transaction multi(Jedis jedis);

    public List<Object> exec(Transaction tx ,Jedis jedis);

    public Set<String> zrange(String key,int start ,int end);

    public Set<String> zrevrange(String key,int start ,int end);

    public long zcard(String key);

    public Double zscore(String key, String member);

    public Jedis getJedis();
}
