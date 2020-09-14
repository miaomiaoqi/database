package com.miaoqi.redis.redisson;

import com.miaoqi.redis.redisson.dataobject.UserDO;
import org.junit.jupiter.api.Test;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

@SpringBootTest
class RedissonApplicationTests {

    @Autowired
    private RedissonClient redissonClient;

    @Test
    void testSet() {
        RBucket<String> bucket = redissonClient.getBucket("redisxxxson-kxxx");
        bucket.set("milky");
        String o = bucket.get();
        System.out.println("aaa");
        System.out.println(o);
        System.out.println("xxx");
    }

    @Test
    void testObject() {
        RBucket<UserDO> bucket = redissonClient.getBucket("redisson-user");
        System.out.println(bucket.get());
        System.out.println(bucket.get() == null);
        bucket.set(new UserDO());
        System.out.println(bucket.get());
        System.out.println(bucket.get()==null);
    }

    @Test
    void testLock() {
        RLock lock = redissonClient.getLock("redisson-lock");
        System.out.println("try get");
        // 第一次会拉取到 redis 中的剩余 ttl, 然后在内存中自己比较
        lock.lock(5, TimeUnit.SECONDS);
        System.out.println("i got it");
        // lock.unlock();
        try {
            boolean b = lock.tryLock(1L, 2L, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testBloom() {
        RBloomFilter<Object> mybloom = redissonClient.getBloomFilter("mybloom");
        mybloom.tryInit(50L, 0);
        mybloom.add("1");
        System.out.println(mybloom.contains("2"));
    }

}
