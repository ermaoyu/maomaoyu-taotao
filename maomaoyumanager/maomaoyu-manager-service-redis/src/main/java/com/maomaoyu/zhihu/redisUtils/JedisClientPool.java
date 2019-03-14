package com.maomaoyu.zhihu.redisUtils;

import com.alibaba.dubbo.config.annotation.Service;
import com.maomayu.zhihu.service.JedisClient;
import org.springframework.beans.factory.InitializingBean;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Set;

/**
 * maomaoyu    2019/3/14_20:40
 * 连接Redis单机版
 * 第一步：创建一个Jedis对象。需要指定服务端的ip及端口。
 * 第二步：使用Jedis对象操作数据库，每个redis命令对应一个方法。
 * 第三步：打印结果。
 * 第四步：关闭Jedis
 **/
@Service
public class JedisClientPool implements JedisClient,InitializingBean {

    private JedisPool jedisPool;

    @Override
    public String set(String key, String value) {
        Jedis jedis = jedisPool.getResource();
        String res = jedis.set(key,value);
        jedis.close();
        return res;
    }

    @Override
    public String get(String key) {
        Jedis jedis = jedisPool.getResource();
        String res = jedis.get(key);
        jedis.close();
        return res;
    }

    @Override
    public Long incr(String key) {
        Jedis jedis = jedisPool.getResource();
        Long res = jedis.incr(key);
        jedis.close();
        return res;
    }

    @Override
    public Boolean exists(String key) {
        Jedis jedis = jedisPool.getResource();
        boolean res = jedis.exists(key);
        jedis.close();
        return res;
    }

    @Override
    public Long expire(String key, int seconds) {
        Jedis jedis = jedisPool.getResource();
        Long res = jedis.expire(key,seconds);
        jedis.close();
        return res;
    }

    @Override
    public Long ttl(String key) {
        Jedis jedis = jedisPool.getResource();
        Long res = jedis.ttl(key);
        jedis.close();
        return res;
    }

    @Override
    public Long hset(String key, String field, String value) {
        Jedis jedis = jedisPool.getResource();
        Long res = jedis.hset(key,field,value);
        jedis.close();
        return res;
    }

    @Override
    public String hget(String key, String field) {
        Jedis jedis = jedisPool.getResource();
        String res = jedis.hget(key,field);
        jedis.close();
        return res;
    }

    @Override
    public Long hdel(String key, String... field) {
        Jedis jedis = jedisPool.getResource();
        Long res = jedis.hdel(key,field);
        jedis.close();
        return res;
    }

    @Override
    public Long sadd(String key, String value) {
        Jedis jedis = jedisPool.getResource();
        Long res = jedis.sadd(key,value);
        jedis.close();
        return res;
    }

    @Override
    public Long srem(String key) {
        Jedis jedis = jedisPool.getResource();
        Long res = jedis.srem(key);
        jedis.close();
        return res;
    }

    @Override
    public boolean sismember(String key, String value) {
        Jedis jedis = jedisPool.getResource();
        boolean res = jedis.sismember(key,value);
        jedis.close();
        return res;
    }

    @Override
    public Long scard(String key) {
        Jedis jedis = jedisPool.getResource();
        Long res = jedis.scard(key);
        jedis.close();
        return res;
    }

    @Override
    public  Set<String> smembers(String key) {
        Jedis jedis = jedisPool.getResource();
        Set<String> res = jedis.smembers(key);
        jedis.close();
        return res;
    }

    @Override
    public Long lpush(String key, String value) {
        Jedis jedis = jedisPool.getResource();
        Long res = jedis.lpush(key,value);
        jedis.close();
        return res;
    }

    @Override
    public Long rpush(String key, String value) {
        Jedis jedis = jedisPool.getResource();
        Long res = jedis.rpush(key,value);
        jedis.close();
        return res;
    }

    @Override
    public String lpop(String key) {
        Jedis jedis = jedisPool.getResource();
        String res = jedis.lpop(key);
        jedis.close();
        return res;
    }

    @Override
    public String rpop(String key) {
        Jedis jedis = jedisPool.getResource();
        String res = jedis.rpop(key);
        jedis.close();
        return res;
    }

    @Override
    public List<String> lrange(String key, int start, int end) {
        Jedis jedis = jedisPool.getResource();
        List<String> res = jedis.lrange(key,start,end);
        jedis.close();
        return res;
    }

    @Override
    public Long zadd(String key, int score, String member) {
        Jedis jedis = jedisPool.getResource();
        Long res = jedis.zadd(key,score,member);
        jedis.close();
        return res;
    }

    @Override
    public Long zrem(String key, String value) {
        Jedis jedis = jedisPool.getResource();
        Long res = jedis.zrem(key,value);
        jedis.close();
        return res;
    }

    @Override
    public Long zrank(String key, String member) {
        Jedis jedis = jedisPool.getResource();
        Long res = jedis.zrank(key,member);
        jedis.close();
        return res;
    }

    @Override
    public Double zscore(String key, String member) {
        Jedis jedis = jedisPool.getResource();
        Double res = jedis.zscore(key,member);
        jedis.close();
        return res;
    }

    @Override
    public Set<String> zrange(String key, int start, int stop) {
        Jedis jedis = jedisPool.getResource();
        Set<String> res = jedis.zrange(key,start,stop);
        jedis.close();
        return res;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        jedisPool = new JedisPool("192.168.184.130",6379);
    }
}
