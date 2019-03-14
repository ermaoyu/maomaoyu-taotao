import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * maomaoyu    2019/3/14_20:42
 **/
public class testJedis {

    @Test
    public void testJedi(){
        Jedis jedis = new Jedis("192.168.184.130",6379);
        jedis.set("hello","wrold");
        String res = jedis.get("hello");
        System.out.println(res);
        jedis.close();
    }

    @Test
    public void testJedisPool(){
        JedisPool jedisPool = new JedisPool("192.168.184.130",6379);
        Jedis jedis = jedisPool.getResource();
        jedis.set("jedis","test");
        String res = jedis.get("jedis");
        System.out.println(res);
        jedis.close();
        jedisPool.close();
    }

    @Test
    public void testJedisCluster() throws IOException {
        Set<HostAndPort> nodes = new HashSet<>();
        nodes.add(new HostAndPort("192.168.184.130",7001));
        nodes.add(new HostAndPort("192.168.184.130",7002));
        nodes.add(new HostAndPort("192.168.184.130",7003));
        nodes.add(new HostAndPort("192.168.184.130",7004));
        nodes.add(new HostAndPort("192.168.184.130",7005));
        nodes.add(new HostAndPort("192.168.184.130",7006));
        JedisCluster jedisCluster = new JedisCluster(nodes);
        jedisCluster.set("hello","100");
        String res = jedisCluster.get("hello");
        System.out.println(res);
        jedisCluster.close();
    }

}
