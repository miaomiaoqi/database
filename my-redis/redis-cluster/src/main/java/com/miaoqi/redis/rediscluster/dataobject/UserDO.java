package com.miaoqi.redis.rediscluster.dataobject;

import lombok.Data;
import org.joda.time.DateTime;

@Data
public class UserDO {

    private Integer id;

    private String name;

    private DateTime createTime;

}
