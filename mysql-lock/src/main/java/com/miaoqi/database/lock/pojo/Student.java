package com.miaoqi.database.lock.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Student {

    /**
     * 自增主键
     */
    private Integer id;

    /**
     * 没索引
     */
    private String name;

    /**
     * 非唯一索引
     */
    private Integer age;

    /**
     * 唯一索引
     */
    private String idcard;

}
