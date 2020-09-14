package com.miaoqi.redis.redisson.dataobject;

import lombok.Data;
import org.joda.time.DateTime;

import java.io.Serializable;

@Data
public class UserDO implements Serializable {

    private Integer id;

    private String name;

    private DateTime createTime;

}
