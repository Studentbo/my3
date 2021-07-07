package com.bjpowernode.test;

import com.bjpowernode.utils.RedisUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import java.util.List;

public class Test {
    public static void main(String[] args) {
        JedisPool jedisPool = RedisUtils.open("192.168.64.128", 6379);
        Jedis jedis = jedisPool.getResource();
        jedis.flushDB();
        //jedis的事务控制
        //获取事务控制的对象
        Transaction multi = jedis.multi();
        try{
            multi.set("k1","v1");
            System.out.println(1/0);
            multi.set("k2","v2");
            multi.exec();
        }catch (Exception e){
            multi.discard();
            System.out.println(jedis.get("k1"));
            System.out.println(jedis.get("k2"));
        }finally {
            RedisUtils.close();
        }
    }
}
