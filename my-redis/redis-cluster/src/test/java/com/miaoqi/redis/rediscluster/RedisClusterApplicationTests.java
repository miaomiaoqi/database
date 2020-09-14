package com.miaoqi.redis.rediscluster;

import com.miaoqi.redis.rediscluster.dataobject.UserDO;
import com.miaoqi.redis.rediscluster.service.RedisService;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RedisClusterApplicationTests {

    @Autowired
    private RedisService redisService;

    @Test
    void testSet() {
        try {
            Thread.sleep(5000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 100; i++) {
            UserDO userDO = new UserDO();
            userDO.setId(i);
            userDO.setName("哈哈哈" + i);
            userDO.setCreateTime(new DateTime());
            redisService.set("user-cache-" + i, userDO);
        }
    }

}
