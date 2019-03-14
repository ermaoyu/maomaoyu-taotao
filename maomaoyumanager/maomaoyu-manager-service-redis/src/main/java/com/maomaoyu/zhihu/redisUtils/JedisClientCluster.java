package com.maomaoyu.zhihu.redisUtils;

import com.maomayu.zhihu.service.JedisClient;
import org.springframework.beans.factory.InitializingBean;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * maomaoyu    2019/3/14_22:20
 * 集群版实现
 **/

public class JedisClientCluster implements JedisClient,InitializingBean {

    private JedisCluster jedisCluster;

    @Override
    public String set(String key, String value) {
        return jedisCluster.set(key,value);
    }

    @Override
    public String get(String key) {
        return jedisCluster.get(key);
    }

    @Override
    public Long incr(String key) {
        return jedisCluster.incr(key);
    }

    @Override
    public Boolean exists(String key) {
        return jedisCluster.exists(key);
    }

    @Override
    public Long expire(String key, int seconds) {
        return jedisCluster.expire(key,seconds);
    }

    @Override
    public Long ttl(String key) {
        return jedisCluster.ttl(key);
    }

    @Override
    public Long hset(String key, String field, String value) {
        return jedisCluster.hset(key,field,value);
    }

    @Override
    public String hget(String key, String field) {
        return jedisCluster.hget(key,field);
    }

    @Override
    public Long hdel(String key, String... field) {
        return jedisCluster.hdel(key,field);
    }

    @Override
    public Long sadd(String key, String value) {
        return jedisCluster.sadd(key,value);
    }

    @Override
    public Long srem(String key) {
        return jedisCluster.srem(key);
    }

    @Override
    public boolean sismember(String key, String value) {
        return jedisCluster.sismember(key,value);
    }

    @Override
    public Long scard(String key) {
        return jedisCluster.scard(key);
    }

    @Override
    public Set<String> smembers(String key) {
        return jedisCluster.smembers(key);
    }

    @Override
    public Long lpush(String key, String value) {
        return jedisCluster.lpush(key,value);
    }

    @Override
    public Long rpush(String key, String value) {
        return jedisCluster.rpush(key,value);
    }

    @Override
    public String lpop(String key) {
        return jedisCluster.lpop(key);
    }

    @Override
    public String rpop(String key) {
        return jedisCluster.rpop(key);
    }

    @Override
    public List<String> lrange(String key, int start, int end) {
        return jedisCluster.lrange(key,start,end);
    }

    @Override
    public Long zadd(String key, int score, String member) {
        return jedisCluster.zadd(key,score,member);
    }

    @Override
    public Long zrem(String key, String value) {
        return jedisCluster.zrem(key,value);
    }

    @Override
    public Long zrank(String key, String member) {
        return jedisCluster.zrank(key, member);
    }

    @Override
    public Double zscore(String key, String member) {
        return jedisCluster.zscore(key, member);
    }

    @Override
    public Set<String> zrange(String key, int start, int stop) {
        return jedisCluster.zrange(key,start,stop);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Set<HostAndPort> nodes = new HashSet<>();
        nodes.add(new HostAndPort("192.168.184.130", 7001));
        nodes.add(new HostAndPort("192.168.184.130", 7002));
        nodes.add(new HostAndPort("192.168.184.130", 7003));
        nodes.add(new HostAndPort("192.168.184.130", 7004));
        nodes.add(new HostAndPort("192.168.184.130", 7005));
        nodes.add(new HostAndPort("192.168.184.130", 7006));
        jedisCluster = new JedisCluster(nodes);
    }
}
